package com.github.liuweijw.business.pay.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 回调通知Bean
 * 
 * @author liuweijw
 */
@Setter
@Getter
public class NotifyBean implements Serializable {

	private static final long	serialVersionUID	= -235116261615512831L;

	private String				method;

	private String				url;

	private String				orderId;

	private Integer				count;

	private Long				createTime;

}
