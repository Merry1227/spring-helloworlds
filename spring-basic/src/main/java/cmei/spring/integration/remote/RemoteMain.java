package cmei.spring.integration.remote;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RemoteMain {
	public static void main(String args[]){	
//		ApplicationContext context=new ClassPathXmlApplicationContext("contexts/remote-http-server2.xml");
//		System.out.println("http start...");
		
		ApplicationContext context=new ClassPathXmlApplicationContext("contexts/remote-rmi-server.xml");
		System.out.println("rmi start...");
	}
}
