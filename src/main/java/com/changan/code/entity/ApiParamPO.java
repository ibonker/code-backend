/**
 * 
 */
package com.changan.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wenxing
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "api_param")
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class ApiParamPO extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3696572365031036657L;

	@Column(name = "api_obj_id")
	@JsonProperty("apiObjId")
	private String apiObjId; // api对象id

	@Column(name = "name")
	@JsonProperty("name")
	private String name; // 参数名

	@Column(name = "description")
	@JsonProperty("description")
	private String description; // 参数描述

	@Column(name = "form")
	@JsonProperty("form")
	private String form; // 归属表

	@Column(name = "is_required")
	@JsonProperty("isRequired")
	private String isRequired; // 归属表

	@Column(name = "type")
	@JsonProperty("type")
	private String type; // 参数类型

	@Column(name = "format")
	@JsonProperty("format")
	private String format; // 参数格式

	/**
	 * 可以更新的属性
	 * 
	 * @param newTransferObjFieldPO
	 * @return
	 */
	public ApiParamPO updateAttrs(ApiParamPO newApiParamPO) {
		this.apiObjId = newApiParamPO.getApiObjId();
		this.name = newApiParamPO.getName();
		this.description = newApiParamPO.getDescription();
		this.form = newApiParamPO.getForm();
		this.isRequired = newApiParamPO.getIsRequired();
		this.type = newApiParamPO.getType();
		this.format = newApiParamPO.getFormat();
		return this;
	}
}
