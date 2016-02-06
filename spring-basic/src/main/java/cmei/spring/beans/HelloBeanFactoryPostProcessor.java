package cmei.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class HelloBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
			throws BeansException {
		System.out.println("called before all beans instantiate:postProcessBeanFactory in BeanFactoryPostProcessor");
		System.out.println(arg0.getBeanDefinitionNames()+" "+arg0.getBeanDefinitionCount());
	}

}
