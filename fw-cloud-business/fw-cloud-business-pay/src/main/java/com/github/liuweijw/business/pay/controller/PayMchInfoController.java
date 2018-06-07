package com.github.liuweijw.business.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.liuweijw.business.commons.web.BaseController;
import com.github.liuweijw.business.pay.domain.PayMchInfo;
import com.github.liuweijw.business.pay.service.MchInfoService;
import com.github.liuweijw.commons.base.R;

/**
 * 支付渠道商户信息获取
 * 
 * @author liuweijw
 */
@RestController
@RequestMapping(value = "/pay/mchinfo")
public class PayMchInfoController extends BaseController {

	@Autowired
	private MchInfoService	mchInfoService;

	@GetMapping("/find/{mchId}")
	public R<PayMchInfo> findMchInfoByMchId(@PathVariable String mchId) {
		return new R<PayMchInfo>().data(mchInfoService.findMchInfoByMchId(mchId));
	}

}
