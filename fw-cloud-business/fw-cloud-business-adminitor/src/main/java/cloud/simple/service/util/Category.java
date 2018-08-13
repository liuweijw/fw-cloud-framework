package cloud.simple.service.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import cloud.simple.service.contants.Constant;
/**
 * 分类管理
 */
public class Category {
	//原始的分类数据
	private List<Map<String, Object>> rawList = Lists.newArrayList();
	//格式化后的分类
	private List<Map<String, Object>> formatList = Lists.newArrayList();
	//字段映射
	private Map<String,String> fields = null;
	
	/**
	 * @param fields 
	 * 			cid 当前分类id
	 * 			fid 当前分类的父id
	 * @param rawList
	 */
	public Category(Map<String,String> fields, List<Map<String, Object>> rawList) {
		this.fields = fields;
		this.rawList = rawList;
		
		fields.put("cid", fields.get("cid") != null ? fields.get("cid") : "cid");
		fields.put("fid", fields.get("fid") != null ? fields.get("fid") : "fid");
		fields.put("name", fields.get("name") != null ? fields.get("name") : "name");
		fields.put("fullname", fields.get("fullname") != null ? fields.get("fullname") : "fullname");
	}
	
	public List<Map<String, Object>> getRawList() {
		return rawList;
	}
	public void setRawList(List<Map<String, Object>> rawList) {
		this.rawList = rawList;
	}
	public List<Map<String, Object>> getFormatList() {
		return formatList;
	}
	public void setFormatList(List<Map<String, Object>> formatList) {
		this.formatList = formatList;
	}
	
	
	/**
	 * 返回指定的上级分类的所有同一级子分类
	 * @param fid 查询的分类id
	 * @return
	 */
	public List<Map<String, Object>> getChild(Object fid) {
		List<Map<String, Object>> results = Lists.newArrayList();
		for (Map<String, Object> m : rawList) {
			if(fid.equals(m.get(fields.get("fid"))))
				results.add(m);
		}
		return results;
	}
	
	/**
	 * 递归格式化分类前的字符
	 * @param pid 分类id
	 * @param space 空白
	 * @param level 级别
	 * @param p_name 父名称
	 * @return
	 */
	private void _searchList( Object cid, String space, int level, Object pname){
		List<Map<String, Object>> childs = this.getChild(cid);
		//如果没有下级分类，结束递归
		if(CollectionUtils.isEmpty(childs)) {
			return;
		}
		int n = childs.size();
		int m = 1;
		for (int i = 0; i < n; i++) {
			Map<String, Object> child = childs.get(i);
			String pre = "";
			String pad = "";
			if (n == m) {
				pre = Constant.ICON[2];
			} else {
				pre = Constant.ICON[1];
				pad = StringUtils.isBlank(space) ? Constant.ICON[0] : "";
			}
			child.put("p_title", pname);
			child.put("else", child.get(fields.get("name")));
			child.put(fields.get("fullname"), (!cid.equals(0)? space + pre  : "") + child.get(fields.get("name")));
			child.put("level", level);
			formatList.add(child);
			this._searchList(child.get(fields.get("cid")), space + pad + "  ", level + 1, child.get("else"));
			m++;
		}
	}
	
	/**
	 * 递归格式化分类
	 * @param cid 起始分类
	 * @return
	 */
	public List<Map<String, Object>> getList(Object cid) {
		this._searchList(cid, "", 1, "");
		return formatList;
	}

}
