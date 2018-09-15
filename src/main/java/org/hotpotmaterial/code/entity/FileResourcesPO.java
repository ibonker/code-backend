package org.hotpotmaterial.code.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hotpotmaterial-Code2 实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "file_resources")
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class FileResourcesPO extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Size(max = 255)
	@Column(name = "path")
	@JsonProperty(value = "path")
	@JsonPropertyDescription("文件上传的真实路径")
	private String path;

	@Size(max = 255)
	@Column(name = "small")
	@JsonProperty(value = "small")
	@JsonPropertyDescription("缩略图的生成路径")
	private String small;

	@Size(max = 255)
	@Column(name = "filename")
	@JsonProperty(value = "filename")
	@JsonPropertyDescription("文件上传前的真实名字")
	private String filename;

	@Size(max = 255)
	@Column(name = "uploadname")
	@JsonProperty(value = "uploadname")
	@JsonPropertyDescription("上传到本地服务器的文件名字")
	private String uploadname;

	@Size(max = 255)
	@Column(name = "user_id")
	@JsonProperty(value = "userId")
	@JsonPropertyDescription("文件上传的用户")
	private String userId;
	
}