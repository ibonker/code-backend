package org.hotpotmaterial.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "version")
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class VersionPO extends BaseEntity {
	
	@Column(name = "flag")
	@JsonProperty("flag")
	private String flag;//前后端版本标识  0:前端  1:后端
	
	@Column(name = "version")
	@JsonProperty("version")
	private String version;//版本
	
	@Column(name = "descriptions")
	@JsonProperty("descriptions")
	private String versionDescription;//版本描述
	
	@Transient
	@JsonProperty("description")
	private String[] description;//分割的多条版本信息
	
	  /**
	   * 更新属性
	   * 
	   * @param versionPO
	   * @return
	   */
	  public VersionPO updateAttrs(VersionPO versionPO) {
	    this.flag = versionPO.getFlag();
	    this.version = versionPO.getVersion();
	    this.description = versionPO.getDescription();
	    return this;
	  }
}
