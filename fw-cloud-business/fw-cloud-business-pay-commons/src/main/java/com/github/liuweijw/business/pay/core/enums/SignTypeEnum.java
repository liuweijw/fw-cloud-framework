package com.github.liuweijw.business.pay.core.enums;

/**
 * 签名类型
 * 
 * @author liuweijw
 *
 */
public enum SignTypeEnum {

	NOT_SIGN(0),	// 明文
	
    SHA1_SIGN(1); 	// SHA-1签名

    private Integer code;

    private SignTypeEnum(Integer code) {
        this.code = code;
    }

	public Integer getCode() {
		return code;
	}
    
	public static SignTypeEnum getSignTypeEnum(Integer code) {
        if (code == null) return null;

        SignTypeEnum[] values =SignTypeEnum.values();
        for (SignTypeEnum e : values) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
	
}
