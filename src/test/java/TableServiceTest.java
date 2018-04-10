import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import org.hotpotmaterial.code.Application;
import org.hotpotmaterial.code.config.property.ApiProperties;
import org.hotpotmaterial.code.service.ITableService;
import com.google.common.collect.Maps;

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
  
  @Autowired
  private ApiProperties apiProperties;
  
  @Test
  public void crudTest() {
//    List<SimpleDataObj> result = tableService.findClassnameByDatasourceId("161e1581-9430-487e-b8d4-b8c277e9d377");
//    System.err.println(result.size());
    String a = apiProperties.getGeneratedModal().getNormalApiModels().get(0).getConsumes();
    Map<String, String> map = Maps.newHashMap();
    map.put("urlPrefix", "aaa");
    map.put("tableName", "bbbb");
    map.put("tableParamName", "ccccc");
    for (String key : map.keySet()) {
      a = a.replace("${".concat(key).concat("}"), map.get(key));
    }
    System.err.println(a);
  }

}
