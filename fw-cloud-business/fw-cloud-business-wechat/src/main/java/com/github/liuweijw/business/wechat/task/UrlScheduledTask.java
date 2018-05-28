package com.github.liuweijw.business.wechat.task;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.liuweijw.business.wechat.service.UrlInfoService;

@Slf4j
@Component
public class UrlScheduledTask {

	@Autowired
	private UrlInfoService	urlInfoService;

	// @Scheduled(cron = "0 0/10 * * * ?")
	// public void execMemoryUrlScheduledTask() {
	// log.info("【execMemoryUrlScheduledTask】:清除内存数据记录");
	// Map<String, UrlMemory> mapUrls = UrlInMemory.getInstance().getUrls();
	// mapUrls.forEach((k, v) -> {
	// if (v.isExpire()) mapUrls.remove(k);
	// });
	// }

	@Scheduled(cron = "0 0/30 * * * ?")
	public void execUrlScheduledTask() {
		log.info("【execUrlScheduledTask】:清除失效数据记录");
		long l = urlInfoService.clearAllExpire();
		log.info("【execUrlScheduledTask】:清除失效数据记录size|" + l);
	}

}
