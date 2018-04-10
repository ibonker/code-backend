/**
 * 
 */
package org.hotpotmaterial.code.common.template;

/**
 * @author wenxing
 *
 */
public enum UiFile {
  
  controllerUi("uiconfig/controllerUi.xml"),
  uiconfigDto("uiconfig/uiconfigDto.xml"),
  uiconfigService("uiconfig/uiconfigService.xml"),
  uiconfigServiceImpl("uiconfig/uiconfigServiceImpl.xml"),
  uiconfigRequestDto("uiconfig/uiconfigRequestDto.xml"),
  uiconfig("uiconfig/uicache.xml");
  
  private String path;
  
  private UiFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}
