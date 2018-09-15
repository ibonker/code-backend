package org.hotpotmaterial.code.service.impl;

import java.util.List;

import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.entity.VersionPO;
import org.hotpotmaterial.code.repository.VersionRepository;
import org.hotpotmaterial.code.service.IVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VersionServiceImpl implements IVersionService {
	@Autowired
	private VersionRepository versionRepository;
	@Override
	@Transactional("jpaTransactionManager")
	public void saveVersion(VersionPO versionPO) {
		// 是否为新增
		if (versionPO.isNew()) {
			//初始化数据
			versionPO.preInsert();
			//保存数据
			versionRepository.save(versionPO);
		}else {
			versionPO.preUpdate();
			//修改数据
			versionRepository.save(versionPO);
		}
	}

	@Override
	@Transactional("jpaTransactionManager")
	public void deleteVersion(String versionId) {
		versionRepository.delete(versionId);

	}

	@Override
	public List<VersionPO> findByFlag(String flag) {
		//查询出来的数据
		List<VersionPO> versions = versionRepository.findByFlag(flag);
		//获取描述信息
		for (VersionPO versionPO : versions) {
			String versiondescription = versionPO.getVersionDescription();
			//切分描述信息
			String[] descriptions = versiondescription.split(Constants.DESCRIPTION_SPILT);
			versionPO.setDescription(descriptions);
		}
		return versions;
		
	}

}
