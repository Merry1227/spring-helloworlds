package cmei.demologs;

import org.apache.log4j.BasicConfigurator;

import org.apache.log4j.PropertyConfigurator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by canhuamei on 12/22/16.
 */
public class Log4jDemo {
    Logger logger= LoggerFactory.getLogger(this.getClass());


    /**
     * 如果将log4j.properties或者log4j.xml防止 class 顶层目录下，则会自动加载
     * 但一般项目为方便配置管理，会将其放入项目外的配置文件中。
     */
    public static void initConfigure(){

        //BasicConfigurator.configure();#默认配置，控制台输出
        // PropertyConfigurator.configureAndWatch();//配置文件修改，无需重启

        String CONF_LOG_PATH="logs/log4j.properties";

        String currentPath = System.getProperty("user.dir");

        String confPath = currentPath + File.separator + CONF_LOG_PATH;

        if(new File(confPath).exists()){
            PropertyConfigurator.configure(confPath);
        } else {
            PropertyConfigurator.configure(Log4jDemo.class.getClassLoader().getResource(CONF_LOG_PATH));
        }

    }



    public static void main(String[] args){

        initConfigure();

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
