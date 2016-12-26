package cmei.demologs;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by canhuamei on 12/23/16.
 */
public class LogBackDemo {

    /**
     * 若将logback.xml或者 logback-test.xml放入 class 顶层目录，则会自动加载。
     * 但一般项目为方便配置管理，会将配置文件一起置于项目之外。
     */
    public static void initConfigure(){
        String CONF_LOG_PATH="logs/logback.xml";

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            // Call context.reset() to clear any previous configuration, e.g. default
            // configuration. For multi-step configuration, omit calling context.reset().
            context.reset();
            configurator.doConfigure(LogBackDemo.class.getClassLoader().getResource(CONF_LOG_PATH));
        } catch (JoranException je) {
            je.printStackTrace();
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    public static void main(String[] args){

        initConfigure();
        //int i=0;
        //while(i<100) {
            Logger logger = LoggerFactory.getLogger("logback");
            logger.trace("trace rolling 2");

            logger.debug("debug rolling 1");

            logger.info(" infor rolling in logback");
        //    i++;
        //}

        Logger logger2=LoggerFactory.getLogger(LogBackDemo.class);
        logger2.info("root logger2 rolling 2");

        try{
            throw new RuntimeException(" klllllded");

        }catch(Exception e){
            logger2.error(e.getMessage(),e);
        }

        Logger otherloggers=LoggerFactory.getLogger("others");
        otherloggers.warn("nowarning-中国");
    }
}
