package com.github.liuweijw.business.pay.service;

import com.github.liuweijw.business.pay.domain.PayMchInfo;

public interface MchInfoService {

	PayMchInfo findMchInfoByMchId(String mchId);
}
