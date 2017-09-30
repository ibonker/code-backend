import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.changan.code.Application;
import com.changan.code.entity.ProjectPO;
import com.changan.code.service.IProjectService;
import com.google.common.base.CaseFormat;

/**
 * @author wenxing
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectServiceTest {

  @Autowired
  IProjectService projectService;

  // @Test
  public void crudTest() {
    ProjectPO updateProject = new ProjectPO();
    // updateProject.setId("6e89a8c3-c7e1-4f8b-9947-ac43bf4a28d6");
    updateProject.setName("测试项目002");
    updateProject.setPackages("com.changan.testproj");
    updateProject.setComponents("springsecurity");
    updateProject.setDescription("测试项目002");
    // projectService.updateProject(updateProject);
    projectService.saveProject(updateProject);
  }

  @Test
  public void genTest() {
    String str = "/apps/relation_1/pages/";
    Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
    Pattern pattern2 = Pattern.compile("(?<=\\}/)(.+?)(?=\\/)");
    Pattern pattern3 = Pattern.compile("(?<=apps\\/)(.+?)(?=\\/pages)");
    Matcher matcher = pattern3.matcher(str);
    while(matcher.find()) {
      System.err.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, matcher.group()));
    }
    System.err.println(str.substring(0, str.length() - 1));
  }

}
