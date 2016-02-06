package cmei.spring.spel;

import java.util.Arrays;
import java.util.List;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

final class LoyaltyActivityHandler {
    static final List<String> EXPLANATIONS = Arrays.asList(
            "life time limitation reached",
            "plan limitation reached",
            "plan total points limitation reached");
    
    public long underLifetimeLimit(int parameter, int threshold) {
        boolean underLimit = (threshold == -1) || (parameter < threshold);
        return underLimit ? 0 : 1;
    }
    
    public long underPlanLimit(int parameter, int threshold) {
        boolean underLimit = (threshold == -1) || (parameter < threshold);
        return underLimit ? 0 : (1 << 1);
    }
    
    public long underPlanPoints(int parameter, int threshold) {
        boolean underLimit = (threshold == -1) || (parameter < threshold);
        return underLimit ? 0 : (1 << 2);
    }
}

class MyBeanResolver implements BeanResolver{
	LoyaltyActivityHandler loyaltyActivityHandler=new LoyaltyActivityHandler();
	public Object resolve(EvaluationContext context, String beanName)
			throws AccessException {
		if(beanName.equals("loyaltyHandler")){
			return loyaltyActivityHandler;
		}
		return null;
	}
	
}
