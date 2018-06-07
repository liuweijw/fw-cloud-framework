package com.github.liuweijw.business.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.pay.domain.PayMchInfo;
import com.github.liuweijw.business.pay.repository.MchInfoRepository;
import com.github.liuweijw.business.pay.service.MchInfoService;
import com.github.liuweijw.commons.utils.StringHelper;

@Component
public class MchInfoServiceImpl implements MchInfoService {

	@Autowired
	private MchInfoRepository	mchInfoRepository;

	@Override
	public PayMchInfo findMchInfoByMchId(String mchId) {
		if (StringHelper.isBlank(mchId)) return null;

		return mchInfoRepository.findPayMchInfoByMchId(mchId.trim());
	}

}
