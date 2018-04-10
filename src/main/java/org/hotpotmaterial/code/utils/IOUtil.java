package org.hotpotmaterial.code.utils;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO流工具类
 * 
 * @author sundc
 *
 *         2015年7月31日 上午8:36:00
 */
public final class IOUtil {
  /**
   * 日志
   */
  private static Logger logger = LoggerFactory.getLogger(IOUtil.class);

  // 隐藏工具类的构造方法
  private IOUtil() {}

  /**
   * 关闭一个或多个流对象
   * 
   * @param closeables 可关闭的流对象列表
   * @throws IOException
   */
  public static void close(Closeable... closeables) throws IOException {
    // 当流对象列表存在时
    if (closeables != null) {
      // 循环关闭流对象
      for (Closeable closeable : closeables) {
        // 当流对象不为null时
        if (closeable != null) {
          // 关闭流对象
          closeable.close();
        }
      }
    }
  }

  /**
   * 关闭一个或多个流对象
   * 
   * @param closeables 可关闭的流对象列表
   */
  public static void closeQuietly(Closeable... closeables) {
    try {
      // 调用循环关闭流对象进行关闭流对象服务
      close(closeables);
    } catch (IOException e) {
      logger.info("关闭流对象出现IO异常!!!", e);
    }
  }

}
