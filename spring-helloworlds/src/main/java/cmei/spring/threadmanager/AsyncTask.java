package cmei.spring.threadmanager;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;


@Service("asyncTask")
public class AsyncTask {
	
	
	@Async
	public void doSomething(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Async
	public Future<AsyncReturn> doSomething(long taskId){
		AsyncReturn asyncR=new AsyncReturn();
		asyncR.setTaskId(taskId);
		asyncR.setIsRunning(true);
		asyncR.setStart_time(System.currentTimeMillis());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		asyncR.setEnd_time(System.currentTimeMillis());
		asyncR.setIsRunning(false);
		return new AsyncResult<AsyncReturn>(asyncR);
	}
	
	

}

class AsyncReturn{
	private long taskId;
	private long start_time;
	private long end_time;
	private boolean isRunning;
	
	public void setTaskId(long taskId){
		this.taskId=taskId;
	}
	
	public long getTaskId(){
		return this.taskId;
	}
	
	public void setStart_time(long start_time){
		this.start_time=start_time;
	}
	
	public void setEnd_time(long end_time){
		this.end_time=end_time;
	}
	
	public long getStart_time(){
		return this.start_time;
	}
	
	public long getEnd_time(){
		return this.end_time;
	}
	
	public void setIsRunning(boolean isRunning){
		this.isRunning=isRunning;
	}
	
	public boolean getIsRunning(){
		return this.isRunning;
	}
	
	public String toString(){
		String s="task:"+taskId+" start at:"+this.start_time+" end at:"+this.end_time+" total time:"+(this.end_time-this.start_time)+ " isRunning:"+this.isRunning;
		return s;
	}
	
}
