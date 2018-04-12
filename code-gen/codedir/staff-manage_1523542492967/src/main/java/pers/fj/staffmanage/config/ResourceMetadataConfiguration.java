package pers.fj.staffmanage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.hotpotmaterial.jsonmeta.ResourceMetaManager;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 元数据配置。该配置主要用于激活元数据信息，通过接口返回相应实体的信息如属性名，数据库最大的长度，数据类型等
 *
 * @author Hotpotmaterial-Code2
 */
@Configuration
public class ResourceMetadataConfiguration {
  
  // 注入jackson objectMapper
  @Autowired
  private ObjectMapper objectMapper;
  // 注入spring应用上下文  
  @Autowired
  private ApplicationContext context;
  
  // 创建bean
  @Bean
  public ResourceMetaManager resourceMetaManager() {
    return new ResourceMetaManager(objectMapper, context);
  }
  
}