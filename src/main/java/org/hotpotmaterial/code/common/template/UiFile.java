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
  uiconfigDao("uiconfig/uiconfigDao.xml"),
  uiconfigMapper("uiconfig/uiconfigMapper.xml"),
  uiconfigService("uiconfig/uiconfigService.xml"),
  uiconfigServiceImpl("uiconfig/uiconfigServiceImpl.xml"),
  uiconfigDto("uiconfig/uiconfigDto.xml"),
  uiconfigRequestDto("uiconfig/uiconfigRequestDto.xml"),
  uicache("uiconfig/uicache.xml");
  
  private String path;
  
  private UiFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}
