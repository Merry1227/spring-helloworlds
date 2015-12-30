package cmei.sql.dao;

public class Account {
	private long aid;
	private String owner;
	private float balance;
	
	public Account(){
		
	}
	
	public Account(long aid){
		this.aid=aid;
	}
	
	public void setAid(long aid){
		this.aid=aid;
	}
	
	public long getAid(){
		return this.aid;
	}
	
	public String getOwner(){
		return this.owner;
	}
	
	
	public void setOwner(String owner){
		this.owner=owner;
	}
	
	public float getBalance(){
		return this.balance;
	}
	
	public void setBalance(float balance){
		this.balance=balance;
	}
}
