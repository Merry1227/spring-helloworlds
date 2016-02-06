package cmei.spring.integration.remote.dto;

public class AccountBDto extends AccountDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -466891496674029640L;
	private String b;
	
	public void setB(String b){
		this.b=b;
	}

	public String getB(){
		return b;
	}
}
