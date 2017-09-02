package com.changan.code.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfTransferObjFieldDTO;
import com.changan.code.entity.TransferObjFieldPO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * TransferObjField Api
 * 
 * @author xuyufeng
 *
 */
@Api(value = "transferObjField", description = "the TransferObjField API")
@RequestMapping(value = "/codegen/api/v1")
public interface TransferObjFieldApi {

  @ApiOperation(value = "根据id获取DTO属性", notes = "根据id获取DTO属性",
      response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
  @RequestMapping(value = "/transferobjfield/{id}/show", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> transferObjFieldShowGet(
      @ApiParam(value = "id") @PathVariable(value = "id", required = true) String id);

  @ApiOperation(value = "删除DTO属性", notes = "删除DTO属性", response = ResultOfTransferObjFieldDTO.class,
      tags = {"TransferObjField"})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
  @RequestMapping(value = "/transferObjField/{id}/Delete", produces = {"application/json"},
      method = RequestMethod.DELETE)
  ResponseEntity<ResultDTO> transferObjFieldDelete(
      @ApiParam(value = "id", required = true) @PathVariable(value = "id", required = true) String id);

  @ApiOperation(value = "保存DTO属性", notes = "保存DTO属性", response = ResultOfTransferObjFieldDTO.class,
      tags = {"TransferObjField"})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
  @RequestMapping(value = "/transferobjfield/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> transferObjFieldSavePost(
      @ApiParam(value = "transferObjField", required = true) @RequestBody TransferObjFieldPO transferObjField);

  @ApiOperation(value = "查询所有DTO属性", notes = "查询所有DTO属性",
      response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
  @RequestMapping(value = "/transferObjFields/{transferObjId}/Show",
      produces = {"application/json"}, method = RequestMethod.GET)
  ResponseEntity<ResultDTO> transferObjFieldAllShowGet(
      @ApiParam(value = "transferObjId") @PathVariable(value = "transferObjId", required = true) String transferObjId);

  @ApiOperation(value = "批量添加DTO属性", notes = "批量添加DTO属性",
      response = ResultOfTransferObjFieldDTO.class, tags = {"TransferObjField"})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfTransferObjFieldDTO.class),
      @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfTransferObjFieldDTO.class)})
  @RequestMapping(value = "/transferobjfields/Save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> transferObjFielSaveAllPost(
      @ApiParam(value = "transferObjFields", required = true) @RequestBody List<TransferObjFieldPO> transferObjFields);
}
