package cmei.spring.integration.remote.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cmei.spring.integration.remote.api.IAccountService;
import cmei.spring.integration.remote.dto.AccountDto;
import cmei.spring.integration.remote.dto.AccountADto;
import cmei.spring.integration.remote.dto.AccountBDto;

@Service("accountServiceImpl")
public class AccountServiceImpl implements IAccountService{

	@Override
	public List<AccountDto> getAllAccounts() {
		List<AccountDto> accounts=new ArrayList<AccountDto>();
		
		//fake data
		AccountADto accountA=new AccountADto();
		accountA.setAid("aid1");
		accountA.setAccountType(1);
		accountA.setA("a");
		accounts.add(accountA);
		
		AccountBDto accountB=new AccountBDto();
		accountB.setAid("aid2");
		accountB.setAccountType(2);
		accountB.setB("b");
		accounts.add(accountB);
		
		return accounts;
	}

}
