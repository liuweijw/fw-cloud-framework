package cloud.simple.service.model;

import javax.persistence.Column;
import javax.persistence.Table;

import cloud.simple.service.base.BaseEntity;

@Table(name = "`sys_admin_group`")
public class SysAdminGroup  extends BaseEntity {
	private static final long serialVersionUID = 4199719159643822719L;

    @Column(name = "`title`")
    private String title;

    @Column(name = "`rules`")
    private String rules;

    @Column(name = "`pid`")
    private Integer pid;

    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`status`")
    private Byte status;


    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return rules
     */
    public String getRules() {
        return rules;
    }

    /**
     * @param rules
     */
    public void setRules(String rules) {
        this.rules = rules;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return remark
     */
	public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
	public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}