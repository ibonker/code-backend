package pers.fj.staffmanage.controller.base;

import pers.fj.staffmanage.exception.CodeCommonException;
import pers.fj.staffmanage.common.RestStatus;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ValidationSchemaFactoryWrapperExtend;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

/**
 * 控制器基础类，一些基础的方法可以写在此处供控制器子类调用
 * @author Hotpotmaterial-Code2
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
      throw new CodeCommonException(RestStatus.RESULT_SYSTEM_ERROR.code(),
          RestStatus.RESULT_SYSTEM_ERROR.message());
    }
    return visitor.finalSchema();
  }

}