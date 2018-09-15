package org.hotpotmaterial.code.common.template;

/**
 * 
 * @author Administrator
 *
 */
public enum JPAFile {
  JPAservice("service/JPAservice.xml"),
  
  JPAserviceImpl("service/JPAserviceImpl.xml"),
  
  repository("service/repository.xml");
  
  private String path;
  
  private JPAFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
