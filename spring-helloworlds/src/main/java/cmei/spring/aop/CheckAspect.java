package cmei.spring.aop;
import org.aspectj.lang.annotation.*;

@Aspect
public class CheckAspect {
	
	@Pointcut("execution(* cmei.spring.aop.UserManageImpl.save*(..))||execution(* cmei.spring.aop.UserManageImpl.del*(..))")
	private void allSaveMethod(){	
	}
	
	@Before("allSaveMethod()")
	public void checkUser(){
		System.out.println("============checkuser============");	
	}

}
