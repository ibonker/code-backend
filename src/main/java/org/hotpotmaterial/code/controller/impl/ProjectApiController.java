/**
 * 
 */
package org.hotpotmaterial.code.controller.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.anywhere.common.mvc.page.rest.response.ResultPageDTO;
import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.common.BaseType;

import org.hotpotmaterial.code.common.RestStatus;
import org.hotpotmaterial.code.config.property.ApiProperties;
import org.hotpotmaterial.code.controller.ProjectApi;
import org.hotpotmaterial.code.dto.JavaTypeDTO;
import org.hotpotmaterial.code.dto.RequestAppinfoDTO;
import org.hotpotmaterial.code.dto.ResultOfAppDTO;
import org.hotpotmaterial.code.dto.ResultOfComponentsDTO;
import org.hotpotmaterial.code.dto.ResultOfMsgDataDTO;
import org.hotpotmaterial.code.dto.ResultOfProjectDTO;
import org.hotpotmaterial.code.dto.ResultOfTypeDTO;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.security.dto.UserInfo;
import org.hotpotmaterial.code.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.druid.util.StringUtils;
import com.changan.otp.app.client.ResultOfTokenDTO;
import com.changan.otp.app.property.TitanServiceProperties;
import com.changan.otp.app.util.CryptionUtil;
import com.changan.otp.app.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wenxing
 *
 */
@Controller
public class ProjectApiController extends BaseController implements ProjectApi {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private ApiProperties apis;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TitanServiceProperties titanProperties;

	/**
	 * 项目分页查询列表
	 */
	@Override
	public ResponseEntity<ResultDTO> projectsGet(@RequestBody PageDTO searchParams) {
		// Page<ProjectPO> result = projectService.findProjecsPage(searchParams, "");
		Page<ProjectPO> result = projectService.findProjecsPage(searchParams, "", getUser());
		if (result != null) {
			return new ResponseEntity<>(
					new ResultPageDTO<ProjectPO>().totalElements(result.getTotalElements())
							.pageNumber(result.getNumber()).pageSize(result.getSize()).data(result.getContent())
							.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResultPageDTO<ProjectPO>().message(RestStatus.RESULT_SUCCESS.message())
					.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
		}
	}

	/**
	 * 获取project
	 */
	@Override
	public ResponseEntity<ResultDTO> projectsShowGet(@PathVariable String id) {
		ProjectPO project = projectService.getProjectById(id, "");
		return new ResponseEntity<>(new ResultOfProjectDTO().project(project)
				.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 保存project
	 */
	@Override
	public ResponseEntity<ResultDTO> projectSavePost(@RequestBody ProjectPO project,
			@RequestParam(value = "token", required = false) String token, HttpServletRequest request) {

		ResultOfAppDTO result = getAppInfo(project);
		if (project.isNew()) {
			if (StringUtils.isEmpty(result.getAppId())) {
				editProject(project);
				// project = projectService.createProject(project, token, request, "");
				project = projectService.createProject(project, token, request, "", getUser());
			} else {
				return new ResponseEntity<>(
						new ResultDTO().message("应用名称重复").statusCode(RestStatus.RESULT_SYSTEM_ERROR.code()),
						HttpStatus.BAD_REQUEST);
			}
			// project = projectService.createProject(project, token, request, "");
			// project = projectService.createProject(project, token, request,
			// "",getUser());
		} else {
			if (StringUtils.isEmpty(project.getAppId()) && StringUtils.isEmpty(result.getAppId())) {
				editProject(project);
			}
			project = projectService.updateProject(project, token, request, "");

		}
		return new ResponseEntity<>(new ResultOfProjectDTO().project(project)
				.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	private void editProject(ProjectPO project) {
		Map<String, String> param = new HashMap<>();
		param.put("appDesc", project.getDescription());
		param.put("appName", project.getName());
		param.put("authedAppId", titanProperties.getAppid());
		param.put("authedAppSeccode", CryptionUtil.encrypt(titanProperties.getAppid(),
				KeyUtil.publicKeyBase64ToKey(titanProperties.getAppPubkey())));
		ResultDTO addResult = restTemplate.postForObject(
				"http://" + titanProperties.getPlatform().getServiceUrl() + "/app_manage/v1/app_infos/auth_create",
				param, ResultDTO.class);
		if (addResult.getStatusCode().equals("0")) {
			ResultOfAppDTO result2 = getAppInfo(project);
			project.setAppId(result2.getAppId());
			addFileService(project);
		}
	}

	private ResultOfAppDTO getAppInfo(ProjectPO project) {
		Map<String, String> param = new HashMap<>();
		param.put("appName", project.getName());
		param.put("authedAppId", titanProperties.getAppid());
		param.put("authedAppSeccode", CryptionUtil.encrypt(titanProperties.getAppid(),
				KeyUtil.publicKeyBase64ToKey(titanProperties.getAppPubkey())));
		ResultOfAppDTO result = restTemplate.postForObject(
				"http://" + titanProperties.getPlatform().getServiceUrl() + "/app_manage/v1/app_infos/auth_info", param,
				ResultOfAppDTO.class);
		return result;
	}

	private ResultOfTokenDTO getToken() {
		String seccode = CryptionUtil.encrypt(titanProperties.getAppid(),
				KeyUtil.publicKeyBase64ToKey(titanProperties.getAppPubkey()));
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://" + titanProperties.getPlatform().getServiceUrl() + "/app_manage/v1/app_infos/"
						+ titanProperties.getAppid() + "/check/token")
				.queryParam("appSeccode", seccode)
				.queryParam("serviceId", titanProperties.getPlatform().getServiceMap().get("file-service"));

		return restTemplate.getForObject(builder.build().encode().toUri(), ResultOfTokenDTO.class);
	}

	private void addFileService(ProjectPO project) {

		RequestAppinfoDTO app = new RequestAppinfoDTO();
		app.setAppId(project.getAppId());
		app.setAppName(project.getName());

		String token = getToken().getToken();
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("titan-apptoken", token);
		requestHeaders.add("titan-appid", titanProperties.getAppid());

		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RequestAppinfoDTO> requestEntity = new HttpEntity<RequestAppinfoDTO>(app, requestHeaders);
		restTemplate.postForObject("http://10.64.19.229:8090/file/api/v1/auth_create", requestEntity, ResultDTO.class);
	}

	/**
	 * 项目持久化实体
	 */
	@Override
	public ResponseEntity<ResultDTO> projectDtoPoGet(@PathVariable String id) {
		return new ResponseEntity<>(
				new ResultOfTypeDTO().dto(projectService.getProjectDTO(id)).po(projectService.getProjectPO(id))
						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 项目支持数据类型
	 */
	@Override
	public ResponseEntity<ResultDTO> projectDataTypeGet(@PathVariable String id) {
		return new ResponseEntity<>(
				new ResultOfTypeDTO()
						.base(new JavaTypeDTO(BaseType.BASE.toString().toLowerCase(), BaseType.BASE.getCname(),
								apis.getBaseType()))
						.array(new JavaTypeDTO(BaseType.ARRAY.toString().toLowerCase(), BaseType.ARRAY.getCname(),
								apis.getArrayType()))
						// .dto(projectService.getProjectDTO(id)).po(projectService.getProjectPO(id))
						.dto(projectService.getProjectDTO(id)).po(projectService.getProjectIsAutoPO(id))

						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 项目支持组件
	 */
	@Override
	public ResponseEntity<ResultDTO> projectsComponentsDefaultGet() {
		return new ResponseEntity<>(
				new ResultOfComponentsDTO().categories(projectService.getComponents())
						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 生成项目代码
	 */
	@Override
	public ResponseEntity<ResultDTO> projectsGenerateCodeGet(@PathVariable String id) {
		return new ResponseEntity<>(
				new ResultOfMsgDataDTO().msgData(projectService.generateCodeFiles(id, ""))
						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 下载项目代码
	 */
	@Override
	public ResponseEntity<InputStreamResource> projectsDownloadGet(@PathVariable String projectName)
			throws FileNotFoundException {
		File file = projectService.downloadZipFiles(projectName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.body(new InputStreamResource(new FileInputStream(file)));
	}

	@Override
	public ResponseEntity<InputStreamResource> projectsDownloadUIGet(@PathVariable String projectId)
			throws FileNotFoundException {
		File file = projectService.downloadZipUIFiles(projectId, "");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.body(new InputStreamResource(new FileInputStream(file)));
	}

	/**
	 * 创建字典表
	 */
	@Override
	public ResponseEntity<ResultDTO> isDictionaryPost(@PathVariable String id) {
		ProjectPO project = projectService.getProjectById(id, "");
		return new ResponseEntity<>(
				new ResultOfProjectDTO().isDictionary(projectService.creatNeedTables(project))
						.message(RestStatus.RESULT_SUCCESS.message()).statusCode(RestStatus.RESULT_SUCCESS.code()),
				HttpStatus.OK);
	}

	/**
	 * 根据id删除项目
	 */
	@Override
	public ResponseEntity<ResultDTO> projectPost(@PathVariable String id) {
		// 执行删除
		projectService.deleteProject(id);
		return new ResponseEntity<>(new ResultOfProjectDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	/**
	 * 修改星标识
	 */
	@Override
	public ResponseEntity<ResultDTO> projectStar(@RequestBody ProjectPO projectPO) {
		projectService.updateProjectStar(projectPO);
		return new ResponseEntity<>(new ResultOfProjectDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResultDTO> projectSaveAndSync(@RequestBody ProjectPO projectPO) {
		ResultOfAppDTO result = getAppInfo(projectPO);
		if (projectPO.isNew()) {
			if (StringUtils.isEmpty(result.getAppId())) {
				editProject(projectPO);
				projectService.saveSyncProject(projectPO, getUser());
			} else {
				return new ResponseEntity<>(
						new ResultDTO().message("应用名称重复").statusCode(RestStatus.RESULT_SYSTEM_ERROR.code()),
						HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(new ResultOfProjectDTO().message(RestStatus.RESULT_SUCCESS.message())
				.statusCode(RestStatus.RESULT_SUCCESS.code()), HttpStatus.OK);
	}

	

}