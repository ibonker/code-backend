/**
 * 
 */
package com.changan.code.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wenxing
 *
 */
public class ResultDTO {

  @JsonProperty("statusCode")
  private String statusCode = null;

  @JsonProperty("message")
  private String message = null;

  public ResultDTO statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * 返回编码
   * 
   * @return statusCode
   **/
  @ApiModelProperty(value = "返回编码")
  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public ResultDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * 返回信息
   * 
   * @return result
   **/
  @ApiModelProperty(value = "返回信息")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultDTO resultDTO = (ResultDTO) o;
    return Objects.equals(this.message, resultDTO.message)
        && Objects.equals(this.statusCode, resultDTO.statusCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, statusCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultDTO {\n");

    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
