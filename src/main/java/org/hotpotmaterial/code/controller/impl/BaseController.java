/**
 * 
 */
package org.hotpotmaterial.code.controller.impl;

import org.hotpotmaterial.code.exception.CodeCommonException;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wenxing
 *
 */
@Slf4j
public class BaseController {
  
  public UserInfo getUser() {   
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();
    try {
      return (UserInfo) auth.getPrincipal();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return null;
  }

}
