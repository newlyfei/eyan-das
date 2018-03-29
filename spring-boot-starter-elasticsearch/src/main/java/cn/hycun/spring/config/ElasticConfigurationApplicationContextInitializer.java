package cn.hycun.spring.config;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

public class ElasticConfigurationApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();
        String scan = env.getProperty("spring.extend.elastic.scan");
        if (scan != null) {
//            applicationContext.addBeanFactoryPostProcessor(scanner);
//            applicationContext.getBeanFactory().addBeanPostProcessor(scanner);
//            applicationContext.getBeanFactory().registerSingleton("annotationBean", scanner);
        }
    }
}
