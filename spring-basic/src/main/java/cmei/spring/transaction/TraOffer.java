package cmei.spring.transaction;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class TraOffer {
	int i=0;
	private TransactionTemplate transactionTemplate;

	public void useOffer(){
		transactionTemplate.execute(
				new TransactionCallback(){
					public Object doInTransaction(TransactionStatus ts){
						boolean isSuccess=updateStoreDB();
							if(isSuccess){
								isSuccess=updateGlobalDB();
								if(!isSuccess){
									ts.setRollbackOnly();
								}
							}
						return null;
					  }
				});		
	}
	
	
	private boolean updateStoreDB(){
		i=10;
		return true;
	}
	
	private boolean updateGlobalDB(){
		i=11;
		return false;
	}
}
