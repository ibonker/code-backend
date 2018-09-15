/**
 * 
 */
package org.hotpotmaterial.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.changan.otp.app.config.EnableTitanAppService;

/**
 * @author wenxing
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan(value={"org.hotpotmaterial.code"})
@EnableTitanAppService
public class Application {

  /**
   * @param args
   */
  public static void main(String[] args) {
    new SpringApplication(Application.class).run(args);
  }

}
