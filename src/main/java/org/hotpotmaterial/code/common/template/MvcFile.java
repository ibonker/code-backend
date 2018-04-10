/**
 * 
 */
package org.hotpotmaterial.code.common.template;

/**
 * @author wenxing
 *
 */
public enum MvcFile {
  
  controllerApi("controller/controllerApi.xml"), 
  controllerImpl("controller/controllerImpl.xml"),
  controllerBase("controller/controllerBase.xml"),
  controllerAdvice("controller/controllerAdvice.xml"),
  serviceIF("service/serviceIF.xml"), 
  serviceImpl("service/serviceImpl.xml");
  
  private String path;
  
  private MvcFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}
