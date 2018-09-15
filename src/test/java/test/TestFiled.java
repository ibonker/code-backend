package test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hotpotmaterial.code.entity.DirectoryPO;
import org.junit.Test;

public class TestFiled {
	
	@Test
	public void testName() throws Exception {
		DirectoryPO msg = new DirectoryPO();
		msg.setName("测试");
		msg.setPid("2");
		Field[] fields = msg.getClass().getDeclaredFields();
		for (Field field : fields) {
			
			String name = field.getName();
			//System.err.println(name);
			//System.out.println(field.get);
			String firstLetter = name.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + name.substring(1);    
            System.out.println(getter);
            Method method = msg.getClass().getMethod(getter);    
            Object value = method.invoke(msg, new Object[] {});  

		}
		List<Field> list = Arrays.asList(fields);
	}
}
