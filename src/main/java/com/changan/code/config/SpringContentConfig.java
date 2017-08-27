/**
 * 
 */
package com.changan.code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.changan.anywhere.common.springsupport.SpringContextHolder;

/**
 * @author wenxing
 *
 */
@Configuration
public class SpringContentConfig {
  
  /**
   * 将上下文环境配置成bean
   * 
   * @return
   */
  @Bean(name = "springContent")
  public SpringContextHolder springContextHolder() {
    return new SpringContextHolder();
  }

}
