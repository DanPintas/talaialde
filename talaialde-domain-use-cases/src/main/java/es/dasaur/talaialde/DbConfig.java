package es.dasaur.talaialde;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:app-config/database-${spring.profiles.active}.properties")
@EnableTransactionManagement
public class DbConfig {

    protected static final String DATASOURCE_DRIVER = "datasource.driverClassName";
    protected static final String DATASOURCE_URL = "datasource.url";
    protected static final String DATASOURCE_USERNAME = "datasource.username";
    protected static final String DATASOURCE_PASSWORD = "datasource.password";
    
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_IMPORT_FILES = "hibernate.hbm2ddl.import_files";

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty(DATASOURCE_DRIVER))
                .url(env.getProperty(DATASOURCE_URL))
                .username(env.getProperty(DATASOURCE_USERNAME))
                .password(env.getProperty(DATASOURCE_PASSWORD))
                .build();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = 
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("es.dasaur.talaialde");

        Properties jpaProperties = new Properties();
        jpaProperties.put(HIBERNATE_DIALECT, 
                env.getRequiredProperty(HIBERNATE_DIALECT));
        jpaProperties.put(HIBERNATE_FORMAT_SQL, 
                env.getRequiredProperty(HIBERNATE_FORMAT_SQL));
        jpaProperties.put(HIBERNATE_HBM2DDL_AUTO, 
                env.getRequiredProperty(HIBERNATE_HBM2DDL_AUTO));
        jpaProperties.put(HIBERNATE_SHOW_SQL, 
                env.getRequiredProperty(HIBERNATE_SHOW_SQL));
        jpaProperties.put(HIBERNATE_IMPORT_FILES, 
                env.getRequiredProperty(HIBERNATE_IMPORT_FILES));
        jpaProperties.put("hibernate.ejb.naming_strategy",
                "org.hibernate.cfg.ImprovedNamingStrategy");

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
    
}
