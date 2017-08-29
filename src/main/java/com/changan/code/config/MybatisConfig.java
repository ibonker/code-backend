package com.changan.code.config;

import java.util.Properties;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInterceptor;

/**
 * 
 * @author wenxing
 *
 */
public class MybatisConfig {

  public Configuration mybatisConfig() {
    Configuration configuration = new Configuration();

    configuration.setCacheEnabled(true);
    configuration.setLazyLoadingEnabled(true);
    configuration.setAggressiveLazyLoading(true);
    configuration.setMultipleResultSetsEnabled(true);
    configuration.setUseColumnLabel(true);
    configuration.setUseGeneratedKeys(false);
    configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
    configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
    configuration.setMapUnderscoreToCamelCase(true);
    configuration.setLocalCacheScope(LocalCacheScope.SESSION);
    configuration.setJdbcTypeForNull(JdbcType.NULL);

    configuration.getTypeAliasRegistry().registerAlias("Page", Page.class);

    // PageHelper pager = new PageHelper();
    PageInterceptor pageInterceptor = new PageInterceptor();

    Properties p = new Properties();
    p.put("helperDialect", "mysql");
    p.put("offsetAsPageNum", "true");
    p.put("rowBoundsWithCount", "true");
    p.put("pageSizeZero", "true");
    p.put("reasonable", "true");

    pageInterceptor.setProperties(p);
    configuration.addInterceptor(pageInterceptor);

    return configuration;

  }

}
