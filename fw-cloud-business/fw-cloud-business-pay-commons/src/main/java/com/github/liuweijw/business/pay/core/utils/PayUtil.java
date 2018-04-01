package com.github.liuweijw.business.pay.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.github.liuweijw.business.pay.core.constants.PayConstant;
import com.github.liuweijw.business.pay.core.enums.PayEnum;
import com.github.liuweijw.core.utils.StringHelper;

/**
 * 支付相关工具类
 * 
 * @author liuweijw
 *
 */
public class PayUtil {

	public static Map<String, Object> makeRetMap(String retCode, String retMsg, String resCode, PayEnum payEnum) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(StringHelper.isNotBlank(retCode)) retMap.put(PayConstant.RETURN_PARAM_RETCODE, retCode);
        if(StringHelper.isNotBlank(retMsg)) retMap.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        if(StringHelper.isNotBlank(resCode)) retMap.put(PayConstant.RESULT_PARAM_RESCODE, resCode);
        if(payEnum != null) {
            retMap.put(PayConstant.RESULT_PARAM_ERRCODE, payEnum.getCode());
            retMap.put(PayConstant.RESULT_PARAM_ERRCODEDES, payEnum.getMessage());
        }
        return retMap;
    }
	
	public static Map<String, Object> makeRetMap(String retCode, String retMsg, String resCode, String errCode, String errCodeDesc) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(StringHelper.isNotBlank(retCode)) retMap.put(PayConstant.RETURN_PARAM_RETCODE, retCode);
        if(StringHelper.isNotBlank(retMsg)) retMap.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        if(StringHelper.isNotBlank(resCode)) retMap.put(PayConstant.RESULT_PARAM_RESCODE, resCode);
        if(StringHelper.isNotBlank(errCode)) retMap.put(PayConstant.RESULT_PARAM_ERRCODE, errCode);
        if(StringHelper.isNotBlank(errCodeDesc)) retMap.put(PayConstant.RESULT_PARAM_ERRCODEDES, errCodeDesc);
        return retMap;
    }

    public static Map<String,Object> makeRetData(Map<String,Object> retMap, String resKey) {
        if(retMap.get(PayConstant.RETURN_PARAM_RETCODE).equals(PayConstant.RETURN_VALUE_SUCCESS)) {
            String sign = PayDigestUtil.getSign(retMap, resKey, "payParams");
            retMap.put(PayConstant.RESULT_PARAM_SIGN, sign);
        }
        return retMap;
    }
    
	public static String buildUrlParams(Map<String, Object> paramMap) {
		if (null == paramMap || paramMap.isEmpty()) return "";
		StringBuffer urlParam = new StringBuffer();
		Set<String> keySet = paramMap.keySet();
		int i = 0;
		for (String key : keySet) {
			urlParam.append(key).append("=").append(paramMap.get(key));
			if (++i == keySet.size()) break;
			urlParam.append("&");
		}
		return urlParam.toString();
	}
	
	/**
	 * 验证支付中心签名
	 */
	public static boolean verifyPaySign(Map<String, Object> params, String key,String ...noVerifyParams) {
		Map<String, Object> vParams = new HashMap<String,Object>();
		vParams.putAll(params);
		
		String sign = (String) vParams.get("sign"); // 签名
		vParams.remove("sign"); // 不参与签名
		
		if(null != noVerifyParams && noVerifyParams.length > 0){
			for(String verifyParam : noVerifyParams){
				if(StringHelper.isNotBlank(verifyParam)) vParams.remove(verifyParam);
			}
		}
		String checkSign = PayDigestUtil.getSign(vParams, key);
		if (!checkSign.equalsIgnoreCase(sign)) {
			return false;
		}
		return true;
	}
	
	public static boolean verifyPaySign(Map<String, Object> params, String key) {
		return verifyPaySign(params, key, "");
	}

}
