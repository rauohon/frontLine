package com.rauOhon.frontLine.cmmn.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.rauOhon.frontLine.cmmn.utils.SessionInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.rauOhon.frontLine" })
@EnableAsync
public class Context_Servlet extends WebMvcConfigurerAdapter{
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void configureDefaultServletHandling (DefaultServletHandlerConfigurer configurer) {
		log.info(">>>>>>>>>>configureDefaultServletHandling");
		super.configureDefaultServletHandling(configurer);
		configurer.enable();
	}

    @Bean
    public TilesConfigurer tilesConfigurer () {
		log.info(">>>>>>>>>>TilesConfigurer");
        final TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions(new String[] {"/WEB-INF/tiles/default-layout.xml"});
        configurer.setCheckRefresh(true);
        return configurer;
    }
     
    @Bean
    public UrlBasedViewResolver tilesViewResolver () {
		log.info(">>>>>>>>>>UrlBasedViewResolver");
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(0);
        return resolver;
    }
    
    @Bean
    public InternalResourceViewResolver viewResolver () {
		log.info(">>>>>>>>>>InternalResourceViewResolver");
    	InternalResourceViewResolver viewResolver
    		= new InternalResourceViewResolver();
    	viewResolver.setViewClass(JstlView.class);
    	viewResolver.setPrefix("/WEB-INF/jsp/");
    	viewResolver.setSuffix(".jsp");
    	viewResolver.setOrder(1);
    	return viewResolver;
    }
    
    @Bean
    public ParamCollectorArgumentResolver paramCollectorArgumentResolver () {
		log.info(">>>>>>>>>>ParamCollectorArgumentResolver");
    	ParamCollectorArgumentResolver pcar = new ParamCollectorArgumentResolver();
    	return pcar;
    }
	
    @Override
    public void addResourceHandlers (ResourceHandlerRegistry registry) {
		log.info(">>>>>>>>>>addResourceHandlers");
    	registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
    @Override
    public void addArgumentResolvers (List<HandlerMethodArgumentResolver> argumentResolvers) {
		log.info(">>>>>>>>>>addArgumentResolvers");
    	super.addArgumentResolvers(argumentResolvers);
    	argumentResolvers.add(new ParamCollectorArgumentResolver());
    }
    
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
    	log.info(">>>>>>>>>>addInterceptors");
    	registry.addInterceptor(new SessionInterceptor())
    		.addPathPatterns("/**")
    		.excludePathPatterns("/public/**");
    }
    
}
