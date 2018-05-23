package com.github.liuweijw.business.admin.service;

import com.github.liuweijw.business.admin.domain.LogInfo;
import com.github.liuweijw.business.commons.beans.PageBean;
import com.github.liuweijw.business.commons.beans.PageParams;

public interface LogInfoService {

	public void saveOrUpdate(LogInfo logInfo);

	/**
	 * 日志列表数据
	 */
	PageBean<LogInfo> findAll(PageParams pageParams, LogInfo logInfo);

	/**
	 * 日志删除
	 */
	boolean delById(Long id);
}
