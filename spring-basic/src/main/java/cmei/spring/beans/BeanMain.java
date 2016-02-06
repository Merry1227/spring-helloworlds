package cmei.spring.beans;

import java.security.MessageDigest;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class BeanMain {	
	public static void main(String[] args){
		ApplicationContext context=new ClassPathXmlApplicationContext("contexts/bean.xml");
		HelloBean helloBean=(HelloBean)context.getBean("helloBean");
		helloBean.sayHello();
		
		System.out.println("***********************************");
		
		MessageDigest md5=(MessageDigest)context.getBean("md5");
		System.out.println(md5.getAlgorithm());
		MessageDigest sha=(MessageDigest)context.getBean("sha");
		System.out.println(md5.getAlgorithm());
		
		System.out.println("done.");
	}
	
	
}
