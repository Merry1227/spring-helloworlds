package cmei.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class HelloBean implements BeanNameAware,BeanFactoryAware,InitializingBean,DisposableBean{
	private String msg="";
	private String id;
	private BeanFactory beanFactory;
	private static String ClassName="HelloBean";
	
	{
		System.out.println(msg);
		System.out.println("instantiate initialization");
	}
	
	static{
		System.out.println(ClassName);
		System.out.println("static initialization");
	}
	
	
	
	public HelloBean(){
		this.msg="msg";
		System.out.println("bean instantiation:HelloBean()");
	}
	
	public void setMsg(String msg){
		System.out.println("DI:HelloBean:setMsg()");
		this.msg=msg;
	}
	public void sayHello(){
		System.out.println(msg);
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("setBeanName:"+name);
		this.id=name;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("setBeanFactory"+beanFactory.toString());
		this.beanFactory=beanFactory;
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Disposable Bean{'s destroy()");	
	}
	
	public void _destroy(){
		System.out.println("custom Bean{'s destroy()");	
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Initializating Bean's afterPropertiesSet()");
	}
	
	public void _init(){
		System.out.println("custom Initializating Bean");
	}
	
	
}
