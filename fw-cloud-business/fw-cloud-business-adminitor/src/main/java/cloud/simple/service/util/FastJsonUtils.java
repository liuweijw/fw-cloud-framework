package cloud.simple.service.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json工具
 */
public class FastJsonUtils {
	
	 public static final String SUCCESS_MSG = "数据加载成功"; 

	 private static SerializerFeature[] features = {SerializerFeature.WriteMapNullValue,
		 											SerializerFeature.WriteNullStringAsEmpty,
		 											SerializerFeature.WriteNullListAsEmpty,
		 											SerializerFeature.WriteNullNumberAsZero,
		 											SerializerFeature.WriteNullBooleanAsFalse,
		 											SerializerFeature.WriteDateUseDateFormat,
		 											SerializerFeature.DisableCircularReferenceDetect};  
	 
	 /**
	 * 生成json返回结果
	 */
	public static String resultSuccess(Integer code,String msg,Object data){
		 Map<String,Object> rs= new HashMap<String,Object>();
		 rs.put("code",code);
		 rs.put("msg",StringUtils.isNotEmpty(msg)?msg:SUCCESS_MSG);
		 rs.put("data", data==null ? new Object() : data);
		 rs.put("error","");
		 return toString(rs);
	}
	
	public static String resultError(Integer code,String error,Object data){
		 Map<String,Object> rs= new HashMap<String,Object>();
		 rs.put("code",code);
		 rs.put("data", data==null ? new Object() : data);
		 rs.put("error",StringUtils.isNotEmpty(error)?error:"");
		 return toString(rs);
	}
	
	 /**
	 * 生成json返回结果
	 */
	public static String resultList(Integer code,String msg,Integer pageNo,Integer pageSize,Object data){
		 Map<String,Object> rs= new HashMap<String,Object>();
		 rs.put("code",code);
		 rs.put("msg",StringUtils.isNotEmpty(msg)?msg:SUCCESS_MSG);
		 rs.put("data", data==null ? new Object() : data);
		 rs.put("pageNo", pageNo == null ? 0 : pageNo);
		 rs.put("pageSize", pageSize == null ? 10 : pageSize);
		 return toString(rs);
	}
	
	/**
	 * 生成json返回结果
	 */
	public static String resultFeatures(Integer code,String msg,Object data,SerializerFeature... feature){
		Map<String,Object> rs= new HashMap<String,Object>();
		rs.put("code",code);
		rs.put("msg",StringUtils.isNotEmpty(msg)?msg:SUCCESS_MSG);
		rs.put("data", data==null ? new Object() : data);
		 return JSON.toJSONString(rs, feature);
	}
	
	/**
	 * 生成json返回结果
	 */
	public static String resultDate(Integer code,String msg,Object data,String dateFormat){
		 Map<String,Object> rs= new HashMap<String,Object>();
		 rs.put("code",code);
		 rs.put("msg",StringUtils.isNotEmpty(msg)?msg:SUCCESS_MSG);
		 rs.put("data", data==null ? new Object() : data);
		 return JSON.toJSONStringWithDateFormat(rs, dateFormat, features);
	}
	/**
	 * 生成json返回结果,包含字段
	 */
	public static String resultIncludes(Integer code,String msg,Object data,String ...properties){
		 Map<String,Object> rs= new HashMap<String,Object>();
		 rs.put("code",code);
		 rs.put("msg",StringUtils.isNotEmpty(msg)?msg:SUCCESS_MSG);
		 rs.put("data", data==null ? new Object() : data);
		 return toStringIncludes(rs,properties);
	}
	
	/**
	 * 生成json返回结果,包含字段
	 */
	public static String resultExcludes(Integer code,String msg,Object data,Class<?> type,String ...properties){
		 Map<String,Object> rs= new HashMap<String,Object>();
		 rs.put("code",code);
		 rs.put("msg",StringUtils.isNotEmpty(msg)?msg:SUCCESS_MSG);
		 rs.put("data", data==null ? new Object() : data);
		 return toStringExcludes(rs,type,properties);
	}
	
	/**
	 * 生成json字符串
	 */
	public static String toString(Object data){
		 return JSON.toJSONString(data, features);
	}
	
	/**
	 * 生成json字符串
	 */
	private static String toStringIncludes(Object data,String ...properties){
		 PropertyFilter filter = new PropertyFilter() {
		      @Override
			public boolean apply(Object source, String name, Object value) {
		    	  if(source.getClass() == HashMap.class && ("code".equals(name) || "data".equals(name) || "msg".equals(name))){
		    		  return true;
		    	  }
		    	  for(String pro : properties){
		    		  if(pro.equals(name)){
		    			  return true;
		    		  }
		    	  }
		          return false;
		      }
		 };
		 return JSON.toJSONString(data, filter, features); 
	}
	
	/**
	 * 排除字段
	 * @param args
	 */
	private static String toStringExcludes(Object data,Class<?> type,String ...properties){
		 PropertyFilter filter = new PropertyFilter() {
		      @Override
			public boolean apply(Object source, String name, Object value) {
		    	  if(source.getClass() == type){
			    	  for(String pro : properties){
		    			  if(pro.equals(name)){
		    				  return false;
		    			  }
		    		  }
		    	  }
		    	  return true;
		      }
		 };
		 return JSON.toJSONString(data, filter, features); 
	}
	
}
