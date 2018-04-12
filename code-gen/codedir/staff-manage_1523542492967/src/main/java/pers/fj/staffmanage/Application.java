package pers.fj.staffmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


/** 
 * 主类 
 *
 * @author Hotpotmaterial-Code2
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class})
@ComponentScan(value={"pers.fj.staffmanage"})
public class Application {
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // 初始化实例
    SpringApplication app = new SpringApplication(Application.class);
	// 运行实例
    app.run(args);
  }

}