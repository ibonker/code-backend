/**
 * 
 */
package com.changan.code.dto;

import java.util.List;

import com.changan.code.entity.ApiViewFormConfigPO;
import com.changan.code.entity.ApiViewPO;
import com.changan.code.entity.ApiViewTableConfigPO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author cyj
 *
 */
@Data
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RequestApiViewConfigDTO {

   private ApiViewPO apiViewPO; //api配置
   
   private List<ApiViewFormConfigPO> formConfigs; //表单配置
   
   private List<ApiViewTableConfigPO> tableConfigs; //表格配置
}
