package com.github.liuweijw.business.wechat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.liuweijw.commons.pay.beans.MsgBean;
import com.github.liuweijw.commons.pay.enums.WxTemplateEnum;
import com.github.liuweijw.commons.utils.StringHelper;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.WxMpTemplateMessageBuilder;

@Slf4j
@Component
public class MessageServiceImpl implements MessageService {

	@Autowired
	private WxMpService wxMpService;

	@Override
	@Deprecated
	public boolean sendWeixinTemplateMessage(WxTemplateEnum wxTemplateEnum, MsgBean msgBean)
			throws WxErrorException {
		if (null == wxTemplateEnum || null == msgBean) return false;

		WxMpTemplateMessageBuilder templateMessage = WxMpTemplateMessage.builder();
		templateMessage.templateId(wxTemplateEnum.getTemplateId());
		if (!StringHelper.isBlank(msgBean.getLinkUrl())) templateMessage.url(msgBean.getLinkUrl());

		List<WxMpTemplateData> data = new ArrayList<WxMpTemplateData>();
		if (WxTemplateEnum.PUT_ORDER_SUC == wxTemplateEnum) {
			/**
			 * {{first.DATA}} 商家名称：{{keyword1.DATA}} 下单时间：{{keyword2.DATA}} 商品明细：{{keyword3.DATA}} 订单金额：{{keyword4.DATA}} {{remark.DATA}}
			 */
			data.add(new WxMpTemplateData("first", msgBean.getFirst(), "#173177"));
			data.add(new WxMpTemplateData("keyword1", msgBean.getKeyword1(), "#173177"));
			data.add(new WxMpTemplateData("keyword2", msgBean.getKeyword2(), "#173177"));
			data.add(new WxMpTemplateData("keyword3", msgBean.getKeyword3(), "#173177"));
			data.add(new WxMpTemplateData("keyword4", msgBean.getKeyword4(), "#173177"));
			data.add(new WxMpTemplateData("remark", msgBean.getRemark(), "#173177"));
		} else if (WxTemplateEnum.PUT_ORDER_MSG == wxTemplateEnum) {
			/**
			 * {{first.DATA}} 订单号：{{keyword1.DATA}} 金额：{{keyword2.DATA}} 时间：{{keyword3.DATA}} 买家：{{keyword4.DATA}} 联系方式：{{keyword5.DATA}} {{remark.DATA}}
			 */
			data.add(new WxMpTemplateData("first", msgBean.getFirst(), "#173177"));
			data.add(new WxMpTemplateData("keyword1", msgBean.getKeyword1(), "#173177"));
			data.add(new WxMpTemplateData("keyword2", msgBean.getKeyword2(), "#173177"));
			data.add(new WxMpTemplateData("keyword3", msgBean.getKeyword3(), "#173177"));
			data.add(new WxMpTemplateData("keyword4", msgBean.getKeyword4(), "#173177"));
			data.add(new WxMpTemplateData("keyword5", msgBean.getKeyword5(), "#173177"));
			data.add(new WxMpTemplateData("remark", msgBean.getRemark(), "#173177"));
		} else if (WxTemplateEnum.PAY_ORDER_SUC == wxTemplateEnum) {
			/**
			 * {{first.DATA}} 商品名称：{{keyword1.DATA}} 订单编号：{{keyword2.DATA}} 支付金额：{{keyword3.DATA}} {{remark.DATA}}
			 */
			data.add(new WxMpTemplateData("first", msgBean.getFirst(), "#173177"));
			data.add(new WxMpTemplateData("keyword1", msgBean.getKeyword1(), "#173177"));
			data.add(new WxMpTemplateData("keyword2", msgBean.getKeyword2(), "#173177"));
			data.add(new WxMpTemplateData("keyword3", msgBean.getKeyword3(), "#173177"));
			data.add(new WxMpTemplateData("remark", msgBean.getRemark(), "#173177"));
		} else if (WxTemplateEnum.PUT_ORDER_CANCEL == wxTemplateEnum) {
			/**
			 * {{first.DATA}} 订单编号：{{keyword1.DATA}} 订单金额：{{keyword2.DATA}} {{remark.DATA}}
			 */
			data.add(new WxMpTemplateData("first", msgBean.getFirst(), "#173177"));
			data.add(new WxMpTemplateData("keyword1", msgBean.getKeyword1(), "#173177"));
			data.add(new WxMpTemplateData("keyword2", msgBean.getKeyword2(), "#173177"));
			data.add(new WxMpTemplateData("remark", msgBean.getRemark(), "#173177"));
		} else if (WxTemplateEnum.PUT_ORDER_MODIFY == wxTemplateEnum) {
			/**
			 * {{first.DATA}} 门店名称：{{keyword1.DATA}} 订单编号：{{keyword2.DATA}} 修改内容：{{keyword3.DATA}} 修改时间：{{keyword4.DATA}} {{remark.DATA}}
			 */
			data.add(new WxMpTemplateData("first", msgBean.getFirst(), "#173177"));
			data.add(new WxMpTemplateData("keyword1", msgBean.getKeyword1(), "#173177"));
			data.add(new WxMpTemplateData("keyword2", msgBean.getKeyword2(), "#173177"));
			data.add(new WxMpTemplateData("keyword3", msgBean.getKeyword3(), "#173177"));
			data.add(new WxMpTemplateData("keyword4", msgBean.getKeyword4(), "#173177"));
			data.add(new WxMpTemplateData("remark", msgBean.getRemark(), "#173177"));
		}
		templateMessage.data(data);

		WxMpTemplateMessage toMessage = null;

		List<String> toUsers = msgBean.getToUser();
		if (null != toUsers && toUsers.size() > 0) {
			for (String toUser : toUsers) {
				templateMessage.toUser(toUser.trim());

				toMessage = templateMessage.build();
				log.info("|send template msg start|" + toMessage.toString());
				String returnMsg = wxMpService.getTemplateMsgService().sendTemplateMsg(toMessage);
				log.info("|send template msg end  |" + returnMsg);
			}
		}
		return true;
	}

	@Override
	public boolean sendWeixinCurrencyTemplateMessage(MsgBean msgBean) throws WxErrorException {
		if (null == msgBean)
			throw new WxErrorException(WxError.builder().errorCode(500).errorMsg("消息内容不能为空").build());
		if (StringHelper.isBlank(msgBean.getTemplateId()))
			throw new WxErrorException(WxError.builder().errorCode(500).errorMsg("消息内容不能为空").build());

		WxMpTemplateMessageBuilder templateMessage = WxMpTemplateMessage.builder();
		templateMessage.templateId(msgBean.getTemplateId());
		if (!StringHelper.isBlank(msgBean.getLinkUrl())) templateMessage.url(msgBean.getLinkUrl());

		List<WxMpTemplateData> data = new ArrayList<WxMpTemplateData>();

		if (StringHelper.isNotBlank(msgBean.getFirst()))
			data.add(new WxMpTemplateData("first", msgBean.getFirst(), "#173177"));
		if (StringHelper.isNotBlank(msgBean.getKeyword1()))
			data.add(new WxMpTemplateData("keyword1", msgBean.getKeyword1(), "#173177"));
		if (StringHelper.isNotBlank(msgBean.getKeyword2()))
			data.add(new WxMpTemplateData("keyword2", msgBean.getKeyword2(), "#173177"));
		if (StringHelper.isNotBlank(msgBean.getKeyword3()))
			data.add(new WxMpTemplateData("keyword3", msgBean.getKeyword3(), "#173177"));
		if (StringHelper.isNotBlank(msgBean.getKeyword4()))
			data.add(new WxMpTemplateData("keyword4", msgBean.getKeyword4(), "#173177"));
		if (StringHelper.isNotBlank(msgBean.getKeyword5()))
			data.add(new WxMpTemplateData("keyword5", msgBean.getKeyword5(), "#173177"));
		if (StringHelper.isNotBlank(msgBean.getRemark()))
			data.add(new WxMpTemplateData("remark", msgBean.getRemark(), "#173177"));

		templateMessage.data(data);

		WxMpTemplateMessage toMessage = null;

		List<String> toUsers = msgBean.getToUser();
		if (null != toUsers && toUsers.size() > 0) {
			for (String toUser : toUsers) {
				templateMessage.toUser(toUser.trim());

				toMessage = templateMessage.build();
				log.info("|send template msg start|" + toMessage.toString());
				String returnMsg = wxMpService.getTemplateMsgService().sendTemplateMsg(toMessage);
				log.info("|send template msg end  |" + returnMsg);
			}
		}
		return true;
	}

}
