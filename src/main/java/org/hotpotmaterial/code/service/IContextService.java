package org.hotpotmaterial.code.service;

import org.hotpotmaterial.code.entity.ContextPO;
import org.hotpotmaterial.jsonmeta.JsonSchema;

/**
 * @author Hotpotmaterial-Code2 
 * 业务接口声明 - IContextService
 */
public interface IContextService{



  /**
   * 新增
   * 
   * @param context
   * @return
   */
  public int insertContext(ContextPO context);
    
  /**
   * 修改
   * 
   * @param context
   * @return
   */
  public int updateContext(String id, ContextPO context);
    
  /**
   * 根据ID查找
   *  
   * @param id
   * @return
   */
  public ContextPO findById(String id);
    
  /**
   * 删除
   * 
   * @param context
   */
  public int deleteById(String id);
  
  /**
   * 根据目录ID找到对应的文档
   * @param directoryId
   * @return
   */
  public ContextPO findBydirectoryId(String directoryId);
  
}