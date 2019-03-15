package com.rauOhon.frontLine.cmmn.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class Context_Web implements WebApplicationInitializer {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		setServlet(servletContext);
		
		setCharEncodingFilter(servletContext);
		
	}

	private void setServlet(ServletContext servletContext) {
		log.info(">>>>>>>>>>setServlet");
		
		Class<?>[] servletClasses = new Class[] { Context_Servlet.class };
		
		AnnotationConfigWebApplicationContext servletContextClass
			= new AnnotationConfigWebApplicationContext();
		
		servletContextClass.register(servletClasses);
		
		ServletRegistration.Dynamic dispatcher 
			= servletContext.addServlet("dispatcher", new DispatcherServlet(servletContextClass));
		
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.do");
		
	}

	private void setCharEncodingFilter(ServletContext servletContext) {
		log.info(">>>>>>>>>>setCharEncodingFilter");
		
		FilterRegistration charEncodingFilterReg
			= servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
		charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
		charEncodingFilterReg.setInitParameter("forceEncoding", "true");
		charEncodingFilterReg.addMappingForUrlPatterns(null, true, "*.do");
		
	}
}
