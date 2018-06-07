package cmei.utils;

import cmei.id.GUIDGeneratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * LogTracer
 *
 * @author meicanhua
 * @create 2017-12-15 下午5:59
 **/
public class LogTracer {

    public static final String LOGGER_MDC_TRACE_ID = "traceId";

    public static final String LOGGER_MDC_USER_ID = "userId";


    private static final Logger logger = LoggerFactory.getLogger(LogTracer.class);

    public static void setTraceId() {
        String traceId = getTraceId();
        if (StringUtils.isEmpty(traceId)) {
            traceId = GUIDGeneratorFactory.getDefaultIDGenerator().generateId();
        }
        MDC.put(LOGGER_MDC_TRACE_ID, traceId);
        logger.info("traceId:{} is set", traceId);
    }

    public static void setTraceId(String traceId) {
        if(!StringUtils.isEmpty(traceId)) {
            MDC.put(LOGGER_MDC_TRACE_ID, traceId);
            logger.info("traceId:{} is set", traceId);
        } else {
            logger.warn("Can't set empty traceId!");
        }
    }

    public static  void setUserId(String userId) {
        MDC.put(LogTracer.LOGGER_MDC_USER_ID, userId);
    }

    public static void removeTraceId() {
        MDC.remove(LOGGER_MDC_TRACE_ID);
    }

    public static void removeUserId() {
        MDC.remove(LogTracer.LOGGER_MDC_USER_ID);
    }

    public static String getTraceId() {
        return MDC.get(LOGGER_MDC_TRACE_ID);
    }


}