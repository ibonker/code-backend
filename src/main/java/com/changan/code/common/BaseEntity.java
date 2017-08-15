/**
 * 
 */
package com.changan.code.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import com.changan.anywhere.common.persistence.json.JsonDateSerializer;
import com.changan.anywhere.common.utils.IdGen;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * @author wenxing
 *
 */
@Data
public class BaseEntity implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -59244562124078745L;

  @JsonProperty("id")
  private String id;
  
  @JsonProperty("createdAt")
  @JsonSerialize(using = JsonDateSerializer.class)
  protected LocalDateTime createdAt;
  
  @JsonProperty("updatedAt")
  @JsonSerialize(using = JsonDateSerializer.class)
  protected LocalDateTime updatedAt;

  @JsonProperty("delFlag")
  private String delFlag = Constants.DATA_IS_NORMAL;
  
  /**
   * 插入之前执行方法，需要手动调用
   */
  public void preInsert(){
      // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
      if (this.isNew()){
          setId(IdGen.uuid());
      }
      this.updatedAt = LocalDateTime.now();
      this.createdAt = this.updatedAt;
  }
  
  /**
   * 更新之前执行方法，需要手动调用
   */
  public void preUpdate(){
      this.updatedAt = LocalDateTime.now();
  }
  
  /**
   * 判断是否新数据
   * @return
   */
  public boolean isNew() {
    return StringUtils.isEmpty(getId());
}

}
