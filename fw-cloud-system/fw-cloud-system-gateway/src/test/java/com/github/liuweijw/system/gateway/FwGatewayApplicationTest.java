package com.github.liuweijw.system.gateway;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.liuweijw.system.FwGatewayApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FwGatewayApplication.class)
public class FwGatewayApplicationTest {

	@Autowired
	private  StringEncryptor	stringEncryptor;


	@Test
	public void testEnvironmentProperties() {
		System.out.println("=====encrypt  jwt:token:liuweijw======"
				+ stringEncryptor.encrypt("jwt:token:liuweijw"));
		System.out.println("=====decrypt  jwt:token:liuweijw======"
				+ stringEncryptor.decrypt(stringEncryptor.encrypt("jwt:token:liuweijw")));

		System.out.println("=====encrypt   liuweijw======"
				+ stringEncryptor.encrypt("liuweijw")) ;


		System.out.println("=====decrypt   rC/X/8UBBH2bn9Tgfuu7aw========"
				+ stringEncryptor.decrypt("rC/X/8UBBH2bn9Tgfuu7aw==")) ;


		System.out.println("=====decrypt admin  IVTzs5LDfVEsblAFWFgA9w=========="
				+ stringEncryptor.decrypt("IVTzs5LDfVEsblAFWFgA9w==")) ;

	}

	/*public static void   main(String[] args){

		System.out.println("=====jwt:token:liuweijw======"
				+ stringEncryptor.encrypt("jwt:token:liuweijw"));
	}*/
}
