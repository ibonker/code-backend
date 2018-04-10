/**
 * 
 */
package org.hotpotmaterial.code.config.property;

import java.util.List;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
public class GeneratedDescptionModel {

  // 单表
  private List<ApiDescriptionModel> normalApiModels;
  // 高级关联查询
  private List<ApiDescriptionModel> seniorApiModels;
  // rest关联查询
  private List<ApiDescriptionModel> restApiModels;

}
