/**
 * 
 */
package com.changan.code.controller.impl;

import com.changan.code.exception.CodeCommonException;
import com.changan.code.extend.ValidationSchemaFactoryWrapperExtend;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

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

}
