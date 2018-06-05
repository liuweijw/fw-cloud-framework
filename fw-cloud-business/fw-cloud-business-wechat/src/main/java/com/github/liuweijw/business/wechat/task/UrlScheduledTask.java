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

	@Scheduled(cron = "0 0/50 * * * ?")
	public void execUrlScheduledTask() {
		log.info("【execUrlScheduledTask】:清除失效数据记录");
		long l = urlInfoService.clearAllExpire();
		log.info("【execUrlScheduledTask】:清除失效数据记录size|" + l);
	}

}
