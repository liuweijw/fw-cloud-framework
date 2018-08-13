package cloud.simple.service.model;

import javax.persistence.Column;
import javax.persistence.Table;

import cloud.simple.service.base.BaseEntity;

@Table(name = "`sys_admin_post`")
public class SysAdminPost  extends BaseEntity {
	private static final long serialVersionUID = -3845160579870153375L;

    /**
     * 岗位名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 岗位备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 数据创建时间
     */
    @Column(name = "`create_time`")
    private Integer createTime;

    /**
     * 状态1启用,0禁用
     */
    @Column(name = "`status`")
    private Byte status;


    /**
     * 获取岗位名称
     *
     * @return name - 岗位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置岗位名称
     *
     * @param name 岗位名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取岗位备注
     *
     * @return remark - 岗位备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置岗位备注
     *
     * @param remark 岗位备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取数据创建时间
     *
     * @return create_time - 数据创建时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置数据创建时间
     *
     * @param createTime 数据创建时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取状态1启用,0禁用
     *
     * @return status - 状态1启用,0禁用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态1启用,0禁用
     *
     * @param status 状态1启用,0禁用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}