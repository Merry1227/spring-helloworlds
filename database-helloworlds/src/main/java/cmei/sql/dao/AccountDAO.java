package cmei.sql.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;


public class AccountDAO extends SqlSessionDaoSupport implements IAccountDAO {	
	public Account getAccountByID(long accountID){
		return  getSqlSession().selectOne("Account.selectOne",accountID);
		//(Account) getSqlSession().getMapper(AccountMapper.class).getAccountByID(accountID);
	}
	
	public boolean update(Account account){
		int rows=getSqlSession().update("Account.update",account);
	    return rows>0;
	}

	public List<Account> getAllAccounts(long accountID) {
		return getSqlSession().selectList("Account.selectAll");
	}
	
}
