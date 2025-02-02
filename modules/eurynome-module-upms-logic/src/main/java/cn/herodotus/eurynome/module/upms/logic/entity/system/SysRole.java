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

import cn.herodotus.engine.data.core.entity.BaseSysEntity;
import cn.herodotus.eurynome.module.upms.logic.constants.UpmsConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: SysRole </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/12 22:58
 */
@Entity
@Table(name = "sys_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"role_code"})},
        indexes = {@Index(name = "sys_role_rid_idx", columnList = "role_id"), @Index(name = "sys_role_rcd_idx", columnList = "role_code")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_ROLE)
public class SysRole extends BaseSysEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "role_id", length = 64)
    private String roleId;

    @Column(name = "role_code", length = 128, unique = true)
    private String roleCode;

    @Column(name = "role_name", length = 128)
    private String roleName;

    /**
     * 用户 - 角色关系定义:
     * (1) 加上fetch=FetchType.LAZY  或 @Fetch(FetchMode.SELECT), 输出结果与上面相同，说明默认设置是fetch=FetchType.LAZY 和 @Fetch(FetchMode.SELECT) 下面四种配置等效，都是N+1条sql的懒加载
     * (2) 加上fetch=FetchType.Eager 和 @Fetch(FetchMode.SELECT), 同样是N+1条sql，不过和上面情况不同的是，N条sql会在criteria.list()时执行
     * (3) 加上@Fetch(FetchMode.JOIN), 那么Hibernate将强行设置为fetch=FetchType.EAGER, 用户设置fetch=FetchType.LAZY将不会生效
     * 从输出可看出，在执行criteria.list()时通过一条sql 获取了所有的City和Hotel。
     * 使用@Fetch(FetchMode.JOIN)需要注意的是：它在Join查询时是Full Join, 所以会有重复City出现
     * (4) 加上@Fetch(FetchMode.SUBSELECT), 那么Hibernate将强行设置为fetch=FetchType.EAGER, 用户设置fetch=FetchType.LAZY将不会生效 从输出可看出，在执行criteria.list()时通过两条sql分别获取City和Hotel
     * <p>
     * {@link :https://www.jianshu.com/p/23bd82a7b96e}
     */

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = UpmsConstants.REGION_SYS_AUTHORITY)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "sys_role_authority",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "authority_id"})},
            indexes = {@Index(name = "sys_role_authority_rid_idx", columnList = "role_id"), @Index(name = "sys_role_authority_aid_idx", columnList = "authority_id")})
    private Set<SysAuthority> authorities = new HashSet<>();

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SysAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SysAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysRole sysRole = (SysRole) o;

        return new EqualsBuilder()
                .append(getRoleId(), sysRole.getRoleId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getRoleId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("roleId", roleId)
                .add("roleCode", roleCode)
                .add("roleName", roleName)
                .toString();
    }
}
