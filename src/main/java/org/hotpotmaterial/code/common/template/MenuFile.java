package org.hotpotmaterial.code.common.template;

/**
 * 
 * @author xuyufeng
 *
 */
public enum MenuFile {
  
  controllerMenu("menuconfig/controllerMenu.xml"),
  menuconfigDto("menuconfig/menuconfigDto.xml"),
  menuconfigService("menuconfig/menuconfigService.xml"),
  menuconfigServiceImpl("menuconfig/menuconfigServiceImpl.xml"),
  menuconfigRequestDto("menuconfig/menuconfigRequestDto.xml"),
  menuconfig("menuconfig/menucache.xml");
  
  private String path;
  
  private MenuFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}
