package com.github.liuweijw.business.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.admin.domain.LogInfo;
import com.github.liuweijw.business.admin.domain.QLogInfo;
import com.github.liuweijw.business.admin.repository.LogInfoRepository;
import com.github.liuweijw.business.admin.service.LogInfoService;
import com.github.liuweijw.business.commons.utils.PageUtils;
import com.github.liuweijw.commons.base.page.PageBean;
import com.github.liuweijw.commons.base.page.PageParams;
import com.github.liuweijw.commons.utils.StringHelper;
import com.querydsl.core.types.Predicate;

@Component
public class LogInfoServiceImpl implements LogInfoService {

	@Autowired
	private LogInfoRepository	logInfoRepository;

	@Override
	@Transactional
	public void saveOrUpdate(LogInfo logInfo) {

		if (null == logInfo) return;

		logInfoRepository.save(logInfo);

	}

	@Override
	public PageBean<LogInfo> findAll(PageParams pageParams, LogInfo logInfo) {
		QLogInfo qLogInfo = QLogInfo.logInfo;
		// 用户名查询条件
		Predicate qServiceIdPredicate = null;
		if (null != logInfo) {
			if (StringHelper.isNotBlank(logInfo.getServiceId())) {
				qServiceIdPredicate = qLogInfo.serviceId.like("%" + logInfo.getServiceId().trim()
						+ "%");
			}
		}

		Predicate predicate = qLogInfo.statu.eq(0).and(qServiceIdPredicate);

		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		PageRequest pageRequest = PageUtils.of(pageParams, sort);
		Page<LogInfo> pageList = logInfoRepository.findAll(predicate, pageRequest);

		List<LogInfo> cList = pageList.getContent();
		if (null != cList && cList.size() > 0) {
			cList.forEach(log -> {
				log.setIdView(log.getId() + "");
			});
		}
		return PageUtils.of(pageList);
	}

	@Override
	@Transactional
	public boolean delById(Long id) {
		if (null == id || id <= 0) return Boolean.FALSE;

		logInfoRepository.delete(id);

		return Boolean.TRUE;
	}

}
