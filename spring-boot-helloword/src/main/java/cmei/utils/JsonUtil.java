package cmei.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON字串相关公共操作, 对象序列化, 反序列化等
 *
 * @author meicanhua
 * @create 2017-03-04 下午11:22
 **/
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);


    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper objectMapperWithoutNull = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapperWithoutNull.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapperWithoutNull.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 解析Json字符串为指定类型的对象
     *
     * @param json  要解析的Json字符串
     * @param clazz 对应的对象类型
     * @param <T>   泛型类型
     * @return 指定类型的对象
     */
    public static <T> T parseToObject(String json, Class<T> clazz) {
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("JsonUtil.parseToObject", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析指定的对象为指定类型的对象
     *
     * @param obj   要解析的对象
     * @param clazz 对应的对象类型
     * @param <T>   泛型类型
     * @return 指定类型的对象
     */
    public static <T> T parseToObject(Object obj, Class<T> clazz) {
        if (obj == null) return null;
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(obj), clazz);
        } catch (Exception e) {
            logger.error("JsonUtil.parseToObject", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析Json字符串为JsonNode对象
     *
     * @param json 要解析的Json字符串
     * @return JsonNode对象
     */
    public static JsonNode parseToJsonNode(String json) {
        if (json == null) return null;

        try {
            return objectMapper.readValue(json, JsonNode.class);
        } catch (IOException e) {
            logger.error("JsonUtil.parseToJsonNode", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析root下指定的Key对应的Json为指定对象
     *
     * @param json  要解析的Json字符串
     * @param key   指定解析数据的Key
     * @param clazz 指定类型
     * @param <T>   泛型类型
     * @return 指定类型的对象
     */
    public static <T> T parseFromRoot(String json, String key, Class<T> clazz) {
        if (json == null) return null;

        try {
            JsonNode jn = objectMapper.readValue(json, JsonNode.class);

            if (jn == null) return null;

            return objectMapper.treeToValue(jn.get(key), clazz);

        } catch (IOException e) {
            logger.error("JsonUtil.parseFromRoot", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析root下指定的Key对应的JsonNode为指定对象
     *
     * @param jsonNode 要解析的JsonNode对象
     * @param key      指定解析数据的Key
     * @param clazz    指定类型
     * @param <T>      泛型类型
     * @return
     */
    public static <T> T parseFromRoot(JsonNode jsonNode, String key, Class<T> clazz) {
        if (jsonNode == null) return null;

        JsonNode jn = jsonNode.get(key);
        if (jn == null) return null;
        try {
            return objectMapper.treeToValue(jn, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析json array成List<T>
     *
     * @param json  json字符串
     * @param clazz List内部对象类型
     * @return
     */
    public static <T> List<T> parse2List(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz));
        } catch (IOException e) {
            logger.error("JsonUtil.parse2List", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析json array成List<Map<String, Object>
     *
     * @param json json字符串
     * @return
     */
    public static List<Map<String, Object>> parse2ListMap(String json) {
        try {
            return objectMapper.readValue(json,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (IOException e) {
            logger.error("JsonUtil.parse2ListMap", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析Json字符串为一个Map对象
     *
     * @param json 要解析的Json字符串
     * @return Map对象
     */
    public static Map<String, Object> parse2Map(String json) {
        try {
            return objectMapper.readValue(json,
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (IOException e) {
            logger.error("JsonUtil.parse2Map", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化一个Java对象为Json字符串
     *
     * @param object Java对象
     * @return Json字符串
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("JsonUtil.toJson", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化一个Java对象为Json字符串不包含null的字段
     *
     * @param object Java对象
     * @return Json字符串
     */
    public static String toJsonWithoutNull(Object object) {
        try {
            return objectMapperWithoutNull.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("JsonUtil.toJsonWithoutNull", e);
            throw new RuntimeException(e);
        }
    }
}
