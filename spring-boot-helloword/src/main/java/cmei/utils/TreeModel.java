package cmei.utils;

/**
 * TreeModel
 *
 * @author meicanhua
 * @create 2017-09-07 下午4:05
 **/
public class TreeModel {

    private Long id;

    private Long parentId;

    private Float sort;  //1.1.2, 2.1.2

    public Float getSort() {
        return sort;
    }

    public void setSort(Float sort) {
        this.sort = sort;
    }

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

}