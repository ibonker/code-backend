package org.hotpotmaterial.code.dto;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.entity.ContextPO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Hotpotmaterial-Code2 new.titancode.test.ContextPO详情实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class ResultOfContextDTO extends ResultDTO {

	@JsonProperty(value = "context")
	@JsonPropertyDescription("context对象")
	@ApiModelProperty(value = "context对象")
	private ContextPO context;

	public ResultOfContextDTO context(ContextPO context) {
		this.context = context;
		return this;
	}
}