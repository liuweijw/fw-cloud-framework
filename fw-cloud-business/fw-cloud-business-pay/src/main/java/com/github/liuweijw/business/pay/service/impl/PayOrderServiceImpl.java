package com.github.liuweijw.business.pay.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.liuweijw.business.commons.web.jpa.JPAFactoryImpl;
import com.github.liuweijw.business.pay.domain.PayOrder;
import com.github.liuweijw.business.pay.domain.QPayOrder;
import com.github.liuweijw.business.pay.repository.PayOrderRepository;
import com.github.liuweijw.business.pay.service.PayOrderService;
import com.github.liuweijw.commons.pay.constants.PayConstant;
import com.github.liuweijw.commons.utils.StringHelper;
import com.querydsl.jpa.impl.JPAUpdateClause;

@Component
public class PayOrderServiceImpl extends JPAFactoryImpl implements PayOrderService {

	@Autowired
	private PayOrderRepository payOrderRepository;

	@Override
	@Transactional
	public PayOrder updateOrSavePayOrder(PayOrder payOrder) {

		if (null == payOrder) return null;
		if (null == payOrder.getCreateTime()) payOrder.setCreateTime(new Date());
		if (null == payOrder.getUpdateTime()) payOrder.setUpdateTime(new Date());

		return payOrderRepository.saveAndFlush(payOrder);
	}

	@Override
	public PayOrder findPayOrderByOrderId(String payOrderId) {

		if (StringHelper.isBlank(payOrderId)) return null;

		return payOrderRepository.findPayOrderByPayOrderId(payOrderId);
	}

	@Override
	public PayOrder findPayOrderByMchIdAndPayOrderId(String mchId, String payOrderId) {

		if (StringHelper.isBlank(mchId) || StringHelper.isBlank(payOrderId)) return null;

		QPayOrder qPayOrder = QPayOrder.payOrder;
		return this.queryFactory.selectFrom(qPayOrder)
				.where(qPayOrder.mch_id.eq(mchId.trim()))
				.where(qPayOrder.payOrderId.eq(payOrderId.trim()))
				.fetchFirst();
	}

	@Override
	public PayOrder findPayOrderByMchIdAndMchOrderNo(String mchId, String mchOrderNo) {

		if (StringHelper.isBlank(mchId) || StringHelper.isBlank(mchOrderNo)) return null;

		QPayOrder qPayOrder = QPayOrder.payOrder;
		return this.queryFactory.selectFrom(qPayOrder)
				.where(qPayOrder.mch_id.eq(mchId.trim()))
				.where(qPayOrder.mchOrderNo.eq(mchOrderNo.trim()))
				.fetchFirst();

	}

	@Override
	@Transactional
	public boolean updatePayOrderStatus4Paying(String payOrderId, String channelOrderNo) {

		if (StringHelper.isBlank(payOrderId)) return false;

		QPayOrder qPayOrder = QPayOrder.payOrder;
		JPAUpdateClause updateClause = this.queryFactory.update(qPayOrder)
				.set(
						qPayOrder.status,
						PayConstant.PAY_STATUS_PAYING)
				.set(
						qPayOrder.paySuccTime,
						System.currentTimeMillis());
		if (StringHelper.isNotBlank(channelOrderNo)) {
			updateClause.set(qPayOrder.channelOrderNo, channelOrderNo.trim());
		}

		updateClause.where(qPayOrder.payOrderId.eq(payOrderId.trim()))
				.where(
						qPayOrder.status.eq(PayConstant.PAY_STATUS_INIT));

		long num = updateClause.execute();
		return num > 0;
	}

	@Override
	@Transactional
	public boolean updatePayOrderStatus4Success(String payOrderId) {

		if (StringHelper.isBlank(payOrderId)) return false;

		QPayOrder qPayOrder = QPayOrder.payOrder;
		long num = this.queryFactory.update(qPayOrder)
				.set(
						qPayOrder.status,
						PayConstant.PAY_STATUS_SUCCESS)
				.set(
						qPayOrder.paySuccTime,
						System.currentTimeMillis())
				.where(qPayOrder.payOrderId.eq(payOrderId.trim()))
				.where(qPayOrder.status.eq(PayConstant.PAY_STATUS_PAYING))
				.execute();

		return num > 0;
	}

	@Override
	@Transactional
	public boolean updatePayOrderStatus4Complete(String payOrderId) {

		if (StringHelper.isBlank(payOrderId)) return false;

		QPayOrder qPayOrder = QPayOrder.payOrder;
		long num = this.queryFactory.update(qPayOrder)
				.set(
						qPayOrder.status,
						PayConstant.PAY_STATUS_COMPLETE)
				.where(qPayOrder.payOrderId.eq(payOrderId.trim()))
				.where(qPayOrder.status.eq(PayConstant.PAY_STATUS_SUCCESS))
				.execute();

		return num > 0;
	}

	@Override
	@Transactional
	public boolean updateNotify(String payOrderId, int count) {
		// TODO 并发情况下 次数问题待解决

		if (StringHelper.isBlank(payOrderId)) return false;

		QPayOrder qPayOrder = QPayOrder.payOrder;
		long num = this.queryFactory.update(qPayOrder)
				.set(qPayOrder.notifyCount, count)
				.set(
						qPayOrder.lastNotifyTime, System.currentTimeMillis())
				.where(
						qPayOrder.payOrderId.eq(payOrderId.trim()))
				.execute();

		return num > 0;
	}
}
