package org.hotpotmaterial.code.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hotpotmaterial.code.entity.FileResourcesPO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hotpotmaterial-Code2 业务接口声明 - IFileResourcesService
 */
public interface IFileResourcesService {

	/**
	 * 新增
	 * 
	 * @param fileResources
	 * @return
	 */
	public int insertFileResources(FileResourcesPO fileResources);

	/**
	 * 修改
	 * 
	 * @param fileResources
	 * @return
	 */
	public int updateFileResources(String id, FileResourcesPO fileResources);

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 */
	public FileResourcesPO findById(String id);

	/**
	 * 删除
	 * 
	 * @param fileResources
	 */
	public int deleteById(String id);

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return URL地址
	 */
	public String uploadFile(MultipartFile file);

	/**
	 * 远程查看图片
	 * 
	 * @param request
	 * @param response
	 * @return 返回结果
	 */
	public OutputStream readFile(HttpServletRequest request, HttpServletResponse response);

}