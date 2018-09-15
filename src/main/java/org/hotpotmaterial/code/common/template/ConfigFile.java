/**
 * 
 */
package org.hotpotmaterial.code.common.template;

/**
 * @author wenxing
 *
 */
public enum ConfigFile {
  
  application("config/application.xml"), 
  applicationYml("config/applicationYaml.xml"), 
  datasourceConfig("config/datasourceConfiguration.xml"),
  banner("config/banner.xml"),
  corsConfig("config/corsConfiguration.xml"),
  metaConfiguration("config/metaConfiguration.xml"),
  buildGradle("gradle/build.xml"), 
  propertiesGradle("gradle/property.xml"), 
  sonar("gradle/sonar.xml"),
  dockerfile("gradle/dockerfile.xml"), 
  gitlab_ci("gradle/gitlab-ci.xml"), 
  gitignore("gradle/gitignore.xml"),
  swaggerConfig("config/swaggerConfiguration.xml"),
  basecontroller("controller/controllerBase.xml"),
  commonException("exception/commonException.xml"),
  baseEntity("common/baseEntity.xml"),
  commonConstants("common/constants.xml"),
  restStatus("common/restStatus.xml"),
  servicesController("titanServices/controller/ServicesController.xml"),
  servicesDTO("titanServices/dto/ResultServiceDTO.xml");
  
  private String path;
  
  private ConfigFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
}
