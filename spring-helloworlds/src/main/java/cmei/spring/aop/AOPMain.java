package cmei.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPMain {
	public static void main(String[] args){
//	  main1();
	  main2();	
	}
	
	public static void main1(){
		ApplicationContext context=new ClassPathXmlApplicationContext("contexts/aopconfig.xml");
		
		Monkey monkey=(Monkey) context.getBean("monkey");
		                monkey.stealPeaches("laosun");     
	}
	
	public static void main2(){	  
		ApplicationContext context=new ClassPathXmlApplicationContext("contexts/aop.xml");
		IUserManager userManager=(IUserManager)context.getBean("userManager");
		userManager.saveUser("cat", "123");
		userManager.delUser(1);
		userManager.updateUser(1);    
	}
}
