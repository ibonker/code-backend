/**
 * 
 */
package com.changan.code.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Component
@ConfigurationProperties(prefix = "generate")
@Data
public class GenerateProperties {
  
  private String projectPath;
  
  private String projectZipPath;
  
  private String mybatisGenlibsPath;
  
  private String projectUiPath;
  
  private String projectUiTempPath;
  
  private String entityZipPath;
  
  private String entityPath;
  
}
