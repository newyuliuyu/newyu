package com.newyu.fx;

import com.github.pagehelper.PageInterceptor;
import com.newyu.domain.exam.*;
import com.newyu.utils.db.DatasourceProperties;
import com.newyu.utils.db.InitDataSource;
import com.newyu.utils.id.IdGenerator;
import com.newyu.utils.mybatis.CodeEnumCodeHandler;
import com.newyu.utils.mybatis.NameEnumCodeHandler;
import com.newyu.utils.spring.SpringContextUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * ClassName: AppConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-22 下午1:20 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Configurable
@ComponentScan({"com.newyu.service.**"})
@EnableTransactionManagement
//@PropertySource("classpath:ds.properties")
//@MapperScan(basePackages = {"com.newyu.**.dao"}, sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class AppConfig {

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

    @Bean(name = "datasourceProperties")
    public DatasourceProperties datasourceProperties() {
        DatasourceProperties datasourceProperties = new DatasourceProperties();

        datasourceProperties.setUrl("jdbc:mysql://192.168.1.251:3306/newyu?autoReconnectForPools=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&rewriteBatchedStatements=true");
        datasourceProperties.setUsername("root");
        datasourceProperties.setPassword("newa_newc");

        datasourceProperties.setDriverClassName("com.mysql.jdbc.Driver");
        datasourceProperties.setInitialSize(5);
        datasourceProperties.setMinIdle(5);
        datasourceProperties.setMaxActive(20);
        datasourceProperties.setMaxWait(60000);
        datasourceProperties.setTimeBetweenEvictionRunsMillis(60000);
        datasourceProperties.setMinEvictableIdleTimeMillis(300000);
        datasourceProperties.setValidationQuery("SELECT 1 FROM DUAL");
        datasourceProperties.setTestWhileIdle(true);
        datasourceProperties.setTestOnBorrow(false);
        datasourceProperties.setTestOnReturn(false);
        datasourceProperties.setPoolPreparedStatements(true);
        datasourceProperties.setMaxPoolPreparedStatementPerConnectionSize(20);
        datasourceProperties.setFilters("stat,wall,log4j");
        datasourceProperties.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        return datasourceProperties;
    }

    @Bean(name = "ds")
    public DataSource dataSource(DatasourceProperties datasourceProperties) {
        return InitDataSource.init(datasourceProperties);
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource ds) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(ds);
        return manager;
    }

    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource ds) throws Exception {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourceResolver.getResources("classpath*:com/newyu/**/dao/**/*.xml");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setMapperLocations(resources);

//        new NameEnumCodeHandler<GradeName>();

        TypeHandler[] typeHandlers = new TypeHandler[]{
                new NameEnumCodeHandler(GradeName.class),
                new CodeEnumCodeHandler(ExamState.class),
                new CodeEnumCodeHandler(ExamLevel.class),
                new CodeEnumCodeHandler(LearningSegment.class),
                new CodeEnumCodeHandler(Semester.class),
                new CodeEnumCodeHandler(WLType.class)
        };
        bean.setTypeHandlers(typeHandlers);

        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("param1", "value1");
        pageInterceptor.setProperties(properties);
        bean.setPlugins(new Interceptor[]{pageInterceptor});


        return bean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.newyu.**.dao.**");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }

}
