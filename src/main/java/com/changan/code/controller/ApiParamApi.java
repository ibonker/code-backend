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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ApiParam RESTful接口
 * @author xuyufeng
 *
 */
@Api(value = "apiParam", description = "the apiParam API")
@RequestMapping(value = "/codegen/api/v1")
public interface ApiParamApi {

  /**
   * 批量ApiParams
   * @param apiParams
   * @param ApiObjId
   * @return
   */
  @ApiOperation(value = "保存apiParam", notes = "保存apiParam", response = ResultDTO.class,
      tags = {"ApiParam"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultDTO.class)})
  @RequestMapping(value = "/apiparam/save", produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<ResultDTO> apiObjSavePost(
      @ApiParam(value = "参数列表") @RequestBody List<ApiParamPO> apiParams,
      @ApiParam(value = "apiObjId") @RequestParam String apiObjId);

  /**
   * 查询指定apiObj下所有的apiParam
   * @param apiObjId
   * @return
   */
  @ApiOperation(value = "查询指定apiObj所有的apiParam", notes = "查询指定apiObj所有的apiParam",
      response = ResultOfApiParamDTO.class, tags = {"ApiParam"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回操作成功信息", response = ResultOfApiParamDTO.class),
          @ApiResponse(code = 200, message = "返回错误信息", response = ResultOfApiParamDTO.class)})
  @RequestMapping(value = "/apiparams/{apiObjId}/show", produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<ResultDTO> apiObjAllShowGet(
      @ApiParam(value = "apiObjId") @PathVariable(value = "apiObjId", required = true) String apiObjId);
}
