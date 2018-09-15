/**
 * 
 */
package org.hotpotmaterial.code.utils;

import java.io.File;
import java.util.List;

import org.hotpotmaterial.anywhere.common.utils.FileUtils;

/**
 * @author Administrator
 *
 */
public class FileExtUtils {
  
  public static boolean copyDirectoryCover(String srcDirName, String descDirName,
      boolean coverlay, List<String> excludeFile) {
    File srcDir = new File(srcDirName);
    // 判断源目录是否存在
    if (!srcDir.exists()) {
      return false;
    }
    // 判断源目录是否是目录
    else if (!srcDir.isDirectory()) {
      return false;
    }
    // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
    String descDirNames = descDirName;
    if (!descDirNames.endsWith(File.separator)) {
      descDirNames = descDirNames + File.separator;
    }
    File descDir = new File(descDirNames);
    // 如果目标文件夹存在
    if (descDir.exists()) {
      if (coverlay) {
        // 允许覆盖目标目录
        if (!FileUtils.delFile(descDirNames)) {
          return false;
        }
      } else {
        return false;
      }
    } else {
      // 创建目标目录
      if (!descDir.mkdirs()) {
        return false;
      }

    }

    boolean flag = true;
    // 列出源目录下的所有文件名和子目录名
    File[] files = srcDir.listFiles();
    for (int i = 0; i < files.length; i++) {
      // 如果是一个单个文件，则直接复制
      if (files[i].isFile() && !excludeFile.contains(files[i].getName())) {
        flag = FileUtils.copyFile(files[i].getAbsolutePath(), descDirNames + files[i].getName());
        // 如果拷贝文件失败，则退出循环
        if (!flag) {
          break;
        }
      }
      // 如果是子目录，则继续复制目录
      if (files[i].isDirectory() && !excludeFile.contains(files[i].getName())) {
        flag =
            FileUtils.copyDirectory(files[i].getAbsolutePath(), descDirNames + files[i].getName());
        // 如果拷贝目录失败，则退出循环
        if (!flag) {
          break;
        }
      }
    }

    if (!flag) {
      return false;
    }
    return true;

  }

}
