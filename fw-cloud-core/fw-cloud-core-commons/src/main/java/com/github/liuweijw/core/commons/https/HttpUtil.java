package com.github.liuweijw.core.commons.https;

import org.springframework.web.client.RestTemplate;

import com.github.liuweijw.commons.utils.StringHelper;

/**
 * 通过请求URL获取不同类别的RestTemplate
 * 
 * @author liuweijw
 */
public class HttpUtil {

	public static final String	HTTPS	= "https";

	public static RestTemplate restTemplate(String reqUrl) {
		if (StringHelper.isBlank(reqUrl)) return new RestTemplate();

		return reqUrl.startsWith(HTTPS) ? new RestTemplate(new HttpsClientRequestFactory())
				: new RestTemplate();
	}
}
