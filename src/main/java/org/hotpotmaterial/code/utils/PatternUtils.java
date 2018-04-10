/**
 * 
 */
package org.hotpotmaterial.code.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

/**
 * @author wenxing
 *
 */
public class PatternUtils {

  /**
   * 获得匹配的字符串
   * @param str
   * @param pattern
   * @return
   */
  public static List<String> getMatchedSubString(String str, String regex) {
    Pattern pattern = Pattern.compile(regex);
    List<String> substrs = Lists.newArrayList();
    Matcher matcher = pattern.matcher(str);
    while(matcher.find()) {
      substrs.add(matcher.group());
    }
    return substrs;
  }
  
}
