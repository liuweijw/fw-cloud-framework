package com.github.liuweijw.monitor.filter;

import lombok.extern.slf4j.Slf4j;

import com.xiaoleilu.hutool.date.DateUtil;

import de.codecentric.boot.admin.event.ClientApplicationEvent;
import de.codecentric.boot.admin.event.ClientApplicationStatusChangedEvent;
import de.codecentric.boot.admin.notify.AbstractStatusChangeNotifier;

/**
 * 服务状态Notifier
 */
@Slf4j
public class StatusChangeNotifier extends AbstractStatusChangeNotifier {

	public static final String	STATUS_CHANGE	= "STATUS_CHANGE";

	public StatusChangeNotifier() {
	}

	/**
	 * 判断是否是状态通知
	 */
	@Override
	protected boolean shouldNotify(ClientApplicationEvent event) {
		if (!STATUS_CHANGE.equals(event.getType())) return Boolean.FALSE;

		if (event.getApplication().getStatusInfo().isOffline()
				|| event.getApplication().getStatusInfo().isDown()) return Boolean.TRUE;

		return Boolean.FALSE;
	}

	/**
	 * 系统接收通知逻辑
	 */
	@Override
	protected void doNotify(ClientApplicationEvent event) {
		if (event instanceof ClientApplicationStatusChangedEvent) {
			log.info("Application {} ({}) is {}", event.getApplication().getName(), event
					.getApplication().getId(), ((ClientApplicationStatusChangedEvent) event)
					.getTo().getStatus());
			String text = String.format("服务应用:%s 服务ID:%s 下线时间：%s",
					event.getApplication().getName(), event.getApplication().getId(), DateUtil
							.date(event.getTimestamp()).toString());
			log.info("接收通知内容：{}", text);
		} else {
			log.info("Application {} ({}) {}", event.getApplication().getName(), event
					.getApplication().getId(), event.getType());
		}
	}

}
