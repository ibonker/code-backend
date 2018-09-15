package org.hotpotmaterial.code.service.impl;

import org.hotpotmaterial.code.entity.ContextPO;
import org.hotpotmaterial.code.entity.DirectoryPO;
import org.hotpotmaterial.code.repository.ContextRepository;
import org.hotpotmaterial.code.repository.DirectoryRepository;
import org.hotpotmaterial.code.service.IContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hotpotmaterial-Code2 业务接口声明实现类 - ContextServiceImpl
 */
@Service
@Slf4j
public class ContextServiceImpl implements IContextService {
	@Autowired
	private ContextRepository contextRepository;
	@Autowired
	private DirectoryRepository directoryRepository;

	// 新增
	@Override
	@Transactional
	public int insertContext(ContextPO context) {
		// 设置值
		context.preInsert();
		// 获取菜单ID
		String directoryId = context.getDirectoryId();
		if (directoryId != null && !"".equals(directoryId)) {
			// 查询该菜单是否存在
			DirectoryPO directoryPO = directoryRepository.findOne(directoryId);
			if (directoryPO != null) {
				// 1: 该菜单有文档标识
				directoryPO.setContentFlag("1");
				// 更新时间
				directoryPO.preUpdate();
				// 修改菜单的文档标识
				directoryRepository.save(directoryPO);
			}
		}
		contextRepository.save(context);
		return 1;
	}

	// 修改
	@Override
	public int updateContext(String id, ContextPO context) {
		context.preUpdate();
		context.setId(id);
		contextRepository.save(context);
		return 1;
	}

	// 通过id查询
	@Override
	public ContextPO findById(String id) {
		return contextRepository.findOne(id);
	}

	// 通过id删除
	@Override
	public int deleteById(String id) {
		contextRepository.delete(id);
		return 1;
	}

	@Override
	public ContextPO findBydirectoryId(String directoryId) {
		return contextRepository.findByDirectoryIdAndDelFlag(directoryId,"0");
	}

}