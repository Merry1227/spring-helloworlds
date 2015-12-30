package cmei.spring.beans;

import java.security.MessageDigest;

import org.springframework.beans.factory.FactoryBean;

public class HelloFactoryBean implements FactoryBean<MessageDigest>{
	private static final String DEFAULT_ALGORITH="MD5";
	
	private static String algorithm=DEFAULT_ALGORITH;

	@Override
	public MessageDigest getObject() throws Exception {
		return MessageDigest.getInstance(algorithm);
		//this.algorithm;
	}

	@Override
	public Class getObjectType() {
		return MessageDigest.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setAlgorithm(String algorithm){
		this.algorithm=algorithm;
	}
}
