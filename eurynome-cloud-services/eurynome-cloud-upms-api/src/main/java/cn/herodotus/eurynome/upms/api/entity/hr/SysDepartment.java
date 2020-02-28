package cn.herodotus.eurynome.upms.api.entity.hr;

import cn.herodotus.eurynome.component.data.base.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: 部门信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/19 16:40
 */
@Entity
@Table(name = "sys_department", indexes = {@Index(name = "sys_department_id_idx", columnList = "department_id")})
public class SysDepartment extends BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "department_id", length = 64)
    private String departmentId;

    @Column(name = "department_name", length = 200)
    private String departmentName;

    @Column(name = "a4_biz_dept_id", length = 64)
    private String a4BizDeptId;

    @Column(name = "biz_dept_code", length = 60)
    private String bizDeptCode;

    @Column(name = "biz_dept_desc", length = 256)
    private String bizDeptDesc;

    @Column(name = "biz_dept_id", length = 64)
    private String bizDeptId;

    @Column(name = "biz_dept_name", length = 200)
    private String bizDeptName;

    @Column(name = "biz_dept_type", length = 30)
    private String bizDeptType;

    @Column(name = "partition_code", length = 256)
    private String partitionCode;

    @Column(name = "short_name", length = 200)
    private String shortName;

    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Column(name = "organization_id", length = 64)
    private String organizationId;

    @Override
    public String getDomainCacheKey() {
        return getDepartmentId();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getA4BizDeptId() {
        return a4BizDeptId;
    }

    public void setA4BizDeptId(String a4BizDeptId) {
        this.a4BizDeptId = a4BizDeptId;
    }

    public String getBizDeptCode() {
        return bizDeptCode;
    }

    public void setBizDeptCode(String bizDeptCode) {
        this.bizDeptCode = bizDeptCode;
    }

    public String getBizDeptDesc() {
        return bizDeptDesc;
    }

    public void setBizDeptDesc(String bizDeptDesc) {
        this.bizDeptDesc = bizDeptDesc;
    }

    public String getBizDeptId() {
        return bizDeptId;
    }

    public void setBizDeptId(String bizDeptId) {
        this.bizDeptId = bizDeptId;
    }

    public String getBizDeptName() {
        return bizDeptName;
    }

    public void setBizDeptName(String bizDeptName) {
        this.bizDeptName = bizDeptName;
    }

    public String getBizDeptType() {
        return bizDeptType;
    }

    public void setBizDeptType(String bizDeptType) {
        this.bizDeptType = bizDeptType;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "SysDepartment{" +
                "departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", a4BizDeptId='" + a4BizDeptId + '\'' +
                ", bizDeptCode='" + bizDeptCode + '\'' +
                ", bizDeptDesc='" + bizDeptDesc + '\'' +
                ", bizDeptId='" + bizDeptId + '\'' +
                ", bizDeptName='" + bizDeptName + '\'' +
                ", bizDeptType='" + bizDeptType + '\'' +
                ", partitionCode='" + partitionCode + '\'' +
                ", shortName='" + shortName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", organizationId='" + organizationId + '\'' +
                '}';
    }
}
