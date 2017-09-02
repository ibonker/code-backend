package com.changan.code.common.template;

public enum GeneratorConfigFile {
  generatorConfigFile("mapper/generatorConfig.xml");
  
  private String path;
  
  private GeneratorConfigFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
