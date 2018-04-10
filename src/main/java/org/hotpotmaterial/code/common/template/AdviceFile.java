package org.hotpotmaterial.code.common.template;

public enum AdviceFile {

  advice("mapper/advice.xml");
  
  private String path;
  
  private AdviceFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
