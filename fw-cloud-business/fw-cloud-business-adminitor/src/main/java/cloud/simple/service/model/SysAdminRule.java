package cloud.simple.service.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import cloud.simple.service.base.BaseEntity;

@Table(name = "`sys_admin_rule`")
public class SysAdminRule  extends BaseEntity{
	private static final long serialVersionUID = 3783338463831001070L;

    /**
     * 名称
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 定义
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 级别。1模块,2控制器,3操作
     */
    @Column(name = "`level`")
    private Byte level;

    /**
     * 父id，默认0
     */
    @Column(name = "`pid`")
    private Integer pid;

    /**
     * 状态，1启用，0禁用
     */
    @Column(name = "`status`")
    private Byte status;
    
    /**
     * 子权限 
     */
    @Transient
    private List<SysAdminRule> child;


    /**
     * 获取名称
     *
     * @return title - 名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置名称
     *
     * @param title 名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取定义
     *
     * @return name - 定义
     */
    public String getName() {
        return name;
    }

    /**
     * 设置定义
     *
     * @param name 定义
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取级别。1模块,2控制器,3操作
     *
     * @return level - 级别。1模块,2控制器,3操作
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置级别。1模块,2控制器,3操作
     *
     * @param level 级别。1模块,2控制器,3操作
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * 获取父id，默认0
     *
     * @return pid - 父id，默认0
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置父id，默认0
     *
     * @param pid 父id，默认0
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取状态，1启用，0禁用
     *
     * @return status - 状态，1启用，0禁用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态，1启用，0禁用
     *
     * @param status 状态，1启用，0禁用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

	public List<SysAdminRule> getChild() {
		return child;
	}

	public void setChild(List<SysAdminRule> child) {
		this.child = child;
	}
    
    
}