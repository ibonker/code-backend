package org.hotpotmaterial.code.dto;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.DirectoryPO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Hotpotmaterial-Code2 new.titancode.test.DirectoryPO详情实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ResultOfDirectoryDTO extends ResultDTO {

	@JsonProperty(value = "directory")
	@JsonPropertyDescription("directory对象")
	@ApiModelProperty(value = "directory对象")
	private DirectoryPO directory;
	
	@JsonProperty(value = "directorys")
	@JsonPropertyDescription("directory对象集合")
	@ApiModelProperty(value = "directory对象集合")
	private List<DirectoryPO> directorys;

	public ResultOfDirectoryDTO directory(DirectoryPO directory) {
		this.directory = directory;
		return this;
	}
	
	public ResultOfDirectoryDTO directoryList(List<DirectoryPO> directorys) {
		this.directorys = directorys;
		return this;
	}
}