/**
 * 
 */
package org.hotpotmaterial.code.exception;

/**
 * @author wenxing
 *
 */
public class CodeCommonException extends RuntimeException {
  
  /**
   * 
   */
  private static final long serialVersionUID = -7090277986807518L;

  /**
   * 异常消息构造方法
   * 
   * @param string
   */
  public CodeCommonException(String string) {
    super(string);
  }

  /**
   * 异常消息构造方法
   * 
   * @param string
   * @param e
   */
  public CodeCommonException(String string, Exception e) {
    super(string, e);
  }

}
