/**
 * 
 */
package com.changan.code.controller.impl;

import com.changan.anywhere.common.mvc.page.rest.response.ValidationSchemaFactoryWrapperExtend;
import com.changan.code.exception.CodeCommonException;
import com.changan.rescenter.auth.security.ResCenterUserDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.google.common.collect.Lists;

/**
 * @author wenxing
 *
 */
public class BaseController {

  /**
   * 获取json-schema
   * 
   * @param typeRef
   * @return
   */
  protected JsonSchema getJsonSchemaByJavaType(TypeReference<?> typeRef) {
    ValidationSchemaFactoryWrapperExtend visitor = new ValidationSchemaFactoryWrapperExtend();
    ObjectMapper mapper = new ObjectMapper();
    try {
      JavaType jt = mapper.getTypeFactory().constructType(typeRef);
      mapper.acceptJsonFormatVisitor(jt, visitor);
    } catch (JsonMappingException e) {
      throw new CodeCommonException("实体映射异常");
    }
    return visitor.finalSchema();
  }

  /**
   * 获取用户信息
   * 
   * @return
   */
  public ResCenterUserDetails getUser() {
    // SecurityContext ctx = SecurityContextHolder.getContext();
    // Authentication auth = ctx.getAuthentication();
    // try {
    // return (ResCenterUserDetails) auth.getPrincipal();
    // } catch (Exception e) {
    // throw new CodeCommonException("401");
    // }
    return new ResCenterUserDetails("67867", null, false, false, false, false, Lists.newArrayList(),
        null);
  }

}
