package pers.fj.staffmanage.controller.uiconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import pers.fj.staffmanage.dto.uiconfig.UiConfigDTO;
import pers.fj.staffmanage.dto.uiconfig.RequestUiConfigDTO;
import pers.fj.staffmanage.service.uiconfig.IUiConfigService;
import pers.fj.staffmanage.common.RestStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiParam;

/**
 * 前端配置文件保存接口
 * @author Hotpotmaterial-Code2
 */
@Controller
@Api(value = "UiConfig", description = "the UiConfig API")
@RequestMapping(value = "/staffmanage/api/common")
public class UIConfigController {

  @Autowired
  private IUiConfigService uiConfigService;

  /**
   * ui配置获取接口
   * 
   * @return
   */
  @ApiOperation(value = "ui配置获取接口", notes = "ui配置获取接口", response = UiConfigDTO.class,
      tags = {"UIConfig"})
  @ApiResponses(
      value = {@ApiResponse(code = 200, message = "返回结果信息", response = UiConfigDTO.class),
          @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/uiconfigs", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
  public ResponseEntity<ResultDTO> uiConfigGet(
  @ApiParam(value = "版本号", required = false) @RequestParam(required = false) String version) {
    return new ResponseEntity<ResultDTO>(new UiConfigDTO().uimap(uiConfigService.getUiConfig(version))
        .message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
  }

  /**
   * ui配置保存接口
   * 
   * @param uiconfig
   * @return
   */
  @ApiOperation(value = "ui配置保存接口", notes = "ui配置保存接口", response = ResultDTO.class,
      tags = {"UIConfig"})
  @ApiResponses(value = {@ApiResponse(code = 200, message = "返回结果信息", response = ResultDTO.class),
      @ApiResponse(code = 401, message = "返回认证错误信息")})
  @RequestMapping(value = "/uiconfigs/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      method = RequestMethod.POST)
  public ResponseEntity<ResultDTO> uiConfigPost(@RequestBody RequestUiConfigDTO uiconfig) {
    uiConfigService.saveUiConfig(uiconfig.getData());
    return new ResponseEntity<ResultDTO>(
        new ResultDTO().message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()), 
        HttpStatus.OK);
  }

}