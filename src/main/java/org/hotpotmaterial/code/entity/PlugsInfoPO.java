package org.hotpotmaterial.code.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hotpotmaterial-Code2 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "plugs_info")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@JsonInclude(Include.NON_NULL)
public class PlugsInfoPO extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Size(max = 64)
	@Column(name = "pic_id")
	@JsonProperty(value = "picId")
	@JsonPropertyDescription("插件对应的图片id")
	private String picId;

	@Size(max = 255)
	@Column(name = "pic_name")
	@JsonProperty(value = "picName")
	@JsonPropertyDescription("图片名称")
	private String picName;

	@Size(max = 255)
	@Column(name = "download_url")
	@JsonProperty(value = "downloadUrl")
	@JsonPropertyDescription("插件下载地址")
	private String downloadUrl;

	@Size(max = 255)
	@Column(name = "plugs_name")
	@JsonProperty(value = "plugsName")
	@JsonPropertyDescription("插件名称")
	private String plugsName;
}