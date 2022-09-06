package ru.clevertec.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.clevertec.exception.RepositoryException;
import ru.clevertec.util.PropertiesUtil;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "ru.clevertec")
@EnableJpaRepositories("ru.clevertec.repository")
@EnableTransactionManagement
public class CheckConfiguration {

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(PropertiesUtil.getYamlProperties().getJdbc().getDriver());
            dataSource.setJdbcUrl(PropertiesUtil.getYamlProperties().getJdbc().getUrl());
            dataSource.setUser(PropertiesUtil.getYamlProperties().getJdbc().getUsername());
            dataSource.setPassword(PropertiesUtil.getYamlProperties().getJdbc().getPassword());
            return dataSource;
        } catch (PropertyVetoException e) {
            throw new RepositoryException("Connection not found");
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("ru.clevertec.entity");
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect",
                PropertiesUtil.getYamlProperties().getHibernate().getDialect());
        properties.setProperty("hibernate.show_sql",
                PropertiesUtil.getYamlProperties().getHibernate().getShowSql());
        properties.setProperty("hibernate.format_sql",
                PropertiesUtil.getYamlProperties().getHibernate().getFormatSql());
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(properties);
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
