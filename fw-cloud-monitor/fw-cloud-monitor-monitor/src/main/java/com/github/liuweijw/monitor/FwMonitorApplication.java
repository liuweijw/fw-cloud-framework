package com.github.liuweijw.monitor;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

import de.codecentric.boot.admin.config.EnableAdminServer;
import de.codecentric.boot.admin.notify.LoggingNotifier;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;

// 注解开启监控
@EnableAdminServer
@EnableTurbine
@EnableDiscoveryClient
@SpringCloudApplication
public class FwMonitorApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FwMonitorApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FwMonitorApplication.class, args);
	}

	@Configuration
	public static class AppNotifierConfiguration {

		@Bean
		@Primary
		public RemindingNotifier remindingNotifier() {
			RemindingNotifier notifier = new RemindingNotifier(filteringNotifier(loggerNotifier()));
			notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(10));
			return notifier;
		}

		@Scheduled(fixedRate = 1_000L)
		public void remind() {
			remindingNotifier().sendReminders();
		}

		@Bean
		public FilteringNotifier filteringNotifier(Notifier delegate) {
			return new FilteringNotifier(delegate);
		}

		@Bean
		public LoggingNotifier loggerNotifier() {
			return new LoggingNotifier();
		}
	}
}
