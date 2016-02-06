package cmei.spring.aop;

public interface IUserManager {
	public void delUser(int id);
	
	public String findUser(int id);
	
	public void saveUser(String username,String password);
	
	public void updateUser(int id);
}
