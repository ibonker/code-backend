package org.hotpotmaterial.code.common.template;

public enum DTOFile {
  dto("service/dto.xml");
  
  private String path;
  
  private DTOFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
