package com.changan.code.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.changan.code.common.Constants;
import com.changan.code.dto.ResultDictDTO;
import com.changan.code.entity.DictTypePO;
import com.changan.code.repository.DictTypeRepository;
import com.changan.code.repository.DictValueRepository;


@Service
public class CacheService {

  //构建缓存容器
  ResultDictDTO resultDictDTO = new ResultDictDTO();

  @Autowired
  public DictTypeRepository dictTypeRepo;
  
  @Autowired
  public DictValueRepository dictValueRepo;

  @PostConstruct
  public void CacheDictTypeAndValue() {
    List<Map<String, Object>> list = new ArrayList<>();
    System.out.println("进入方法！");
    // 获取DictType数据
    List<DictTypePO> dictTypes = dictTypeRepo.findByDelFlag(Constants.DATA_IS_NORMAL);
    for(DictTypePO dictType:dictTypes){
      Map<String, Object> map = new HashMap<>();
      map.put("id", dictType.getId());
      map.put("name", dictType.getName());
      map.put("code", dictType.getCode());
      map.put("value", dictValueRepo.findByDictCodeAndDelFlag(dictType.getCode(),Constants.DATA_IS_NORMAL));
      list.add(map);
    }
    resultDictDTO.setDictTypeAndValue(list);
    resultDictDTO.setVersion(0);
    resultDictDTO.setIsUpdate("0");
  }
  
  public ResultDictDTO getResultDictDTO(){
    return this.resultDictDTO;
  }
}
