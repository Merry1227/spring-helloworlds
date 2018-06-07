package cmei.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

//import org.apache.commons.beanutils.*;



import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SortedTreeDto
 *
 * @author meicanhua
 * @create 2017-09-07 下午4:07
 **/
public class SortedTreeDto {

    private Logger logger = LoggerFactory.getLogger(SortedTreeDto.class);

    private static final int MAX_LEVEL = 10;

    private Long id;

    private Long parentId;

    private boolean valid = true; //当前节点是否被选中

    private boolean hasValidChild = false;

    @JsonIgnore
    private transient boolean inserted=false;

    @JsonIgnore
    private transient boolean visited=false;

    private Float sort;  //1.1.2, 2.1.2

    public Float getSort() {
        return sort;
    }

    public void setSort(Float sort) {
        this.sort = sort;
    }

    private static Comparator<SortedTreeDto> comparator = new Comparator<SortedTreeDto>() {
        @Override
        public int compare(SortedTreeDto o1, SortedTreeDto o2) {
            if (o1.getSort().floatValue() == o2.getSort().floatValue()) {
                return 0;
            } else {
                return (o1.getSort().floatValue() - o2.getSort().floatValue()) < 0 ? -1 : 1;
            }
        }
    };

    private Collection<SortedTreeDto> children = new TreeSet<>(comparator);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Collection<SortedTreeDto> getChildren() {
        return children;
    }

    public void setChildren(Collection<SortedTreeDto> children) {
        this.children = children;
    }

    public void addChild(SortedTreeDto treeDto) {
        children.add(treeDto);
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isHasValidChild() {
        return hasValidChild;
    }

    public void setHasValidChild(boolean hasValidChild) {
        this.hasValidChild = hasValidChild;
    }

    /**
     * 从扁平的models中建立树形层次结构
     * 用<code>treeModels</code> 构建一颗树，并且根据<code>validIds</code>设置树节点是否是有效节点
     * @param treeModels
     * @param validIds
     * @param <T>
     * @param <M>
     * @return
     */
    public <T extends SortedTreeDto, M extends TreeModel> Collection<T> buildTreeRootsFromModels(List<M> treeModels, List<Long> validIds){

        if (treeModels == null) {
            return null;
        }

        if (treeModels.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        if (treeModels.size() > 10000) {
            logger.warn("TreeDto", "The size of treeModels is too large:" + treeModels.size());
        }

        Map<Long,T> refDtos = new HashMap<Long, T>();
        Map<Long, T> rootMap = new HashMap<Long, T>();
        Map<Long, T>  allIdBeanMap = new HashMap<Long, T>();

        //初始化，存储id与Dto关系
        for (M model: treeModels) {
            T dto = buildInstance(model);
            if(validIds != null)  {
                dto.setValid(validIds.contains(dto.getId()));
            }

            refDtos.put(dto.getId(), dto);
            allIdBeanMap.putIfAbsent(dto.getId(), dto);
        }

        //轮训当前roots
        for(Long id : allIdBeanMap.keySet()) {
            if (refDtos.containsKey(id)) {
                int maxLevel = 0;
                T currentDto = refDtos.get(id);

                //找根
                do {
                    maxLevel++;

                    //为根节点
                    if (null == currentDto.getParentId()
                            || 0 == currentDto.getParentId()
                            || !allIdBeanMap.containsKey(currentDto.getParentId())) {
                        rootMap.putIfAbsent(currentDto.getId(), currentDto);
                        if(!currentDto.isInserted()) {
                            refDtos.remove(currentDto.getId());
                            currentDto.setInserted(true);
                        }
                        break;
                    }

                    T parentDto = allIdBeanMap.get(currentDto.getParentId());

                    if (parentDto == null ) {
                        logger.info("TreeDto", "currentId:" + currentDto.getId() + " parentId:" + currentDto.getParentId());
                        break;
                    }

                    //当前节点没有插入的话，插入当前节点
                    if(!currentDto.isInserted()) {
                        parentDto.addChild(currentDto);
                        refDtos.remove(currentDto.getId());
                        currentDto.setInserted(true);
                    }
                    //如果插入的话，则更加最新的leaf状态更新
                    if (currentDto.isValid()||currentDto.isHasValidChild()) {
                        parentDto.setHasValidChild(true);
                    }

                    currentDto = parentDto;

                } while (currentDto != null && maxLevel <= MAX_LEVEL);
            }
        }


        Collection<T> result = rootMap.values().stream().sorted(comparator).collect(Collectors.toList());
        return result;

    }

    /**
     * 用<code>treeModels</code> 构建一颗树，并且根据<code>validIds</code>设置树节点是否是有效节点
     * @param treeModels
     * @param <T>
     * @param <M>
     * @return
     */
    public <T extends SortedTreeDto, M extends TreeModel> Collection<T> buildTreeRootsFromModels(List<M> treeModels) {
        return this.buildTreeRootsFromModels(treeModels, null);
    }


    public <T extends SortedTreeDto, M extends TreeModel> T buildInstance(M treeModel) {
        T dto = null;
        try {
            dto = (T) this.getClass().newInstance();
            BeanUtils.copyProperties(dto, treeModel);
        } catch (InstantiationException e) {
            logger.warn("buildInstance", e);
        } catch (IllegalAccessException e) {
            logger.warn("buildInstance", e);
        }
//        } catch (InvocationTargetException e) {
//            logger.warn("bean-copy", e);
//        }
        return (T) dto;
    };






}