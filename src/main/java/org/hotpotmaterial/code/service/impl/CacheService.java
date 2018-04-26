package org.hotpotmaterial.code.service.impl;

import org.hotpotmaterial.code.dto.ResultDictDTO;
import org.hotpotmaterial.code.repository.DictTypeRepository;
import org.hotpotmaterial.code.repository.DictValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CacheService {

  //构建缓存容器
  ResultDictDTO resultDictDTO = new ResultDictDTO();

  @Autowired
  public DictTypeRepository dictTypeRepo;
  
  @Autowired
  public DictValueRepository dictValueRepo;
  
  public ResultDictDTO getResultDictDTO(){
    return this.resultDictDTO;
  }
}
