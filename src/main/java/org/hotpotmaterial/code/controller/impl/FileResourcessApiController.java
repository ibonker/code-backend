package org.hotpotmaterial.code.controller.impl;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.controller.FileResourcessApi;
import org.hotpotmaterial.code.dto.ResultOfFileResourcesDTO;
import org.hotpotmaterial.code.entity.FileResourcesPO;
import org.hotpotmaterial.code.service.IFileResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 控制器实现类
 * 
 * @author Hotpotmaterial-Code2
 */
@Controller
public class FileResourcessApiController extends BaseController implements FileResourcessApi {

	// 注入业务bean - IFileResourcesService
	@Autowired
	private IFileResourcesService fileResourcesService;

	/**
	 * 实体new.titancode.test.FileResourcesPO详情
	 */
	@Override
	public ResponseEntity<ResultDTO> fileResourcessGET(@PathVariable(required = true) String fileResourcesId) {
		return new ResponseEntity<ResultDTO>(
				new ResultOfFileResourcesDTO().fileResources(fileResourcesService.findById(fileResourcesId))
						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 实体new.titancode.test.FileResourcesPO更新
	 */
	@Override
	public ResponseEntity<ResultDTO> fileResourcessPut(@PathVariable(required = true) String fileResourcesId,
			@RequestBody(required = true) FileResourcesPO fileResources) {
		fileResourcesService.updateFileResources(fileResourcesId, fileResources);
		return new ResponseEntity<ResultDTO>(new ResultOfFileResourcesDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	/**
	 * 实体new.titancode.test.FileResourcesPO删除
	 */
	@Override
	public ResponseEntity<ResultDTO> fileResourcessDelete(@PathVariable(required = true) String fileResourcesId) {
		fileResourcesService.deleteById(fileResourcesId);
		return new ResponseEntity<ResultDTO>(new ResultDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	/**
	 * 实体new.titancode.test.FileResourcesPO新增
	 */
	@Override
	public ResponseEntity<ResultDTO> fileResourcessPost(@RequestBody(required = true) FileResourcesPO fileResources) {
		fileResourcesService.insertFileResources(fileResources);
		return new ResponseEntity<ResultDTO>(new ResultOfFileResourcesDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResultDTO> fileResourcessUpload(MultipartFile file) {
		String url = fileResourcesService.uploadFile(file);
		return new ResponseEntity<ResultDTO>(new ResultOfFileResourcesDTO().fileInfo(url)
				.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	@Override
	public OutputStream fileResourcessRead(HttpServletRequest request, HttpServletResponse response) {
		return fileResourcesService.readFile(request, response);
	}

}