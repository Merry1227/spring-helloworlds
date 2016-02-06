package cmei.spring.integration.remote.api;

import java.util.List;

import cmei.spring.integration.remote.dto.AccountDto;

public interface IAccountService {
		List<AccountDto> getAllAccounts();
		
}
