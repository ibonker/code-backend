import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.changan.code.Application;
import com.changan.code.dto.SimpleDataObj;
import com.changan.code.service.ITableService;

/**
 * 
 */

/**
 * @author wenxing
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TableServiceTest {
  
  @Autowired
  private ITableService tableService;
  
  @Test
  public void crudTest() {
    List<SimpleDataObj> result = tableService.findClassnameByDatasourceId("161e1581-9430-487e-b8d4-b8c277e9d377");
    System.err.println(result.size());
  }

}
