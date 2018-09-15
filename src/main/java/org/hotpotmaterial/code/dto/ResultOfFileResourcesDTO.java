package org.hotpotmaterial.code.dto;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.FileResourcesPO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

 /**
  * @author Hotpotmaterial-Code2
  *  new.titancode.test.FileResourcesPO详情实体
  */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ResultOfFileResourcesDTO extends ResultDTO{

	@JsonProperty(value = "fileResources")
	@JsonPropertyDescription("file_resources对象")				
	@ApiModelProperty(value = "file_resources对象")
	private FileResourcesPO fileResources;
	
	@JsonProperty(value = "url")
	@JsonPropertyDescription("描述上传文件的地址")				
	@ApiModelProperty(value = "描述上传文件的地址")
	private String url;

	public ResultOfFileResourcesDTO fileResources (FileResourcesPO fileResources){
	  this.fileResources = fileResources;
	  return this;
	}
	
	public ResultOfFileResourcesDTO fileInfo (String url){
		this.url = url;
		return this;
	}
}