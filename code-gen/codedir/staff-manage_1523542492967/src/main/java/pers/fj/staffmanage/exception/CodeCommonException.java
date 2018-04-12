package pers.fj.staffmanage.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一异常，用于controller advice统一处理
 * @author Hotpotmaterial-Code2
 */
public class CodeCommonException extends RuntimeException {
  
  /**
   * 序列化
   */
  private static final long serialVersionUID = -7090277986807518L;

  /**
   * 返回码
   */
  @Getter
  @Setter
  private String code;
  
  /**
   * 说明
   */
  @Getter
  @Setter
  private String message;
  
  /**
   * 异常消息构造方法
   * 
   * @param string
   */
  public CodeCommonException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  /**
   * 异常消息构造方法
   * 
   * @param string
   * @param e
   */
  public CodeCommonException(String code, String message, Exception e) {
    super(message, e);
    this.code = code;
    this.message = message;
  }

}