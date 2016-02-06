package cmei.spring.transaction;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TraMain {
	public static void main(String[] args){
	
		Resource res=new ClassPathResource("contexts/transaction.xml");
		BeanFactory fac=new XmlBeanFactory(res);
		TraOffer traOffer=(TraOffer)fac.getBean("traOffer");
		traOffer.useOffer();	
	}

}
