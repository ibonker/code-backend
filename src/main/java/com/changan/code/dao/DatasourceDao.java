package com.changan.code.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.changan.anywhere.common.persistence.mybatis.annotation.MyBatisDao;
import com.changan.anywhere.common.persistence.mybatis.dao.CrudDao;
import com.changan.code.entity.DatasourcePO;

/**
 * ClassName: DatasourceDao <br/>
 * date: 2016年4月15日 上午10:54:53 <br/>
 * 
 * @author zengjing
 * @version
 * @since JDK 1.7
 */
@MyBatisDao
public interface DatasourceDao extends CrudDao<DatasourcePO> {

  /**
   * 通过projectId获取数据源列表
   * 
   * @param user
   * @return
   */
  public List<DatasourcePO> findDatasourcesByProject(String projectId);

  /**
   * 根据projectID和查询字段模糊查询datasource
   * 
   * @param params
   * @return
   */
  public List<DatasourcePO> findDatasourcesByProjectAndFuzzyQuery(Map<String, String> params);

  /**
   * 通过projectId联级删除数据源
   * 
   * @param projectId
   */
  public void deleteByProject(String projectId);

  /**
   * 通过id查
   * 
   * @param id
   * @return
   */
  public DatasourcePO findDatasourceById(@Param("id") String id);

  /**
   * 根据Id数据源
   */
  public void deleteById(String id);

  /**
   * @Description datatable 分页统计查询
   * @param params
   * @return
   */
  public int countByPage(Map<String, Object> params);

  /**
   * 获得分页数据
   * 
   * @return
   */
  public List<DatasourcePO> findWithPage(Map<String, Object> params);
}
