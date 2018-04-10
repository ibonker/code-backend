/**
 * 
 */
package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.code.entity.TableSeniorSlavePO;
import com.google.common.base.CaseFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
@AllArgsConstructor
public class SeniorDtoRelation {
  
  private String name; // 关系名
  
  private String desc;
  
  private List<SeniorDtoAttribute> attrs; // 实体字段列表
  
  private List<TableSeniorSlavePO> slaves; // 关联的从表信息
  
  public String getNameAttr() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
  }
  
}
