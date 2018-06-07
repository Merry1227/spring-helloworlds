package cmei.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanCopyHelper: 一般Bean的拷贝，基本可以用spring 自带的BeanUtil解决。但带条件的Bean copy则没有那么方便。
 * 该类提供了对NULL类型的属性，是否进行考虑的各种实现。
 *
 * @author meicanhua
 * @create 2018-01-08 下午2:48
 **/
public class BeanCopyHelper {

    private static Logger logger = LoggerFactory.getLogger(BeanCopyHelper.class);

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        List<String> emptyNames = new ArrayList();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String[] getNotNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        List<String> ignoreNames = new ArrayList();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null)
                ignoreNames.add(pd.getName());
        }
        String[] result = new String[ignoreNames.size()];
        return ignoreNames.toArray(result);
    }

    public static String[] getNotEmptyPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        List<String> ignoreNames = new ArrayList();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (!ObjectUtils.isEmpty(srcValue))
                ignoreNames.add(pd.getName());
        }
        String[] result = new String[ignoreNames.size()];
        return ignoreNames.toArray(result);
    }

    /**
     * 拷贝源对象中非空属性值， i.e. 忽略源对象中为NUll的属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * 从源对象中拷贝目标对象的非空属性值，i.e:
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNotNullPropertyNames(target));
    }


}