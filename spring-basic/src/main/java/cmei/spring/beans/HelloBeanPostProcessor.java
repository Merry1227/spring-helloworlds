package cmei.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 
 * @author cmei
 * @since 2012.07.26
 *
 */
public class HelloBeanPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		System.out.println("called after spring bean's initialization:postProcessAfterInitialization");
		System.out.println(arg0+" "+arg1);
		return arg0;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		System.out.println("called before spring bean's initialization:postProcessBeforeInitialization");
		System.out.println(arg0+" "+arg1);
		return arg0;
	}

}
