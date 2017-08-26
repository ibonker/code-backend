import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.changan.code.Application;
import com.changan.code.entity.ProjectPO;
import com.changan.code.service.IProjectService;

/**
 * @author wenxing
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectServiceTest {
  
  @Autowired
  IProjectService projectService;

  @Test
  public void crudTest() {
    ProjectPO updateProject = new ProjectPO();
//    updateProject.setId("6e89a8c3-c7e1-4f8b-9947-ac43bf4a28d6");
    updateProject.setName("测试项目002");
    updateProject.setPackages("com.changan.testproj");
    updateProject.setComponents("springsecurity");
    updateProject.setDescription("测试项目002");
//    projectService.updateProject(updateProject);
    projectService.saveProject(updateProject);
  }
  
}
