package org.youth.api.config.persistance;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages= "org.youth.api.mybatis.*", sqlSessionFactoryRef = "mybatisSqlSessionFactory")
public class DataSourceConfig {
	
	
	@Bean(name="mybatisSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource")DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mapper/*.xml"));
		
		return sqlSessionFactory.getObject();
	}
	
}
