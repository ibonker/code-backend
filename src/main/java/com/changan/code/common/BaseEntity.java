/**
 * 
 */
package com.changan.code.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.changan.anywhere.common.persistence.json.JsonDateSerializer;
import com.changan.anywhere.common.utils.IdGen;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * 数据库表实体基类
 * 
 * @author wenxing
 *
 */
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -59244562124078745L;

  @Id
  @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
  @GeneratedValue(generator = "uuidGenerator")
  @JsonProperty("id")
  private String id;
  
  @Column(name = "created_at", nullable = false)
  @JsonProperty("createdAt")
  @CreatedDate
  @JsonSerialize(using = JsonDateSerializer.class)
  protected LocalDateTime createdAt;
  
  @Column(name = "updated_at", nullable = false)
  @JsonProperty("updatedAt")
  @LastModifiedDate
  @JsonSerialize(using = JsonDateSerializer.class)
  protected LocalDateTime updatedAt;

  @Column(name = "del_flag")
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
