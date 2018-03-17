package com.changan.code.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.request.PageDTO;
import com.changan.anywhere.common.mvc.rest.basic.ResultDTO;
import com.changan.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import com.changan.code.dto.ResultDictDTO;
import com.changan.code.entity.DictTypePO;
import com.changan.code.entity.DictValuePO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Dict", description = "the Dict API")
@RequestMapping(value = "/codegen/api/v1")
public interface DictApi {
  
  @ApiOperation(value = "查询所有DictType以及DictVlaue", notes = "查询所有DictType以及DictVlaue", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/dict_values/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
  ResponseEntity<ResultDTO> DictPost(@ApiParam(value = "version", required = true) @RequestParam int version);
  
  @ApiOperation(value = "新增DictType", notes = "新增DictType", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
  ResponseEntity<ResultDTO> DictTypePost(@ApiParam(value = "dictType", required = true) @RequestBody DictTypePO dictType);
  
  @ApiOperation(value = "删除DictType", notes = "删除DictType", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{dictTypeId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> DictTypeDelete(@ApiParam(value = "dictTypeId", required = true) @PathVariable String dictTypeId);
  
  @ApiOperation(value = "修改DictType", notes = "修改DictType", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{dictTypeId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> DictTypePut(
      @ApiParam(value = "dictTypeId", required = true) @PathVariable String dictTypeId,
      @ApiParam(value = "dictType", required = true) @RequestBody DictTypePO dictType);
  
  @ApiOperation(value = "查询DictType", notes = "查询DictType", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{dictTypeId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
  ResponseEntity<ResultDTO> DictTypeShow(@ApiParam(value = "dictTypeId", required = true) @PathVariable String dictTypeId);
  
  @ApiOperation(value = "DictType分页列表", notes = "DictType分页列表", response = ResultPageDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultPageDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/pages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
  ResponseEntity<ResultDTO> dictTypePagesPost(@ApiParam(value = "searchParams", required = true) @RequestBody PageDTO searchParams);
  
  @ApiOperation(value = "新增DictValue", notes = "新增DictValue", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{code}/dict_values", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
  ResponseEntity<ResultDTO> DictValuePost(
      @ApiParam(value = "code", required = true) @PathVariable String code ,
      @ApiParam(value = "dictValue", required = true) @RequestBody DictValuePO dictValue);
  

  @ApiOperation(value = "修改DictValue", notes = "修改DictValue", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{code}/dict_values/{dictValueId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.PUT)
  ResponseEntity<ResultDTO> DictValuePut(
      @ApiParam(value = "code", required = true) @PathVariable String code ,
      @ApiParam(value = "dictValueId", required = true) @PathVariable String dictValueId ,
      @ApiParam(value = "dictValue", required = true) @RequestBody DictValuePO dictValue);
  

  @ApiOperation(value = "删除DictValue", notes = "删除DictValue", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{code}/dict_values/{dictValueId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> DictValueDelete(
      @ApiParam(value = "code", required = true) @PathVariable String code,
      @ApiParam(value = "dictValueId", required = true) @PathVariable String dictValueId);
  
  @ApiOperation(value = "查询DictValue", notes = "查询DictValue", response = ResultDictDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDictDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{code}/dict_values/{dictValueId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
  ResponseEntity<ResultDTO> DictValueShow(
      @ApiParam(value = "code", required = true) @PathVariable String code ,
      @ApiParam(value = "dictValueId", required = true) @PathVariable String dictValueId);
  
  @ApiOperation(value = "实体ddemo.DictTypePO关联dict_value分页列表", notes = "实体ddemo.DictTypePO关联dict_value分页列表", response = ResultPageDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultPageDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/dict_type/{code}/dict_values/pages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
  ResponseEntity<ResultDTO> dictValuePagesPost(
      @ApiParam(value = "code", required = true) @PathVariable String code ,
      @ApiParam(value = "searchParams", required = true) @RequestBody PageDTO searchParams);
  
  @ApiOperation(value = "查询所有DictType", notes = "查询所有DictType", response = ResultPageDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultPageDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/table/{tableId}/dict_type", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.GET)
  ResponseEntity<ResultDTO> dictTypeAllGet(@PathVariable String tableId);
  
  @ApiOperation(value = "DictValue批量保存", notes = "DictValue批量保存", response = ResultPageDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultPageDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/table/{tableId}/dict_type/{dictCode}/dict_values", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
  ResponseEntity<ResultDTO> dictValuesPost(
      @ApiParam(value = "tableId", required = true)@PathVariable String tableId, 
      @ApiParam(value = "dictCode", required = true)@PathVariable String dictCode, 
      @ApiParam(value = "dictValues", required = true)@RequestBody ResultDictDTO dictTypeAndValue);
  
  @ApiOperation(value = "查询指定DictType下所有的DictValue", notes = "查询指定DictType下所有的DictValue", response = ResultPageDTO.class,
      tags = {"Dict"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultPageDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/table/{tableId}/dict_type/{dictCode}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,method = RequestMethod.POST)
  ResponseEntity<ResultDTO> findValues(
      @ApiParam(value = "tableId", required = true)@PathVariable String tableId, 
      @ApiParam(value = "dictCode", required = true)@PathVariable String dictCode);
}
