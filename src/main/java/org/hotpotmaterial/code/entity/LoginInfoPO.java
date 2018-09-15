/**
 * 
 */
package org.hotpotmaterial.code.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "login_info")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@JsonInclude(Include.NON_NULL)
public class LoginInfoPO extends BaseEntity {

  @Column(name = "user_id")
  @JsonProperty("userId")
  private String userId; // 登陆用户id

  @Column(name = "user_name")
  @JsonProperty("userName")
  private String userName; // 用户名称

  @Column(name = "ip")
  @JsonProperty("ip")
  private String ip; // 用户登陆IP地址

}
