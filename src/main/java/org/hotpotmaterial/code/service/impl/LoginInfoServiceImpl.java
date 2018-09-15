package org.hotpotmaterial.code.service.impl;

import java.util.List;

import org.hotpotmaterial.code.common.Constants;
import org.hotpotmaterial.code.entity.LoginInfoPO;
import org.hotpotmaterial.code.repository.LoginInfoRepository;
import org.hotpotmaterial.code.service.ILoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginInfoServiceImpl implements ILoginInfoService {
	@Autowired
	private LoginInfoRepository loginInfoRepository;
	@Override
	@Transactional("jpaTransactionManager")
	public void saveLoginInfo(LoginInfoPO loginInfoPO) {
		// 是否为新增
		if (loginInfoPO.isNew()) {
			//初始化数据
			loginInfoPO.preInsert();
			//保存数据
			loginInfoRepository.save(loginInfoPO);
		}else {
			loginInfoPO.preUpdate();
			//修改数据
			loginInfoRepository.save(loginInfoPO);
		}
	}

	@Override
	@Transactional("jpaTransactionManager")
	public void deleteLoginInfo(String loginInfoId) {
		loginInfoRepository.delete(loginInfoId);

	}



}
