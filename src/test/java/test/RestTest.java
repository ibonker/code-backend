package test;

import java.util.HashMap;
import java.util.Map;

import org.hotpotmaterial.anywhere.common.mvc.rest.basic.ResultDTO;
import org.hotpotmaterial.code.Application;
import org.hotpotmaterial.code.dto.RequestAppinfoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.changan.otp.app.client.ResultOfTokenDTO;
import com.changan.otp.app.property.TitanServiceProperties;
import com.changan.otp.app.util.CryptionUtil;
import com.changan.otp.app.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RestTest {
  
  @Autowired
  private TestRestTemplate restTemplate;
  
  @Autowired
  private TitanServiceProperties titanProperties;
  
  @Test
  public void test1(){
    ResultDTO result = addFileService();
    System.out.println(result.getStatusCode());
  }
  
  private ResultOfTokenDTO getToken() {
    String seccode = CryptionUtil.encrypt(titanProperties.getAppid(), KeyUtil.publicKeyBase64ToKey(titanProperties.getAppPubkey()));
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
        "http://10.64.26.40:8020/app_manage/v1/app_infos/" + titanProperties.getAppid() + "/check/token")
        .queryParam("appSeccode", seccode)
        .queryParam("serviceId", titanProperties.getPlatform().getServiceMap().get("file-service"));
    
    return restTemplate.getForObject(builder.build().encode().toUri(), ResultOfTokenDTO.class);
  }
  
  private ResultDTO addFileService() {
    
    RequestAppinfoDTO app = new RequestAppinfoDTO();
    app.setAppId("abc");
    app.setAppName("abcName");
    
    String token = getToken().getToken();
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.add("titan-apptoken", token);
    requestHeaders.add("titan-appid",  titanProperties.getAppid());
    
    log.info("titan-apptoken:{}", token);
    log.info("titan-appid:{}", titanProperties.getAppid());
    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<RequestAppinfoDTO> requestEntity = new HttpEntity<RequestAppinfoDTO>(app, requestHeaders);
//    restTemplate.postForObject("http://10.64.19.229:8090/file/api/v1/auth_create", requestEntity, ResultDTO.class);
    return restTemplate.postForObject("http://127.0.0.1:8090/file/api/v1/auth_create", requestEntity, ResultDTO.class);
  }
  

}
