package org.hotpotmaterial.code.service;

import java.util.List;
import java.util.Map;

import org.hotpotmaterial.code.dto.SimpleDataObj;
import org.hotpotmaterial.code.entity.DatasourcePO;
import org.hotpotmaterial.code.entity.TableSeniorRelationPO;
import org.hotpotmaterial.code.entity.TransferObjPO;

/**
 * 
 * @author xuyufeng
 *
 */
public interface ITransferObjService {

	/**
	 * 更新DTO
	 * 
	 * @param transferObj
	 */
	public TransferObjPO updateTransferObj(TransferObjPO transferObj);

	/**
	 * 保存DTO
	 * 
	 * @param transferObj
	 */
	public TransferObjPO saveTransferObj(TransferObjPO transferObj);

	/**
	 * 删除DTO
	 * 
	 * @param transferObj
	 */
	public void deleteTransferObj(String id);

	/**
	 * 删除高级关联DTO
	 * 
	 * @param transferObj
	 */
	public void deleteSeniorTransferObj(String tableId);

	/**
	 * 根据id获取DTO
	 * 
	 * @param id
	 * @return
	 */
	public TransferObjPO findTransferObjById(String id);

	/**
	 * 包含高级关联
	 * 
	 * @param tableId
	 * @return
	 */
	public List<TransferObjPO> findAllTransferObjByTableId(String tableId);

	/**
	 * 根据table id获取DTO
	 * 
	 * @param id
	 * @return
	 */
	public TransferObjPO findTransferObjByTableId(String tableId);

	/**
	 * 根据table id获取高级关联DTO
	 * 
	 * @param id
	 * @return
	 */
	public List<TransferObjPO> findSeniorTransferObjByTableId(String tableId);

	/**
	 * 查询所有的DTO
	 * 
	 * @return
	 */
	public List<TransferObjPO> findAllTransferObj(String projectId);

	/**
	 * 获取自定义实体类名
	 * 
	 * @param projectId
	 * @return
	 */
	public Map<String, List<SimpleDataObj>> findClassnameByProjectId(String projectId);

	/**
	 * 自动创建实体
	 * 
	 * @param tableName
	 *            表名app_info
	 * @param datasourcePName
	 *            包名ddemo.test
	 * @param className
	 *            类名 ddemo.test.AppInfoPO
	 * @return
	 */
	public TransferObjPO createAutoCrudDTO(String projectId, String tableId, String tableName, String datasourcePName,
			String className);

	/**
	 * 创建高级查询dto
	 * 
	 * @param projectId
	 * @param tableId
	 * @param tableName
	 * @param relation
	 * @return
	 */
	public List<TransferObjPO> updateAutoCrudSeniorDTO(String projectId, String datasourcePname,
			TableSeniorRelationPO relation);

	/**
	 * 根据tableId删除api
	 * 
	 * @param tableId
	 */
	public void deleteAutoCrudDTO(String tableId);

	/**
	 * 根据名称获取默认基础dto
	 * 
	 * @param name
	 */
	public TransferObjPO getDefaultDtoByName(String name);

	/**
	 * 确认是否为基础dto
	 * 
	 * @param name
	 */
	public boolean checkIfIsDefaultDto(String name);

	/**
	 * 根据genBasedTableId列表获取数据id
	 * 
	 * @param genBasedTableId
	 * @return
	 */
	public List<String> findIdByGenBasedTableIdIn(List<String> genBasedTableIds);

	/**
	 * 根据genBasedTableId列表删除数据
	 * 
	 * @param genBasedTableId
	 * @return
	 */
	public Long deleteByGenBasedTableIdIn(List<String> genBasedTableIds);

	/**
	 * 根据项目ID以及它对应的数据源,查询对应的TransferObjPO
	 * 
	 * @param datasources
	 *            该项目ID对应的数据源
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	public List<TransferObjPO> findAllTransferObj(List<DatasourcePO> datasources, String projectId);
}
