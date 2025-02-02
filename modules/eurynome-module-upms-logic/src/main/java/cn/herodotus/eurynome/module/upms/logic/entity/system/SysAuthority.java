/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.module.upms.logic.entity.system;

import cn.herodotus.engine.assistant.core.enums.AuthorityType;
import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import cn.herodotus.eurynome.module.upms.logic.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * <p>Description: 权限及菜单表 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/25 15:06
 */
@Entity
@Table(name = "sys_authority", indexes = {@Index(name = "sys_authority_id_idx", columnList = "authority_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_AUTHORITY)

public class SysAuthority extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "authority-uuid")
    @GenericGenerator(name = "authority-uuid", strategy = "cn.herodotus.eurynome.module.upms.logic.assistant.generator.SysAuthorityUUIDGenerator")
    @Column(name = "authority_id", length = 64)
    private String authorityId;

    @Column(name = "authority_code", length = 128)
    private String authorityCode;

    @Column(name = "authority_name", length = 1024)
    private String authorityName;

    @Column(name = "request_method", length = 20)
    private String requestMethod;

    @Column(name = "authority_type")
    @Enumerated(EnumType.ORDINAL)
    private AuthorityType authorityType = AuthorityType.API;

    @Column(name = "service_id", length = 128)
    private String serviceId;

    @Column(name = "class_name", length = 512)
    private String className;

    @Column(name = "method_name", length = 128)
    private String methodName;

    @Column(name = "url", length = 2048)
    private String url;

    @Column(name = "menu_class", length = 50)
    private String menuClass = "fa fa-caret-right";

    /**
     * 以下是权限的树形查询，经过几次尝试，以下是最优方案。
     * 如果 @ManyToOne 和 @OneToMany都设置成 FetchType.EAGER 会出现父节点找不到的问题。
     * 如果 @ManyToOne 和 @OneToMany都设置成 FetchType.LAZY 会出现Lazy失败问题。出现“could not initialize proxy - no Session”错误
     *
     * @link https://blog.csdn.net/blueheart20/article/details/52912023
     * <p>
     * 注解@JsonBackReference和@JsonManagedReference
     * 这两个标注通常配对使用，通常用在父子关系中。@JsonBackReference标注的属性在序列化（serialization，即将对象转换为json数据）时，会被忽略（即结果中的json数据不包含该属性的内容）
     * 。@JsonManagedReference标注的属性则会被序列化。在序列化时，@JsonBackReference的作用相当于@JsonIgnore，此时可以没有@JsonManagedReference。
     * 但在反序列化（deserialization，即json数据转换为对象）时，如果没有@JsonManagedReference，则不会自动注入@JsonBackReference标注的属性（被忽略的父或子）；
     * 如果有@JsonManagedReference，则会自动注入自动注入@JsonBackReference标注的属性。
     * <p>
     * 注解@JsonIgnore：直接忽略某个属性，以断开无限递归，序列化或反序列化均忽略。当然如果标注在get、set方法中，则可以分开控制，序列化对应的是get方法，反序列化对应的是set方法。
     * 在父子关系中，当反序列化时，@JsonIgnore不会自动注入被忽略的属性值（父或子），这是它跟@JsonBackReference和@JsonManagedReference最大的区别。
     */
    @Column(name = "parent_id", length = 64)
    private String parentId;


    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysAuthority that = (SysAuthority) o;

        return new EqualsBuilder()
                .append(getAuthorityId(), that.getAuthorityId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAuthorityId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("authorityId", authorityId)
                .add("authorityCode", authorityCode)
                .add("authorityName", authorityName)
                .add("requestMethod", requestMethod)
                .add("authorityType", authorityType)
                .add("serviceId", serviceId)
                .add("className", className)
                .add("methodName", methodName)
                .add("url", url)
                .add("menuClass", menuClass)
                .add("parentId", parentId)
                .toString();
    }
}
