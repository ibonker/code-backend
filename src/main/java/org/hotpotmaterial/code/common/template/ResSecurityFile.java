/**
 * 
 */
package org.hotpotmaterial.code.common.template;

/**
 * @author Administrator
 *
 */
public enum ResSecurityFile {

  HtFuncController("resSecurity/controller/HtFuncController.xml"),
  HtRoleController("resSecurity/controller/HtRoleController.xml"),
  HtUserController("resSecurity/controller/HtUserController.xml"),
  
  FuncPOMapper("resSecurity/dao/FuncPOMapper.xml"),
  RolePOMapper("resSecurity/dao/RolePOMapper.xml"),
  UserPOMapper("resSecurity/dao/UserPOMapper.xml"),
  
  CurrentUserDTO("resSecurity/dto/CurrentUserDTO.xml"),
  HotpotUserSeniorDTO("resSecurity/dto/HotpotUserSeniorDTO.xml"),
  LoginResponseDTO("resSecurity/dto/LoginResponseDTO.xml"),
  ResultDTO("resSecurity/dto/ResultDTO.xml"),
  ResultOfUserSeniorDTO("resSecurity/dto/ResultOfUserSeniorDTO.xml"),
  UserInfo("resSecurity/dto/UserInfo.xml"),
  
  HotpotFuncPOMapper("resSecurity/mapping/HotpotFuncPOMapper.xml"),
  HotpotRolePOMapper("resSecurity/mapping/HotpotRolePOMapper.xml"),
  HotpotUserPOMapper("resSecurity/mapping/HotpotUserPOMapper.xml"),
  
  HotpotFunctionPO("resSecurity/entity/HotpotFunctionPO.xml"),
  HotpotOrganizationPO("resSecurity/entity/HotpotOrganizationPO.xml"),
  HotpotRolePO("resSecurity/entity/HotpotRolePO.xml"),
  HotpotUserPO("resSecurity/entity/HotpotUserPO.xml"),
  
  AuthenLogoutFilter("resSecurity/filter/AuthenLogoutFilter.xml"),
  AuthenticationFilter("resSecurity/filter/AuthenticationFilter.xml"),
  LoginFilter("resSecurity/filter/LoginFilter.xml"),
  RescenterUserProvider("resSecurity/filter/RescenterUserProvider.xml"),
  UnauthorizedEntryPoint("resSecurity/filter/UnauthorizedEntryPoint.xml"),
  
  IFuncService("resSecurity/service/IFuncService.xml"),
  IRoleService("resSecurity/service/IRoleService.xml"),
  IUserService("resSecurity/service/IUserService.xml"),
  IUserTokenService("resSecurity/service/IUserTokenService.xml"),
  FuncServiceImpl("resSecurity/service/FuncServiceImpl.xml"),
  RoleServiceImpl("resSecurity/service/RoleServiceImpl.xml"),
  UserDetailsServiceImpl("resSecurity/service/UserDetailsServiceImpl.xml"),
  UserServiceImpl("resSecurity/service/UserServiceImpl.xml"),
  UserTokenServiceImpl("resSecurity/service/UserTokenServiceImpl.xml"),

  //initsql("resSecurity/initsql.xml"),
  
  WebSecurityConfig("resSecurity/WebSecurityConfig.xml");
  
  private String path;
  
  private ResSecurityFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}
