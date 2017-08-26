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
 * 
 * @author xuyufeng
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "transfer_obj_field")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class TransferObjFieldPO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8102260394240350397L;
	
	@Column(name = "transfer_obj_id")
	@JsonProperty("transferObjId")
	private String transferObjId; //实体id

	@Column(name = "name")
	@JsonProperty("name")
	private String name; //属性名
	
	@Column(name = "type")
	@JsonProperty("type")
	private String type; //属性类型
	
	@Column(name = "format")
	@JsonProperty("format")
	private String format; //属性格式
	
	@Column(name = "description")
	@JsonProperty("description")
	private String description; //属性描述
	
	/**
	 * 可以更新的属性
	 * @param newTransferObjFieldPO
	 * @return
	 */
	public TransferObjFieldPO updateAttrs(TransferObjFieldPO newTransferObjFieldPO) {
		this.transferObjId = newTransferObjFieldPO.getTransferObjId();
		this.name = newTransferObjFieldPO.getName();
		this.type = newTransferObjFieldPO.getType();
		this.format = newTransferObjFieldPO.getFormat();
		this.description = newTransferObjFieldPO.getDescription();
		return this;
	}
}
