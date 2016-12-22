package cmei.demologs;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;

/**
 * Created by canhuamei on 12/22/16.
 */
public class MyPatternParser extends PatternParser{

    public MyPatternParser(String pattern){
        super(pattern);
    }

    @Override
    protected void finalizeConverter(char formatChar) {
        PatternConverter pc = null;
        switch( formatChar )
        {
            case 'S':
                pc = new MyThrowablePatternConverter(formattingInfo);
                currentLiteral.setLength(0);
                addConverter(pc);
                break;
            default:
                super.finalizeConverter(formatChar);
        }
    }
}
