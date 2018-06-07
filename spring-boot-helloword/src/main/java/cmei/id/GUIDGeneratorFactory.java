package cmei.id;

/**
 * GUIDGeneratorFactory
 *
 * @author meicanhua
 * @create 2017-11-23 下午5:33
 **/
public class GUIDGeneratorFactory {

    private static final GUIDGenerator DEFAULT_UUID_GENERATOR = new DefaultGenerator();

    public static GUIDGenerator getDefaultIDGenerator() {
        return DEFAULT_UUID_GENERATOR;
    }
}