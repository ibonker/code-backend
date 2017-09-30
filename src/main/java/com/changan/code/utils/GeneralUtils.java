/**
 * 
 */
package com.changan.code.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wenxing
 *
 */
public class GeneralUtils {
  
  /**
   * 只大写首字母
   * @param str
   * @return
   */
  public static String toCapFirstLetter(String str) {
    return StringUtils.capitalize(str.toLowerCase());
  }

}
