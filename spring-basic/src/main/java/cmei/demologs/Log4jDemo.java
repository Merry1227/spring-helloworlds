package cmei.demologs;

import org.apache.log4j.BasicConfigurator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.net.SyslogAppender;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.net.SocketAppender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by canhuamei on 12/22/16.
 */
public class Log4jDemo {
    Logger logger= LoggerFactory.getLogger(this.getClass());



    public static void main(String[] args){
        //BasicConfigurator.configure();#默认配置，控制台输出
        // PropertyConfigurator.configureAndWatch();//配置文件修改，无需重启

        //String currentPath = System.getProperty("user.dir")

        PropertyConfigurator.configure(Log4jDemo.class.getClassLoader().getResource("logs/log4j.properties"));
        Logger logger= LoggerFactory.getLogger("cmei.logger1");

        logger.debug("info1");



        Logger logger2=LoggerFactory.getLogger("cmei.logger2");
        logger2.info("logger2");

        try{
            throw new RuntimeException(" klllllded");

        }catch(Exception e){
            logger2.error(e.getMessage(),e);
        }

        Logger otherloggers=LoggerFactory.getLogger("others");
        otherloggers.warn("nowarning-中国");
    }
}
