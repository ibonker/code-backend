package org.hotpotmaterial.code.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.hotpotmaterial.anywhere.common.mapper.JaxbMapper;
import org.hotpotmaterial.anywhere.common.utils.FileUtils;
import org.hotpotmaterial.anywhere.common.utils.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: GeneratorUtils <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 2016年5月19日 下午4:41:14 <br/>
 * 
 * @author zengjing
 * @version
 * @since JDK 1.7
 */
@Slf4j
public class GeneratorUtils {
  // 压缩文件后缀
  private static final String ZIP_FILE_EXTENSION = ".zip";

  /**
   * XML文件转换为对象
   * 
   * @param fileName
   * @param clazz
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T fileToObject(String fileName, Class<?> clazz) {
    try {
      String pathName = "/generator/" + fileName;
      Resource resource = new ClassPathResource(pathName);
      InputStream is = resource.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      StringBuilder sb = new StringBuilder();
      while (true) {
        String line = br.readLine();
        if (line == null) {
          break;
        }
        sb.append(line).append("\r\n");
      }
      IOUtil.close(is, br);
      return (T) JaxbMapper.fromXml(sb.toString(), clazz);
    } catch (IOException e) {
      log.error("Error file convert: {}", e.getMessage(), e);
    }
    return null;
  }

  /**
   * 获取工程路径
   * 
   * @return
   */
  public static String getProjectPath(String projectPath, String name) {
    // 如果配置了工程路径，则直接返回，否则自动获取。
    if (StringUtils.isNotBlank(projectPath)) {
      return projectPath + File.separator + name;
    }
    File f = new File("E:/" + name + File.separator + "src" + File.separator + "main");
    projectPath = f.toString();
    return projectPath;
  }

  /**
   * 获取项目压缩路径
   * 
   * @return
   */
  public static String getProjectZipPath(String projectZipPath, String name) {
    // 如果配置了工程路径，则直接返回，否则自动获取。
    if (StringUtils.isNotBlank(projectZipPath)) {
      // 创建文件夹
      FileUtils.createDirectory(projectZipPath);
      // 返回路径
      return projectZipPath + File.separator + name + ZIP_FILE_EXTENSION;
    }
    File f = new File(
        "E:/" + name + File.separator + "src" + File.separator + "main" + ZIP_FILE_EXTENSION);
    projectZipPath = f.toString();
    return projectZipPath;
  }
  
}
