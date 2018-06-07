package cmei.id;

import java.util.UUID;

/**
 * DefaultGenerator:并发量不打情况下，默认用UUID， 产生
 *
 * @author meicanhua
 * @create 2017-11-23 下午5:27
 **/
public class DefaultGenerator implements GUIDGenerator {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}