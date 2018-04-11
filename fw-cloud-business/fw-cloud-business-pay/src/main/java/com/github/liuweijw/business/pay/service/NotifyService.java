package com.github.liuweijw.business.pay.service;

import com.github.liuweijw.business.pay.beans.NotifyBean;
import com.github.liuweijw.business.pay.domain.PayOrder;

public interface NotifyService {

	public void notifyPayOrder(PayOrder payOrder);

	public void notifyPayOrder(NotifyBean notifyBean, int count);

}
