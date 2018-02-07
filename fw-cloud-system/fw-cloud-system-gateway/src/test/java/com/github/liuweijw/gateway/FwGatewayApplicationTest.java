package com.github.liuweijw.gateway;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FwGatewayApplication.class)
public class FwGatewayApplicationTest {

	@Autowired
	private StringEncryptor stringEncryptor;

	@Test
	public void testEnvironmentProperties() {
		System.out.println("=================="+stringEncryptor.encrypt("guest")); // tbrYcuNRFv1gflhdhpuY0g==
		System.out.println("=================="+stringEncryptor.encrypt("root")); // rC/X/8UBBH2bn9Tgfuu7aw==
		System.out.println("=================="+stringEncryptor.encrypt("admin"));// IVTzs5LDfVEsblAFWFgA9w==
	}

}
