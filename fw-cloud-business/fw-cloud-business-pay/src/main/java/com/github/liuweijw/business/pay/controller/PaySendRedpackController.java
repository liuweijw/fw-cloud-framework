package com.github.liuweijw.business.pay.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest;
import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest.WxPaySendRedpackRequestBuilder;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.liuweijw.business.commons.utils.SequenceUtils;
import com.github.liuweijw.business.pay.config.wechat.WxPayProperties;
import com.github.liuweijw.business.pay.config.wechat.WxPayUtil;
import com.github.liuweijw.business.pay.domain.PayChannel;
import com.github.liuweijw.business.pay.domain.PayMchInfo;
import com.github.liuweijw.business.pay.domain.PaySendRedpack;
import com.github.liuweijw.business.pay.service.MchInfoService;
import com.github.liuweijw.business.pay.service.PayChannelService;
import com.github.liuweijw.business.pay.service.WxSendRedpackService;
import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.commons.pay.beans.PaySendRedpackReqBean;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.pay.enums.PaySceneEnum;
import com.github.liuweijw.commons.pay.utils.PayUtil;
import com.github.liuweijw.commons.utils.PublicHelper;
import com.github.liuweijw.commons.utils.StringHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送微信红包给个人用户
 * 
 * @author liuweijw
 */
@Slf4j
@RestController
@RequestMapping(value = "/pay/order")
public class PaySendRedpackController {

	@Autowired
	private WxSendRedpackService	wxSendRedpackService;

	@Autowired
	private MchInfoService			mchInfoService;

	@Autowired
	private PayChannelService		payChannelService;

	@Autowired
	private WxPayProperties			wxPayProperties;

	@RequestMapping(value = "/sendRedpack")
	public R<Map<String, Object>> sendRedpack(@RequestBody PaySendRedpackReqBean paySendRedpackReqBean) {
		PaySendRedpack paySendRedpack = new PaySendRedpack();
		WxPaySendRedpackRequestBuilder wxPaySendRedpackRequestBuilder = WxPaySendRedpackRequest.newBuilder();
		R<Boolean> validateResult = validateSendRedpackReqParams(paySendRedpackReqBean, paySendRedpack, wxPaySendRedpackRequestBuilder);
		if (!validateResult.getData()) {
			log.info(JSON.toJSONString(validateResult));
			Map<String, Object> returnMap = PayUtil
					.makeRetData(PayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, validateResult.getMsg(), null, null, null), paySendRedpack.getResKey());
			log.info(JSON.toJSONString(returnMap));
			return new R<Map<String, Object>>().data(returnMap).failure();
		}
		return wxSendRedpackService.sendRedpack(paySendRedpack, wxPaySendRedpackRequestBuilder.build());
	}

	private R<Boolean> validateSendRedpackReqParams(PaySendRedpackReqBean sendRedpackReqBean, PaySendRedpack paySendRedpack,
			WxPaySendRedpackRequestBuilder wxPaySendRedpackRequestBuilder) {

		if (null == sendRedpackReqBean)
			return new R<Boolean>().data(false).failure("请求[/pay/order/sendRedpack]参数不能为空！");

		// 是否是发放普通
		boolean isCommonRedPack = sendRedpackReqBean.getRedPackType().intValue() == 0;
		String preFix = isCommonRedPack ? "【发放普通红包】" : "【发放裂变红包】";

		// 商户id
		String mchId = sendRedpackReqBean.getMchId();
		if (StringHelper.isBlank(mchId))
			return new R<Boolean>().data(false).failure(preFix + "商户ID不存在！");

		// 商户订单号
		String mchOrderNo = sendRedpackReqBean.getMchOrderNo();
		if (StringHelper.isBlank(mchOrderNo))
			return new R<Boolean>().data(false).failure(preFix + "商户订单号不存在！");

		// 渠道ID
		String channelId = sendRedpackReqBean.getChannelId();
		if (StringHelper.isBlank(channelId))
			return new R<Boolean>().data(false).failure(preFix + "渠道ID不存在！");

		// 红包发放总金额（单位分）
		Integer amount = sendRedpackReqBean.getTotalAmount();
		if (null == amount || amount <= 0)
			return new R<Boolean>().data(false).failure(preFix + "红包发放总金额（单位分）不正确！");

		if (amount < 100 || amount > 20000)
			return new R<Boolean>().data(false).failure(preFix + "每个红包的平均金额必须在1.00元到200.00元之间！");

		// 红包发放总人数
		Integer totalNum = sendRedpackReqBean.getTotalNum();
		if (null == totalNum || totalNum <= 0)
			return new R<Boolean>().data(false).failure(preFix + "红包发放总人数设置不正确！");

		// 红包祝福语
		String wishing = sendRedpackReqBean.getWishing();
		if (StringHelper.isBlank(wishing))
			return new R<Boolean>().data(false).failure(preFix + "红包祝福语未设置！");

		// ip
		String ip = sendRedpackReqBean.getIp();
		if (StringHelper.isBlank(ip))
			return new R<Boolean>().data(false).failure(preFix + "请求IP地址不正确！");

		// 活动名称
		String actName = sendRedpackReqBean.getActName();
		if (StringHelper.isBlank(actName))
			return new R<Boolean>().data(false).failure(preFix + "活动名称未设置！");

		// 备注
		String remark = sendRedpackReqBean.getRemark();
		if (StringHelper.isBlank(remark))
			return new R<Boolean>().data(false).failure(preFix + "备注信息未设置！");

		// 场景id
		String sceneId = sendRedpackReqBean.getSceneId();
		if (StringHelper.isBlank(sceneId) || !checkSceneId(sceneId))
			return new R<Boolean>().data(false).failure(preFix + "场景id不正确！");

		// 签名信息
		String sign = sendRedpackReqBean.getSign();
		if (StringHelper.isBlank(sign))
			return new R<Boolean>().data(false).failure(preFix + "未签名！");

		// 查询商户信息
		PayMchInfo mchInfo = mchInfoService.findMchInfoByMchId(mchId);
		if (null == mchInfo) return new R<Boolean>().data(false).failure(preFix + "商户信息不存在！");

		if (mchInfo.getStatu().intValue() != 1)
			return new R<Boolean>().data(false).failure(preFix + "商户信息已失效！");

		if (StringHelper.isBlank(mchInfo.getReqKey()))
			return new R<Boolean>().data(false).failure(preFix + "商户请求私钥未设置！");

		// 查询商户对应的支付渠道
		PayChannel payChannel = payChannelService.findPayChannel(channelId, mchId);
		if (null == payChannel)
			return new R<Boolean>().data(false).failure(preFix + "商户渠道[channelId=" + channelId + ",mchId=" + mchId + "]信息不存在！");

		if (payChannel.getStatu().intValue() != 1)
			return new R<Boolean>().data(false).failure(preFix + "商户渠道[channelId=" + channelId + ",mchId=" + mchId + "]信息已经失效！");

		WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(payChannel.getParam(), wxPayProperties.getCertRootPath());
		if (null == wxPayConfig)
			return new R<Boolean>().data(false).failure(preFix + "商户渠道[channelId=" + channelId + ",mchId=" + mchId + "]信息param设置不正确！");

		// 验证签名数据
		boolean verifyFlag = PayUtil.verifyPaySign((JSONObject) JSON.toJSON(sendRedpackReqBean), mchInfo.getReqKey());
		if (!verifyFlag) return new R<Boolean>().data(false).failure(preFix + "验证签名失败！");

		paySendRedpack.setSendOrderId(SequenceUtils.getInstance().generateBizSeqNo("SRP"));
		paySendRedpack.setWxPayConfig(wxPayConfig);
		paySendRedpack.setResKey(mchInfo.getResKey());
		paySendRedpack.setRedPackType(isCommonRedPack ? 0 : 1);
		paySendRedpack.setChannelId(channelId);
		paySendRedpack.setMchOrderNo(mchOrderNo);  	// 商户订单号
		paySendRedpack.setMchId(wxPayConfig.getMchId()); // 商户号
		paySendRedpack.setOpenId(sendRedpackReqBean.getOpenId());
		paySendRedpack.setTotalAmount(sendRedpackReqBean.getTotalAmount()); // 付款金额
		paySendRedpack.setTotalNum(sendRedpackReqBean.getTotalNum()); // 红包发放总人数
		if (!isCommonRedPack) {
			paySendRedpack.setAmtType("ALL_RAND"); // 红包金额设置方式 裂变红包才进行设置
		} else {
			paySendRedpack.setAmtType(null);
		}
		paySendRedpack.setWishing(sendRedpackReqBean.getWishing()); // 红包祝福语
		paySendRedpack.setIp(ip);
		paySendRedpack.setActName(sendRedpackReqBean.getActName()); // 活动名称
		paySendRedpack.setRemark(sendRedpackReqBean.getRemark()); // 备注
		paySendRedpack.setSceneId(sceneId);  // 场景id
		if (!PublicHelper.isEmpty(sendRedpackReqBean.getRiskInfo()))
			paySendRedpack.setRiskInfo(sendRedpackReqBean.getRiskInfo()); // 活动信息
		if (!PublicHelper.isEmpty(sendRedpackReqBean.getConsumeMchId()))
			paySendRedpack.setConsumeMchId(sendRedpackReqBean.getConsumeMchId()); // 资金授权商户号

		wxPaySendRedpackRequestBuilder
				.mchBillNo(paySendRedpack.getMchOrderNo())
				.wxAppid(wxPayConfig.getAppId())
				.sendName(mchInfo.getMchName()) // 商户名称 来自商户数据库
				.reOpenid(paySendRedpack.getOpenId())
				.totalAmount(paySendRedpack.getTotalAmount())
				.totalNum(paySendRedpack.getTotalNum())
				.amtType(paySendRedpack.getAmtType())
				.wishing(paySendRedpack.getWishing())
				.clientIp(paySendRedpack.getIp())
				.actName(paySendRedpack.getActName())
				.remark(paySendRedpack.getRemark())
				.sceneId(paySendRedpack.getSceneId())
				.riskInfo(paySendRedpack.getRiskInfo())
				.consumeMchId(paySendRedpack.getConsumeMchId())
				.build();

		return new R<Boolean>().data(true).success(preFix + "信息验证成功！");
	}

	private boolean checkSceneId(String sceneId) {
		PaySceneEnum paySceneEnum = PaySceneEnum.valueOf(sceneId);
		return null != paySceneEnum;
	}
}
