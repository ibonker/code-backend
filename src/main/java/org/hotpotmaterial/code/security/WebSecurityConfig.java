package org.hotpotmaterial.code.security;

import org.hotpotmaterial.code.repository.LoginInfoRepository;
import org.hotpotmaterial.code.security.filter.AuthenLogoutFilter;
import org.hotpotmaterial.code.security.filter.AuthenticationFilter;
import org.hotpotmaterial.code.security.filter.LoginFilter;
import org.hotpotmaterial.code.security.filter.RescenterUserProvider;
import org.hotpotmaterial.code.security.filter.UnauthorizedEntryPoint;
import org.hotpotmaterial.code.security.service.IUserTokenService;
import org.hotpotmaterial.code.security.service.impl.UserTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "res.center")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Getter
  @Setter
  private static String server;
  
  @Autowired
  private LoginInfoRepository loginInfoRepository;
  /**
   * token crud操作
   * @return
   */
  @Bean
  public IUserTokenService tokenService() {
    return new UserTokenServiceImpl();
  }

  /**
   * 资源中心认证登录
   * @return
   */
  @Bean
  public RescenterUserProvider provider() {
    RescenterUserProvider rescenterUserProvider = new RescenterUserProvider();
    return rescenterUserProvider;
  }
  
  /**
   * security配置
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
    .antMatchers("/").permitAll()
    .anyRequest().authenticated()
    .and().exceptionHandling().authenticationEntryPoint(new UnauthorizedEntryPoint())
    .and().formLogin().loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
    .and().logout().logoutUrl("/logout").addLogoutHandler(new AuthenLogoutFilter(tokenService()))
    .and()
    .addFilter(new LoginFilter(authenticationManager(), tokenService(),loginInfoRepository))  
    .addFilter(new AuthenticationFilter(authenticationManager(), tokenService()));  
  }

  /**
   * 登录配置
   */
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(provider());
  }
  
  /**
   * 忽略认证地址
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    // ignore
    web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
        "/configuration/**", "/swagger-ui.html", "/webjars/**", "/dist/**");
  }

}