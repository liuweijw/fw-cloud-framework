package com.github.liuweijw.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.admin.domain.LogInfo;
import com.github.liuweijw.admin.repository.LogInfoRepository;
import com.github.liuweijw.admin.service.LogInfoService;

@Component
public class LogInfoServiceImpl implements LogInfoService {

	@Autowired
	private LogInfoRepository logInfoRepository; 
	
	@Override
	public void saveOrUpdate(LogInfo logInfo) {
		
		if(null == logInfo) return;
		
		logInfoRepository.saveAndFlush(logInfo);
		
	}

}
