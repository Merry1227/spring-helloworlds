package cmei.spring.threadmanager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloMain {
	public static void main(String args[]){
		ApplicationContext context=new ClassPathXmlApplicationContext("contexts/task.xml");
		//helloTask(context);
		asyncTask(context);
		System.out.println("done.");
	}
	
	
	public static void helloTask(ApplicationContext context){
		HelloTaskStarter helloexecutor=(HelloTaskStarter) context.getBean("helloTaskStarter");
		helloexecutor.execute();
	}
	
	public static void asyncTask(ApplicationContext context){
		AsyncTask asyncTask=(AsyncTask) context.getBean("asyncTask");
		long start=System.currentTimeMillis();
		System.out.println("start:"+start);
		Future<AsyncReturn> rs=asyncTask.doSomething(2);
		System.out.println ("return:"+(System.currentTimeMillis()-start));
		try {
			System.out.println(rs.get().toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("return-asyncR:"+(System.currentTimeMillis()-start));
		System.out.println("helloword!");
	}

}
