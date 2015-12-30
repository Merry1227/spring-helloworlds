package cmei.sql.dao;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

public class AccountService implements IAccountService{

	private IAccountDAO accountDAO1;
		
	private IAccountDAO accountDAO2;
	
	public void setAccountDAO1(IAccountDAO accountDAO1){
		this.accountDAO1=accountDAO1;
	}
	
	public IAccountDAO getAccountDAO1(){
		return this.accountDAO1;
	}
	
	public void setAccountDAO2(IAccountDAO accountDAO2){
		this.accountDAO2=accountDAO2;
	}
	
	public IAccountDAO getAccountDAO2(){
		return this.accountDAO2;
	}
	
	public void setAccountDAO2(){
		
	}
	
	public Account getAccount(long accountID){
		return accountDAO1.getAccountByID(accountID);
	}
	
	@Transactional(value="tx1",rollbackFor=RuntimeException.class)
	public void transfer(long ida,long idb,float amount){
	      boolean isSucess=false;
	      Account accountA=accountDAO1.getAccountByID(ida);
	      Account accountB=accountDAO2.getAccountByID(idb);
	      
	 	  if(accountA!=null){
	          float balance1=accountA.getBalance();
	    	  accountA.setBalance(balance1-amount);
	    	  isSucess=accountDAO1.update(accountA);
	      }
	      if(true)	{      
	    	  //throw new IOException("usSuccess1");
	    	  throw new RuntimeException("unSuccess1");
	      }
	      if(isSucess&&accountB!=null){
	    	  float balance2=accountB.getBalance();
	    	  accountB.setBalance(balance2+amount);
	    	  isSucess=accountDAO2.update(accountB);
	      }else{
	    	  isSucess=false;
	      }
	     
	      if(!isSucess){
	    	  throw new RuntimeException("unSuccess3");
	      }
	      
	}
	
	public static void main(String args[]){
		
		Resource res=new ClassPathResource("contexts/transaction.xml");
		//BeanFactory fac=new XmlBeanFactory(res);
		
		GenericApplicationContext context=new GenericApplicationContext();
		XmlBeanDefinitionReader xmlReader=new XmlBeanDefinitionReader(context);
		xmlReader.loadBeanDefinitions(res);
		context.refresh();
		AccountService  accountService=(AccountService)context.getBean("accountService");	
		System.out.println(accountService.getAccount(1L).getBalance());
		try {
			accountService.transfer(1L, 1L,1.0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(accountService.getAccount(1L).getBalance());
		
	}
	
}
