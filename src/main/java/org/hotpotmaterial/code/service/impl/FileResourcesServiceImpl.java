package org.hotpotmaterial.code.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.hotpotmaterial.code.entity.FileResourcesPO;
import org.hotpotmaterial.code.repository.FileResourcesRepository;
import org.hotpotmaterial.code.service.IFileResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hotpotmaterial-Code2 业务接口声明实现类 - FileResourcesServiceImpl
 */
@Service
@Slf4j
public class FileResourcesServiceImpl implements IFileResourcesService {

	@Autowired
	private FileResourcesRepository fileResourcesRepository;

	@Value("${upload.adress}")
	private String adress;

	@Value("${upload.uri}")
	private String uri;

	// 新增
	@Override
	public int insertFileResources(FileResourcesPO fileResources) {
		// 设置值
		fileResources.preInsert();
		fileResourcesRepository.save(fileResources);
		return 1;
	}

	// 修改
	@Override
	public int updateFileResources(String id, FileResourcesPO fileResources) {
		fileResources.preUpdate();
		fileResources.setId(id);
		fileResourcesRepository.save(fileResources);
		return 1;
	}

	// 通过id查询
	@Override
	public FileResourcesPO findById(String id) {
		return fileResourcesRepository.findOne(id);
	}

	// 通过id删除
	@Override
	public int deleteById(String id) {
		fileResourcesRepository.delete(id);
		return 1;
	}

	@Override
	public String uploadFile(MultipartFile file) {
		// 获取上传路径
		String path = adress;
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 截取后缀
		String extension = FilenameUtils.getExtension(fileName);
		// 通过时间戳生成文件名
		String uploadName = String.valueOf(System.currentTimeMillis()) + "." + extension;
		// 创建保存对象
		FileResourcesPO fileResourcesPO = new FileResourcesPO();
		fileResourcesPO.setId(UUID.randomUUID().toString());
		// 设置创建时间
		fileResourcesPO.setCreatedAt(LocalDateTime.now());
		// 设置文件真实文件名
		fileResourcesPO.setFilename(fileName);
		// 设置保存路径
		fileResourcesPO.setPath(path + uploadName);
		// 设置上传到服务器的文件名
		fileResourcesPO.setUploadname(uploadName);
		// 保存到数据库
		fileResourcesRepository.save(fileResourcesPO);
		// 文件上传
		// 获取文件
		File uploadfile = new File(path, uploadName);
		// 判断文件夹是否存在，没有就创建
		if (!uploadfile.getParentFile().exists()) {
			uploadfile.getParentFile().mkdirs();
		}
		InputStream inputStream = null;
		OutputStream outputStream = null;
		// 生成文件
		try {
			inputStream = file.getInputStream();
			outputStream = new FileOutputStream(uploadfile);
			// 上传
			IOUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				inputStream.close();
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return uri + "?picUploadName=" + uploadName;
	}

	@Override
	public OutputStream readFile(HttpServletRequest request, HttpServletResponse response) {
		// 获取图片名称
		String picName = request.getParameter("picUploadName");
		// 根据上传图片名称获取图片地址  uploadname
		FileResourcesPO fileResourcesPO = fileResourcesRepository.findByUploadname(picName);
		if (fileResourcesPO == null) {
			return null;
		}
		String picPath = fileResourcesPO.getPath();
		// 根据地址获取本地文件
		File file = new File(picPath);
		if (!file.exists()) {
			// 该文件不存在
			return null;
		}
		// 设置响应头
		response.setHeader("Content-Type", "image/jpeg");
		// 将文件转为输入流
		InputStream inputStream = null;
		// 得到响应输出流
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(file);
			outputStream = response.getOutputStream();
			IOUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.flush();
				inputStream.close();
				outputStream.close();
				return outputStream;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outputStream;
	}

}