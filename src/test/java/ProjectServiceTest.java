import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import org.hotpotmaterial.code.Application;
import org.hotpotmaterial.code.entity.ProjectPO;
import org.hotpotmaterial.code.service.IProjectService;
import com.google.common.base.CaseFormat;
import com.google.common.io.Files;

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
    updateProject.setPackages("org.hotpotmaterial.testproj");
    updateProject.setComponents("springsecurity");
    updateProject.setDescription("测试项目002");
    // projectService.updateProject(updateProject);
//    projectService.saveProject(updateProject);
  }

//  @Test
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
    String str2 = "Relation1";
    System.err.println(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str2));
  }
  
  @Test
  public void normalTest() {
    File file = new File("./aaaaa/bbbb/mmm.txt");
    try {
      Files.createParentDirs(file);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
