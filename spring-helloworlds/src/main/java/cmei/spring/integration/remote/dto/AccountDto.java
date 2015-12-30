package cmei.spring.integration.remote.dto;

import java.io.Serializable;

public class AccountDto implements Serializable{

	private static final long serialVersionUID = -6934673060016735451L;
	private String aid;
	
	private int accountType;
	
	public void setAid(String aid){
		this.aid=aid;
	}

	public String getAid(){
		return this.aid;
	}
	
	public void setAccountType(int type){
		this.accountType=type;
	}
	
	public int getAccountType(){
		return this.accountType;
	}
}
