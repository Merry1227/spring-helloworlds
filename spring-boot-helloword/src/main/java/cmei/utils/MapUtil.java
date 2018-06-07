package cmei.utils;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * MapUtil
 *
 * @author meicanhua
 * @create 2017-06-22 下午7:45
 **/
public class MapUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);


    public static Map<String, ImmutableTriple<String, String, Boolean>> diffMap(Map<String, Object> oldMap, Map<String, Object> newMap, Collection<String> diffKeys) {
        Map<String, ImmutableTriple<String, String, Boolean>> diffResult = new HashMap<>(diffKeys.size());
        boolean allSame = true;
        for(String key : diffKeys) {
            Object value1 = oldMap.getOrDefault(key, "");
            Object value2 = newMap.getOrDefault(key, "");
            if(!value1.equals(value2)) {
                allSame=false;
                diffResult.put(key, ImmutableTriple.of(String.valueOf(value1), String.valueOf(value2), false));
            }
        }
        logger.info("diffMap", diffResult);
        if(allSame) {
            return null;
        }
        return diffResult;
    }

}