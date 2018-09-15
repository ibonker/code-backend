package org.hotpotmaterial.code.service;

import org.hotpotmaterial.anywhere.common.mvc.page.rest.request.PageDTO;
import org.hotpotmaterial.code.entity.PlugsInfoPO;
import org.springframework.data.domain.Page;

/**
 * @author Hotpotmaterial-Code2 业务接口声明 - IPlugsInfoService
 */
public interface IPlugsInfoService {

	/**
	 * datatable 查询全部
	 * 
	 * @param page
	 * @return
	 */
	public Page<PlugsInfoPO> getPlugsInfoList(PageDTO page);

	/**
	 * 新增
	 * 
	 * @param plugsInfo
	 * @return
	 */
	public int insertPlugsInfo(PlugsInfoPO plugsInfo);

	/**
	 * 修改
	 * 
	 * @param plugsInfo
	 * @return
	 */
	public int updatePlugsInfo(String id, PlugsInfoPO plugsInfo);

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 */
	public PlugsInfoPO findById(String id);

	/**
	 * 删除
	 * 
	 * @param plugsInfo
	 */
	public int deleteById(String id);

}