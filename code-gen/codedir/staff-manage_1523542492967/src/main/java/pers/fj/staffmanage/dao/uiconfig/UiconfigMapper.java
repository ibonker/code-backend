package pers.fj.staffmanage.dao.uiconfig;

import pers.fj.staffmanage.entity.staff.manage.UiConfigPO;
	
	
/**
 * ui配置dao
 * @author Hotpotmaterial-Code2
 */
public interface uiconfigMapper {
  
  /**
   * 获取ui数据
   * @param id
   */
  UiConfigPO selectByPrimaryKey(String id);

  /**
   * 新增ui数据
   * @param id
   */
  int insertSelective(UiConfigPO record);

  /**
   * 修改ui数据
   * @param id
   */
  int updateByPrimaryKeySelective(UiConfigPO record);
  
}