package cmei.spring.integration.remote;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cmei.spring.integration.remote.api.IAccountService;

public class RemoteClientMain {
	public static void main(String args[]){
		
		//ApplicationContext context=new ClassPathXmlApplicationContext("contexts/remote-http-client.xml");
		
		ApplicationContext context=new ClassPathXmlApplicationContext("contexts/remote-rmi-client.xml");
		
		IAccountService accountService=(IAccountService)context.getBean("accountService");
		System.out.println(accountService.getAllAccounts().size());
	}
}
