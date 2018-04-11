package com.github.liuweijw.business.commons.beans;

/**
 * 分页bean的扩展
 * 
 * @author liuweijw
 * @param <T>
 */
public class ExtentPageBean<T> extends PageBean<T> {

	private String	key1;

	private String	key2;

	private String	key3;

	private String	key4;

	private Integer	num1	= 0;

	private Integer	num2	= 0;

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getKey4() {
		return key4;
	}

	public void setKey4(String key4) {
		this.key4 = key4;
	}

	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

}
