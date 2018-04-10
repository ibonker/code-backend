package org.hotpotmaterial.code.common.template;

public enum DAOFile {
  dao("mapper/dao.xml"),
  mapper("mapper/mapper.xml");
  
  private String path;
  
  private DAOFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
