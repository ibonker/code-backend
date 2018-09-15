package org.hotpotmaterial.code.security.controller;

import java.util.List;

import org.hotpotmaterial.code.controller.impl.BaseController;
import org.hotpotmaterial.code.security.entity.HotpotFunctionPO;
import org.hotpotmaterial.code.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/security")
public class HtUserController extends BaseController{
  
  @Autowired
  private IUserService userService;
  
  /**
   * 用户获取对应目录
   * @return
   */
  @GetMapping("/functions")
  @ResponseBody
  public List<HotpotFunctionPO> fucntions(){
    return userService.selectFuncByPartyIdList(getUser());
  }
  
}