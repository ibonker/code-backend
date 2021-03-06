package org.hotpotmaterial.code.security.controller;

import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.impl.BaseController;
import org.hotpotmaterial.code.security.dto.HotpotUserSeniorDTO;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.entity.HotpotOrganizationPO;
import org.hotpotmaterial.code.security.entity.HotpotRolePO;
import org.hotpotmaterial.code.security.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/roleMag")
public class HtRoleController extends BaseController{
  
  @Autowired
  private IRoleService roleService;
  
  /**
   * 查询所有角色
   * @param searchParams
   * @return
   */
  @PostMapping("/roles")
  @ResponseBody
  public ResponseEntity<ResultDTO> roles(@RequestBody PageDTO searchParams){
    return new ResponseEntity<ResultDTO>(roleService.getHotpotRoleList(searchParams)
        .message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 新增角色
   * @param hotpotRolePO
   * @return
   */
  @PostMapping("/role")
  @ResponseBody
  public ResponseEntity<ResultDTO> saveRole(@RequestBody HotpotRolePO hotpotRolePO){
    roleService.saveRole(hotpotRolePO, getUser());
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 更新角色
   * @param id
   * @param hotpotRolePO
   * @return
   */
  @PutMapping("/role/{id}")
  @ResponseBody
  public ResponseEntity<ResultDTO> updateRole(@PathVariable String id, @RequestBody HotpotRolePO hotpotRolePO){
    roleService.updateRole(id, hotpotRolePO, getUser());
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 删除角色
   * @param id
   * @return
   */
  @DeleteMapping("role/{id}")
  @ResponseBody
  public ResponseEntity<ResultDTO> deleteRole(@PathVariable String id){
    roleService.deleteRole(id);
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 角色获取对应目录
   * @param roleId
   * @return
   */
  @GetMapping("/{roleId}/functions")
  @ResponseBody
  public List<HotpotFunctionPO> roleFucntions(@PathVariable String roleId){
    return roleService.selectFuncByRoleId(roleId);
  }
  
  /**
   * 角色获取对应用户
   * @param roleId
   * @return
   */
  @GetMapping("/{roleId}/users")
  @ResponseBody
  public List<HotpotUserSeniorDTO> roleUsers(@PathVariable String roleId) {
    return roleService.getUsersByRoleId(roleId, getUser());
  }
  
  /**
   * 角色获取对应组织
   * @param roleId
   * @return
   */
  @GetMapping("/{roleId}/orgs")
  @ResponseBody
  public List<HotpotOrganizationPO> roleOrgs(@PathVariable String roleId) {
    return roleService.getOrgsByRoleId(roleId, getUser());
  }
  
  /**
   * 保存角色-功能
   * @param roleId
   * @param functionIds
   * @return
   */
  @PutMapping("/{roleId}/functions")
  @ResponseBody
  public ResponseEntity<ResultDTO> saveRoleFuncs(@PathVariable String roleId, @RequestBody List<String> functionIds){
    roleService.saveRoleFuncs(roleId, functionIds, getUser());
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 保存角色对应人员、组织
   * @param roleId
   * @param partyIds
   * @return
   */
  @PutMapping("/{roleId}/partys/{type}")
  @ResponseBody
  public ResponseEntity<ResultDTO> saveRolePartys(@PathVariable String roleId,@PathVariable String type, @RequestBody List<String> partyIds){
    roleService.saveRolePartys(roleId, type, partyIds, getUser());
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
  
  /**
   * 删除角色下的组织，人员
   * @param roleId
   * @param partyIds
   * @return
   */
  @PostMapping("/{roleId}/partys/delete")
  @ResponseBody
  public ResponseEntity<ResultDTO> deleteRoleParty(@PathVariable String roleId, @RequestBody List<String> partyIds){
    roleService.deleteRoleParty(roleId, partyIds);
    return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
        .statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }
}