package com.github.liuweijw.business.admin.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.liuweijw.core.commons.constants.SecurityConstant;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * @author liuweijw
 */
@Configuration
public class KaptchaConfig {

	private static final String	KAPTCHA_BORDER						= "kaptcha.border";

	private static final String	KAPTCHA_TEXTPRODUCER_FONT_COLOR		= "kaptcha.textproducer.font.color";

	private static final String	KAPTCHA_TEXTPRODUCER_CHAR_SPACE		= "kaptcha.textproducer.char.space";

	private static final String	KAPTCHA_IMAGE_WIDTH					= "kaptcha.image.width";

	private static final String	KAPTCHA_IMAGE_HEIGHT				= "kaptcha.image.height";

	private static final String	KAPTCHA_TEXTPRODUCER_CHAR_LENGTH	= "kaptcha.textproducer.char.length";

	private static final Object	KAPTCHA_IMAGE_FONT_SIZE				= "kaptcha.textproducer.font.size";

	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put(KAPTCHA_BORDER, SecurityConstant.DEFAULT_IMAGE_BORDER);
		properties.put(KAPTCHA_TEXTPRODUCER_FONT_COLOR, SecurityConstant.DEFAULT_COLOR_FONT);
		properties.put(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, SecurityConstant.DEFAULT_CHAR_SPACE);
		properties.put(KAPTCHA_IMAGE_WIDTH, SecurityConstant.DEFAULT_IMAGE_WIDTH);
		properties.put(KAPTCHA_IMAGE_HEIGHT, SecurityConstant.DEFAULT_IMAGE_HEIGHT);
		properties.put(KAPTCHA_IMAGE_FONT_SIZE, SecurityConstant.DEFAULT_IMAGE_FONT_SIZE);
		properties.put(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, SecurityConstant.DEFAULT_IMAGE_LENGTH);
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
