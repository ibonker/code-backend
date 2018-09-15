package org.hotpotmaterial.code.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.config.property.GenerateProperties;
import org.hotpotmaterial.code.controller.PartyProjectApi;
import org.hotpotmaterial.code.dto.ResultOfPartyProjectDTO;
import org.hotpotmaterial.code.entity.PartyProjectPO;
import org.hotpotmaterial.code.security.dto.HotpotUserSeniorDTO;
import org.hotpotmaterial.code.security.entity.HotpotOrganizationPO;
import org.hotpotmaterial.code.service.IPartyProjectService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;

/**
 * 版本查询
 * 
 * @author Administrator
 *
 */
@Controller
@Slf4j
public class PartyProjectConrtoller extends BaseController implements PartyProjectApi {

	@Autowired
	private IPartyProjectService partyprojectService;

	@Override
	public ResponseEntity<ResultDTO> partyprojectSavePost(@RequestBody List<PartyProjectPO> partyprojectPO) {
		partyprojectService.savePartyProject(partyprojectPO);
		return new ResponseEntity<>(new ResultOfPartyProjectDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResultDTO> deleteSavePost(@PathVariable String partyprojectId) {
		partyprojectService.deletePartyProject(partyprojectId);
		return new ResponseEntity<>(new ResultOfPartyProjectDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResultDTO> projectUserInfo(@PathVariable String projectId, @PathVariable String style) {
		ResultOfPartyProjectDTO result = partyprojectService.findUserInfoByProjectId(projectId, style, getUser());
		if (result == null) {
			return new ResponseEntity<>(new ResultOfPartyProjectDTO().message(RestStatus.RESULT_NO_DATA.message())
					.statusCode(RestStatus.RESULT_NO_DATA.code()), HttpStatus.OK);
		}
		return new ResponseEntity<>(
				result.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

}
