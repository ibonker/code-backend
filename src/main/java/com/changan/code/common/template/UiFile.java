/**
 * 
 */
package com.changan.code.common.template;

/**
 * @author wenxing
 *
 */
public enum UiFile {
  
  controllerUi("uiconfig/controllerUi.xml"),
  uiconfigDto("uiconfig/uiconfigDto.xml"),
  uiconfigService("uiconfig/uiconfigService.xml"),
  uiconfigServiceImpl("uiconfig/uiconfigServiceImpl.xml"),
  uiconfigRequestDto("uiconfig/uiconfigRequestDto.xml");
  
  private String path;
  
  private UiFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}
