/**
 * 
 */
package org.hotpotmaterial.code.dto;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
public class RelationAnnotation {
  
  private String relationType;
  private String mappedBy = null;
  private boolean isCollection;
  private String attrName;
  private String attrPOName;
  private String columnName;
  private String refColumnName;
  
  public boolean getIsCollection() {
    return isCollection;
  }
  
}
