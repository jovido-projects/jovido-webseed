package biz.jovido.webseed.test.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager

import javax.sql.DataSource

@ComponentScans([
        @ComponentScan('biz.jovido.webseed.model'),
        @ComponentScan('biz.jovido.webseed.service'),])
@EnableJpaRepositories('biz.jovido.webseed.repository')
@EnableJpaAuditing
class ModelTestConfiguration {

    @Autowired
    Environment environment

    @Bean
    DataSource dataSource() {
//        new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .build()

        def ds = new DriverManagerDataSource()
        ds.driverClassName = 'com.mysql.jdbc.Driver'
        ds.url = 'jdbc:mysql://localhost/webseed'
        ds.username = 'root'
        ds.password = ''

        ds
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws Exception {
        def localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean()
        localContainerEntityManagerFactoryBean.setDataSource(dataSource)

        localContainerEntityManagerFactoryBean.setPackagesToScan('biz.jovido.webseed.model')

        def jpaVendorAdapter = new HibernateJpaVendorAdapter()
        jpaVendorAdapter.showSql = true
//        jpaVendorAdapter.databasePlatform = 'org.hibernate.dialect.H2Dialect'
        jpaVendorAdapter.databasePlatform = 'org.hibernate.dialect.MySQL5Dialect'

        localContainerEntityManagerFactoryBean.jpaVendorAdapter = jpaVendorAdapter

        def jpaProperties = new Properties()
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update")
//        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
        jpaProperties.setProperty("hibernate.dialect", 'org.hibernate.dialect.MySQL5Dialect')
        jpaProperties.setProperty("hibernate.show_sql", "true")
        localContainerEntityManagerFactoryBean.jpaProperties = jpaProperties

        localContainerEntityManagerFactoryBean
    }

    @Bean
    PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) throws Exception {
        def transactionManager = new JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory.object

        transactionManager
    }
}