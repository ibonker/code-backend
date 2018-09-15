package org.hotpotmaterial.code.service;

import java.util.List;

import org.hotpotmaterial.code.entity.DirectoryPO;

/**
 * @author Hotpotmaterial-Code2 业务接口声明 - IDirectoryService
 */
public interface IDirectoryService {

	/**
	 * 新增
	 * 
	 * @param directory
	 * @return
	 */
	public int insertDirectory(DirectoryPO directory);

	/**
	 * 修改
	 * 
	 * @param directory
	 * @return
	 */
	public int updateDirectory(String id, DirectoryPO directory);

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 */
	public DirectoryPO findById(String id);

	/**
	 * 删除
	 * 
	 * @param directory
	 */
	public int deleteById(String id);

	/**
	 * 获取所有根目录
	 * 
	 * @return
	 */
	public List<DirectoryPO> findRootDirectory();
	
	
	public List<DirectoryPO> findAll();

	/**
	 * 根据目录ID查询它的子目录
	 * 
	 * @param directoryId
	 * @return
	 */
	public List<DirectoryPO> findDirectorysById(String directoryId);

}