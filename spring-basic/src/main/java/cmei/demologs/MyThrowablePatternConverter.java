package cmei.demologs;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * Created by canhuamei on 12/22/16.
 */
public class MyThrowablePatternConverter extends PatternConverter{

    public MyThrowablePatternConverter(FormattingInfo fi){
        super(fi);
    }

    protected String convert(LoggingEvent event) {
        StringBuilder stackTraceInfo = new StringBuilder();
        ThrowableInformation information = event.getThrowableInformation();

        if (information != null) {
            String[] stringRep = information.getThrowableStrRep();
            // Only log the last line of the exception to save space
            if (stringRep.length > 0)
                stackTraceInfo.append(stringRep[0]);
                stackTraceInfo.append(stringRep[1]);
        }

        return stackTraceInfo.toString();
    }
}
