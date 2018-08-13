package cloud.simple.service.model;

import javax.persistence.Column;
import javax.persistence.Table;

import cloud.simple.service.base.BaseEntity;

@Table(name = "`sys_admin_structure`")
public class SysAdminStructure  extends BaseEntity {
	private static final long serialVersionUID = 8560760088975512813L;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`pid`")
    private Integer pid;

    @Column(name = "`status`")
    private Byte status;


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