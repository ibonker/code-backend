/**
 * 
 */
package org.hotpotmaterial.code.config.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Component
@ConfigurationProperties(prefix = "apis")
@Data
public class ApiProperties {
  
  private List<String> baseType;
  
  private List<String> arrayType;
  
  private GeneratedDescptionModel generatedModal;
  
}
