package cmei.spring.threadmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;


@Component("helloTaskStarter")
public class HelloTaskStarter {
	
         @Autowired
         TaskExecutor myExecutor;
         
         public void execute(){
             for(int i=0;i<5;i++){
            	 myExecutor.execute(new HelloTask(i));
             }
         }
}
