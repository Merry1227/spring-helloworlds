package cmei.demologs;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * Created by canhuamei on 12/22/16.
 */
public class MyPatternLayout extends PatternLayout{

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new MyPatternParser(pattern);
    }

}


