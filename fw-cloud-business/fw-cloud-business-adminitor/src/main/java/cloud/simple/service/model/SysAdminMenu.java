package cloud.simple.service.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import cloud.simple.service.base.BaseEntity;

@Table(name = "`sys_admin_menu`")
public class SysAdminMenu  extends BaseEntity {
	private static final long serialVersionUID = 6521017462166057129L;


    /**
     * 上级菜单ID
     */
    @Column(name = "`pid`")
    private Integer pid;

    /**
     * 菜单名称
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 链接地址
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 图标
     */
    @Column(name = "`icon`")
    private String icon;

    /**
     * 菜单类型
     */
    @Column(name = "`menu_type`")
    //@JSONField(name="menu_type")  
    private Byte menuType;

    /**
     * 排序（同级有效）
     */
    @Column(name = "`sort`")
    private Byte sort;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private Byte status;

    /**
     * 权限id
     */
    @Column(name = "`rule_id`")
    //@JSONField(name="rule_id") 
    private Integer ruleId;
    
    @Column(name = "`rule_name`")
    private String ruleName;

    @Column(name = "`module`")
    private String module;

    /**
     * 三级菜单吗
     */
    @Column(name = "`menu`")
    private String menu;
    
    /**
     * 子菜单 
     */
    @Transient
    private List<SysAdminMenu> child;
    /**
     * 是否选中
     */
    @Transient
    private Boolean selected = false;
    /**
     * 级别
     */
    @Transient
    private Integer level;
    
    /**
     * 全名
     */
    @Transient
    private String fullName;
    
    /**
     * 关键权限
     */
    @Transient
    private SysAdminRule rule;
    
    /**
     * 获取上级菜单ID
     *
     * @return pid - 上级菜单ID
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置上级菜单ID
     *
     * @param pid 上级菜单ID
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取菜单名称
     *
     * @return title - 菜单名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置菜单名称
     *
     * @param title 菜单名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取链接地址
     *
     * @return url - 链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接地址
     *
     * @param url 链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取菜单类型
     *
     * @return menu_type - 菜单类型
     */
    public Byte getMenuType() {
        return menuType;
    }

    /**
     * 设置菜单类型
     *
     * @param menuType 菜单类型
     */
    public void setMenuType(Byte menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取排序（同级有效）
     *
     * @return sort - 排序（同级有效）
     */
    public Byte getSort() {
        return sort;
    }

    /**
     * 设置排序（同级有效）
     *
     * @param sort 排序（同级有效）
     */
    public void setSort(Byte sort) {
        this.sort = sort;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取权限id
     *
     * @return rule_id - 权限id
     */
    public Integer getRuleId() {
        return ruleId;
    }

    /**
     * 设置权限id
     *
     * @param ruleId 权限id
     */
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * @return module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 获取三级菜单吗
     *
     * @return menu - 三级菜单吗
     */
    public String getMenu() {
        return menu;
    }

    /**
     * 设置三级菜单吗
     *
     * @param menu 三级菜单吗
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }

	public List<SysAdminMenu> getChild() {
		return child;
	}

	public void setChild(List<SysAdminMenu> child) {
		this.child = child;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public SysAdminRule getRule() {
		return rule;
	}

	public void setRule(SysAdminRule rule) {
		this.rule = rule;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	
}