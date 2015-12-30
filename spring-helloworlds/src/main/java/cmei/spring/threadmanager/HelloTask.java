package cmei.spring.threadmanager;

public class HelloTask extends Thread{
	
	private long id;
	
	public HelloTask(long id){
		this.id=id;
		super.setName("HelloTask"+id);
	}

	@Override
	public void run() {
		System.out.println(this.getName()+"is runing");
	}
	
}
