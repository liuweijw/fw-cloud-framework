package cloud.simple.service.model;

import javax.persistence.Column;
import javax.persistence.Table;

import cloud.simple.service.base.BaseEntity;

@Table(name = "`sys_system_config`")
public class SysSystemConfig  extends BaseEntity {
	private static final long serialVersionUID = -3343040004793640240L;


    @Column(name = "`name`")
    private String name;

    /**
     * 配置值
     */
    @Column(name = "`value`")
    private String value;

    /**
     * 配置分组
     */
    @Column(name = "`group`")
    private Byte group;

    /**
     * 1需要登录后才能获取，0不需要登录即可获取
     */
    @Column(name = "`need_auth`")
    private Byte needAuth;


    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取配置值
     *
     * @return value - 配置值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置配置值
     *
     * @param value 配置值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取配置分组
     *
     * @return group - 配置分组
     */
    public Byte getGroup() {
        return group;
    }

    /**
     * 设置配置分组
     *
     * @param group 配置分组
     */
    public void setGroup(Byte group) {
        this.group = group;
    }

    /**
     * 获取1需要登录后才能获取，0不需要登录即可获取
     *
     * @return need_auth - 1需要登录后才能获取，0不需要登录即可获取
     */
    public Byte getNeedAuth() {
        return needAuth;
    }

    /**
     * 设置1需要登录后才能获取，0不需要登录即可获取
     *
     * @param needAuth 1需要登录后才能获取，0不需要登录即可获取
     */
    public void setNeedAuth(Byte needAuth) {
        this.needAuth = needAuth;
    }
}