package cmei.spring.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {
	
	
	public static void main(String[] args){
		//LoyaltyActivityHandler loyaltyActivityHander=new LoyaltyActivityHandler();
		
		//StandardEvaluationContext simpleContext=new StandardEvaluationContext(loyaltyActivityHander);
		StandardEvaluationContext simpleContext=new StandardEvaluationContext();
		simpleContext.setBeanResolver(new MyBeanResolver());
		
		simpleContext.setVariable("parameter_plan_per_user_occurrence", 11);
		simpleContext.setVariable("parameter_plan_total_points",100);
		simpleContext.setVariable("threshold_plan_per_user_limit",10);
		simpleContext.setVariable("threshold_plan_total_points_limit",5000);
		String spel="@handler.underPlanLimit(#parameter_plan_per_user_occurrence,#threshold_plan_per_user_limit)"+
		"+@handler.underPlanPoints(#parameter_plan_total_points,#threshold_plan_total_points_limit)";
		//String spel="underPlanLimit(#parameter_plan_per_user_occurrence,#threshold_plan_per_user_limit)+underPlanPoints(#parameter_plan_total_points,#threshold_plan_total_points_limit)";
		spel=spel.replaceAll("@handler","@loyaltyHandler");
		ExpressionParser parser=new SpelExpressionParser();
		Expression exp=parser.parseExpression(spel);
		Object message=exp.getValue(simpleContext);
		System.out.println(message);
	}
	
	
	
}


