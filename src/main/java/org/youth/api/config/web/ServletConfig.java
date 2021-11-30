package org.youth.api.config.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class ServletConfig implements WebMvcConfigurer {

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//	}

//	@Bean
//	public FilterRegistrationBean<MultipartFilter> registrationMultipartFilter(){
//		FilterRegistrationBean<MultipartFilter> registrationBean = new FilterRegistrationBean<>();
//		registrationBean.setFilter(new MultipartFilter());
//		registrationBean.setOrder(1);
//		registrationBean.addUrlPatterns("/*");
//		
//		return registrationBean;
//	}

	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> getFilterRegistrationBean() {

		FilterRegistrationBean<XssEscapeServletFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new XssEscapeServletFilter());
		registrationBean.setOrder(1);
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}

}
