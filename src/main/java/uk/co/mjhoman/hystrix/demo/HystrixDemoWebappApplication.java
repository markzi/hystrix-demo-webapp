
package uk.co.mjhoman.hystrix.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.netflix.hystrix.HystrixCommandProperties;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
@EnableCircuitBreaker
@Configurable
@EnableHystrixDashboard
public class HystrixDemoWebappApplication extends WebMvcConfigurerAdapter implements ApplicationContextAware
{

    public static final String CHARACTER_ENCODING = "UTF-8";

    private ApplicationContext applicationContext;

    public HystrixDemoWebappApplication()
    {
        super();
    }

    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(HystrixDemoWebappApplication.class, args);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public TemplateResolver templateResolver()
    {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine()
    {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }
}
