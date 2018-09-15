package org.hotpotmaterial.code.service.impl;

import java.util.List;

import org.hotpotmaterial.code.entity.DirectoryPO;
import org.hotpotmaterial.code.repository.DirectoryRepository;
import org.hotpotmaterial.code.service.IDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hotpotmaterial-Code2 业务接口声明实现类 - DirectoryServiceImpl
 */
@Service
public class DirectoryServiceImpl implements IDirectoryService {

	@Autowired
	private DirectoryRepository directoryRepository;

	// 新增
	@Override
	public int insertDirectory(DirectoryPO directory) {
		// 设置值
		directory.preInsert();
		String pid = directory.getPid();
		if (pid == null || "".equals(pid)) {
			directory.setPid("0");
		}
		directoryRepository.save(directory);
		return 1;
	}

	// 修改
	@Override
	public int updateDirectory(String id, DirectoryPO directory) {
		directory.preUpdate();
		directory.setId(id);
		directoryRepository.save(directory);
		return 1;
	}

	// 通过id查询
	@Override
	public DirectoryPO findById(String id) {
		return directoryRepository.findOne(id);
	}

	// 通过id删除
	@Override
	public int deleteById(String id) {
		directoryRepository.delete(id);
		return 1;
	}

	@Override
	public List<DirectoryPO> findRootDirectory() {
		return directoryRepository.findByPidIsNullOrPidAndDelFlag();
	}

	@Override
	public List<DirectoryPO> findDirectorysById(String directoryId) {
		return directoryRepository.selectDirectorysById(directoryId);
	}

  @Override
  public List<DirectoryPO> findAll() {
    return directoryRepository.findAll();
  }

}