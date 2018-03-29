package cn.hycun.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
@EnableConfigurationProperties(ElasticProperties.class)
@ComponentScan(basePackages = { "cn.hycun" }, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class) })
public class ElasticAutoConfiguration {
    @Autowired
    private ElasticProperties elasticProperties;

    @Bean
    public ElasticTransportClientFactoryBean transportClient() {
        ElasticTransportClientFactoryBean transportClientFactory = new ElasticTransportClientFactoryBean();
        transportClientFactory.setClusterName(elasticProperties.getClusterName());
        transportClientFactory.setHost(elasticProperties.getHost());
        transportClientFactory.setPort(elasticProperties.getPort());
        return transportClientFactory;
    }
}
