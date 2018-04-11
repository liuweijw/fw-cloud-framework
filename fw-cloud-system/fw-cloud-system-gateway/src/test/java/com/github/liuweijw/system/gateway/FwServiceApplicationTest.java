package com.github.liuweijw.system.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.liuweijw.system.gateway.FwGatewayApplication;
import com.github.liuweijw.system.gateway.service.MenuPermissionService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FwGatewayApplication.class)
public class FwServiceApplicationTest {

	@Autowired
	private MenuPermissionService	menuService;

	@Test
	public void testMenuService() {
		menuService.findMenuByRole("admins");
	}
}
