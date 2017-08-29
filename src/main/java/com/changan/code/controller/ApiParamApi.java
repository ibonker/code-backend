package com.changan.code.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.changan.anywhere.common.mvc.page.rest.response.ResultDTO;
import com.changan.code.dto.ResultOfApiParamDTO;
import com.changan.code.entity.ApiParamPO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author xuyufeng
 *
 */
public interface ApiParamApi {


  @ApiOperation(value = "保存apiParam", notes = "保存apiParam", response = ResultOfApiParamDTO.class,
      tags = {"ApiParam"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiParamDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiParamDTO.class)})
  @RequestMapping(value = "/ApiParam/Save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> ApiObjSavePost(
      @ApiParam(value = "参数列表") @RequestBody List<ApiParamPO> apiParams,
      @ApiParam(value = "方法id") @RequestParam String apiObjId);

  @ApiOperation(value = "查询指定apiObj所有的apiParam", notes = "查询指定apiObj所有的apiParam",
      response = ResultOfApiParamDTO.class, tags = {"ApiParam"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiParamDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiParamDTO.class)})
  @RequestMapping(value = "/ApiParams/{apiObjId}/Show", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> ApiObjAllShowPost(
      @ApiParam(value = "apiObjId") @PathVariable(value = "apiObjId", required = true) String apiObjId);
}
