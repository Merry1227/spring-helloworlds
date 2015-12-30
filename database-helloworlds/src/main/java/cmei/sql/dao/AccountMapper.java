package cmei.sql.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {
	@Select("SELECT * FROM account WHERE aid=#{aid}")
	Account getAccountByID(@Param("aid") long accountID);
}
