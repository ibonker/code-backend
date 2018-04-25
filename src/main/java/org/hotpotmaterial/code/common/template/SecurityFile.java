/**
 * 
 */
package org.hotpotmaterial.code.common.template;

/**
 * @author Administrator
 *
 */
public enum SecurityFile {

  HtFuncController("security/controller/HtFuncController.xml"),
  HtOrgController("security/controller/HtOrgController.xml"),
  HtRoleController("security/controller/HtRoleController.xml"),
  HtUserController("security/controller/HtUserController.xml"),
  FuncPOMapper("security/dao/FuncPOMapper.xml"),
  OrgPOMapper("security/dao/OrgPOMapper.xml"),
  RolePOMapper("security/dao/RolePOMapper.xml"),
  UserPOMapper("security/dao/UserPOMapper.xml"),
  CurrentUserDTO("security/dto/CurrentUserDTO.xml"),
  HotpotFuncPOMapper("security/mapping/HotpotFuncPOMapper.xml"),
  HotpotOrgPOMapper("security/mapping/HotpotOrgPOMapper.xml"),
  HotpotRolePOMapper("security/mapping/HotpotRolePOMapper.xml"),
  HotpotUserPOMapper("security/mapping/HotpotUserPOMapper.xml"),
  HotpotUserSeniorDTO("security/dto/HotpotUserSeniorDTO.xml"),
  LoginResponseDTO("security/dto/LoginResponseDTO.xml"),
  ResultOfHotpotOrganizationDTO("security/dto/ResultOfHotpotOrganizationDTO.xml"),
  ResultOfUserSeniorDTO("security/dto/ResultOfUserSeniorDTO.xml"),
  UserInfo("security/dto/UserInfo.xml"),
  HotpotFunctionPO("security/entity/HotpotFunctionPO.xml"),
  HotpotOrganizationPO("security/entity/HotpotOrganizationPO.xml"),
  HotpotOrgUserPO("security/entity/HotpotOrgUserPO.xml"),
  HotpotOrgUserPOKey("security/entity/HotpotOrgUserPOKey.xml"),
  HotpotRoleFunctionPO("security/entity/HotpotRoleFunctionPO.xml"),
  HotpotRolePO("security/entity/HotpotRolePO.xml"),
  HotpotRoleUserPO("security/entity/HotpotRoleUserPO.xml"),
  HotpotUserPO("security/entity/HotpotUserPO.xml"),
  AuthenLogoutFilter("security/filter/AuthenLogoutFilter.xml"),
  AuthenticationFilter("security/filter/AuthenticationFilter.xml"),
  LoginFilter("security/filter/LoginFilter.xml"),
  UnauthorizedEntryPoint("security/filter/UnauthorizedEntryPoint.xml"),
  IFuncService("security/service/IFuncService.xml"),
  IOrgService("security/service/IOrgService.xml"),
  IRoleService("security/service/IRoleService.xml"),
  IUserService("security/service/IUserService.xml"),
  IUserTokenService("security/service/IUserTokenService.xml"),
  FuncServiceImpl("security/service/FuncServiceImpl.xml"),
  OrgServiceImpl("security/service/OrgServiceImpl.xml"),
  RoleServiceImpl("security/service/RoleServiceImpl.xml"),
  UserDetailsServiceImpl("security/service/UserDetailsServiceImpl.xml"),
  UserServiceImpl("security/service/UserServiceImpl.xml"),
  UserTokenServiceImpl("security/service/UserTokenServiceImpl.xml"),
  initsql("security/initsql.xml"),
  WebSecurityConfig("security/WebSecurityConfig.xml");
  
  private String path;
  
  private SecurityFile(String path) {
    this.path = path;
  }
  
  public String getPath() {
    return this.path;
  }
  
}
