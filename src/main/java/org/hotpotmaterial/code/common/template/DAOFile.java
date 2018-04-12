package org.hotpotmaterial.code.common.template;

public enum DAOFile {
  dao("service/dao.xml"),
  mapper("service/mapper.xml");
  
  private String path;
  
  private DAOFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
