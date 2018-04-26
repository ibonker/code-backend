/**
 * 
 */
package org.hotpotmaterial.code.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * @author Administrator
 *
 */
public class DatasourceInitUtils {

  public static void runSchemaScripts(String sqlproperties, List<String> schemas, String sqlname, DataSource datasource,
      DataSourceProperties properties, ApplicationContext applicationContext) {
    List<Resource> scripts =
        getScripts(sqlproperties, schemas, sqlname, properties, applicationContext);
    if (!scripts.isEmpty()) {
      runScripts(scripts, datasource, properties, applicationContext);
    }
  }

  private static void runScripts(List<Resource> resources, DataSource datasource,
      DataSourceProperties properties, ApplicationContext applicationContext) {
    if (resources.isEmpty()) {
      return;
    }
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.setContinueOnError(properties.isContinueOnError());
    populator.setSeparator(properties.getSeparator());
    if (properties.getSqlScriptEncoding() != null) {
      populator.setSqlScriptEncoding(properties.getSqlScriptEncoding().name());
    }
    for (Resource resource : resources) {
      populator.addScript(resource);
    }
    DatabasePopulatorUtils.execute(populator, datasource);
  }

  private static List<Resource> getScripts(String propertyName, List<String> resources,
      String fallback, DataSourceProperties properties, ApplicationContext applicationContext) {
    if (resources != null) {
      return getResources(propertyName, resources, true, applicationContext);
    }
    String platform = properties.getPlatform();
    List<String> fallbackResources = new ArrayList<String>();
    fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
    fallbackResources.add("classpath*:" + fallback + ".sql");
    return getResources(propertyName, fallbackResources, false, applicationContext);
  }

  private static List<Resource> getResources(String propertyName, List<String> locations,
      boolean validate, ApplicationContext applicationContext) {
    List<Resource> resources = new ArrayList<Resource>();
    for (String location : locations) {
      for (Resource resource : doGetResources(location, applicationContext)) {
        if (resource.exists()) {
          resources.add(resource);
        } else if (validate) {
          throw new ResourceNotFoundException(propertyName, resource);
        }
      }
    }
    return resources;
  }

  private static Resource[] doGetResources(String location, ApplicationContext applicationContext) {
    try {
      SortedResourcesFactoryBean factory =
          new SortedResourcesFactoryBean(applicationContext, Collections.singletonList(location));
      factory.afterPropertiesSet();
      return factory.getObject();
    } catch (Exception ex) {
      throw new IllegalStateException("Unable to load resources from " + location, ex);
    }
  }

}
