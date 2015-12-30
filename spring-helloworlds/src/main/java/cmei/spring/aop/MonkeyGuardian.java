package cmei.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
/**
 * 
 * @author cmei
 *
 */

@Aspect
@Order(22)
public class MonkeyGuardian {
	
	String name1;
	
	@Pointcut("execution(* cmei.spring.aop.Monkey.stealPeaches(..))")
	private void foundMonkey(){}
	
	@Before("foundMonkey()")
	public void foundBefore(){
		this.name1="name";
		System.out.println(" Daemon found monkey is in the gardon...");
	}

	//@AfterReturning("foundMonkey()&& args(name,..)")
	@AfterReturning(pointcut="execution(* cmei.spring.aop.Monkey.stealPeaches(..))&& args(name)",returning="retVal")
	public void foundAfter(String name,Object retVal){
		System.out.println(" Daemon cought the moken and asked its name is "+name+"...");
		System.out.println(" name"+this.name1+"return:"+retVal);
	}
	
	@Around("execution (* cmei.spring.aop.Monkey.stealPeaches(..))")
	public long aroundMethod(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("MonkeyGuardian--around begin----print args:");
		Object[] args=pjp.getArgs();
		for(Object arg:args){
			System.out.print(" "+arg.toString());
		}
		
		long ret=(Long) pjp.proceed();
		
		System.out.println("MonkeyGuardian--around after----handle return:"+ret);	
		return ret;
	}
	
	
	

}
