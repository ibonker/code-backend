/**
 * 
 */
package org.hotpotmaterial.code.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import org.hotpotmaterial.code.common.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

/**
 * 数据库表实体基类
 * 
 * @author wenxing
 *
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -59244562124078745L;

  @Id
  @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
  @GeneratedValue(generator = "uuidGenerator")
  @JsonPropertyDescription("id")
  @JsonProperty(value = "id")
  protected String id;

  @Column(name = "created_at", nullable = false)
  @JsonPropertyDescription("创建时间")
  @JsonProperty(value = "createdAt", access = Access.READ_ONLY)
  @CreatedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  protected LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  @JsonPropertyDescription("更新时间")
  @JsonProperty(value = "updatedAt", access = Access.READ_ONLY)
  @LastModifiedDate
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  protected LocalDateTime updatedAt;

  @Column(name = "del_flag")
  @JsonPropertyDescription("删除标识")
  @JsonProperty(value = "delFlag", access = Access.READ_ONLY)
  protected String delFlag = Constants.DATA_IS_NORMAL;

  /**
   * 插入之前执行方法，需要手动调用
   */
//  public void preInsert() {
//    // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
//    if (this.isNew()) {
//      setId(IdGen.uuid());
//    }
//    this.updatedAt = LocalDateTime.now();
//    this.createdAt = this.updatedAt;
//  }

  /**
   * 更新之前执行方法，需要手动调用
   */
//  public void preUpdate() {
//    this.updatedAt = LocalDateTime.now();
//  }

  /**
   * 判断是否新数据
   * 
   * @return
   */
  @JsonIgnore
  public boolean isNew() {
    return StringUtils.isEmpty(getId());
  }

}
