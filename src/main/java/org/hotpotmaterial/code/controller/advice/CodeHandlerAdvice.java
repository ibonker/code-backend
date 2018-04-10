/**
 * 
 */
package org.hotpotmaterial.code.controller.advice;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.exception.CodeCommonException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wenxing
 *
 */
@ControllerAdvice
@Slf4j
public class CodeHandlerAdvice {
  
  @ExceptionHandler(value = CodeCommonException.class)
  public ResponseEntity<ResultDTO> exception(CodeCommonException exception) {
    String errorMsg;
    String code = Constants.EXCEPTION_API_CODE;
    log.error("**** 发生异常 ****", exception);
    errorMsg = exception.getMessage();
    ResponseEntity<ResultDTO> resultDto = new ResponseEntity<ResultDTO>(
        new ResultDTO().statusCode(code).message(errorMsg), HttpStatus.OK);
    return resultDto;
  }
  
  @ExceptionHandler(value = DataAccessException.class)
  public ResponseEntity<ResultDTO> msqlException(DataAccessException exception) {
    String errorMsg;
    String code = Constants.EXCEPTION_API_CODE;
    errorMsg = "操作失败";
    ResponseEntity<ResultDTO> resultDto = new ResponseEntity<ResultDTO>(
        new ResultDTO().statusCode(code).message(errorMsg), HttpStatus.OK);
    return resultDto;
  }

}
