/**
 * 
 */
package com.changan.code.common.template;

/**
 * @author wenxing
 *
 */
public enum ConfigFile {
  
  application("config/application.xml"), 
  applicationYml("config/applicationYaml.xml"), 
  datasourceConfig("config/datasourceConfiguration.xml"), 
  buildGradle("gradle/build.xml"), 
  propertiesGradle("gradle/property.xml"), 
  mvcConfig("config/mvcConfiguration.xml");
  
  private String path;
  
  private ConfigFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
