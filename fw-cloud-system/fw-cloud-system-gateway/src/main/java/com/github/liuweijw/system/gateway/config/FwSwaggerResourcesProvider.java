package com.github.liuweijw.system.gateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.core.commons.constants.ServiceIdConstant;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * swagger 资源配置
 * 
 * @author liuweijw
 */
@Component
@Primary
public class FwSwaggerResourcesProvider implements SwaggerResourcesProvider {

	private final RouteLocator routeLocator;

	public FwSwaggerResourcesProvider(RouteLocator routeLocator) {
		this.routeLocator = routeLocator;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<Route> routes = routeLocator.getRoutes();
		routes.forEach(route -> {
			// swagger排除 auth 模块
			if (!StringHelper.contains(route.getId(), ServiceIdConstant.AUTH_SERVICE)) {
				resources.add(buildSwaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs")));
			}
		});
		return resources;
	}

	private SwaggerResource buildSwaggerResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}

}
