package cmei.spring.aop;


public class UserManageImpl implements IUserManager{
	
	public void delUser(int id){
		System.out.println("+++++++++++delUser++++++++");
	}

	public String findUser(int id){
		System.out.println("+++++++++++++findUser+++++++");
		return null;
	}
	
	public void saveUser(String username,String password){
		System.out.println("+++++++++++++saveUser+++++++");
	}
	
	public void updateUser(int id){
		System.out.println("+++++++++++++updateUser+++++++");
	}	
}
