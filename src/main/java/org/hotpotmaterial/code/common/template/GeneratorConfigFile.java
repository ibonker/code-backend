package org.hotpotmaterial.code.common.template;

public enum GeneratorConfigFile {
  generatorConfigFile("service/generatorConfig.xml");
  
  private String path;
  
  private GeneratorConfigFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
