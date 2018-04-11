package com.github.liuweijw.business.pay.service;

import com.github.liuweijw.business.pay.domain.PayOrder;

public interface PayOrderService {

	public PayOrder updateOrSavePayOrder(PayOrder payOrder);

	public PayOrder findPayOrderByOrderId(String payOrderId);

	public PayOrder findPayOrderByMchIdAndPayOrderId(String mchId, String payOrderId);

	public PayOrder findPayOrderByMchIdAndMchOrderNo(String mchId, String mchOrderNo);

	public boolean updatePayOrderStatus4Paying(String payOrderId, String channelOrderNo);

	public boolean updatePayOrderStatus4Success(String payOrderId);

	public boolean updatePayOrderStatus4Complete(String payOrderId);

	public boolean updateNotify(String payOrderId, int count);

}
