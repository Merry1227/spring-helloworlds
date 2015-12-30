package cmei.sql.dao;

import java.util.List;

public interface IAccountDAO {
	Account getAccountByID(long accountID);
	
	List<Account> getAllAccounts(long accountID);
	
	boolean update(Account account);
}
