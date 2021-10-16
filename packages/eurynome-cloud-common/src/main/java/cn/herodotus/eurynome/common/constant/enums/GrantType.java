/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-constant
 * File Name: GrantType.java
 * Author: gengwei.zheng
 * Date: 2021/08/07 20:07:07
 */

package cn.herodotus.eurynome.common.constant.enums;

import cn.herodotus.eurynome.common.constant.magic.SecurityConstants;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: OAuth2 认证模式枚举 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/16 16:31
 */
public enum GrantType {

    /**
     * enum
     */
    AUTHORIZATION_CODE(SecurityConstants.AUTHORIZATION_CODE, "Authorization Code 模式"),
    IMPLICIT(SecurityConstants.IMPLICIT, "Implicit 模式"),
    PASSWORD(SecurityConstants.PASSWORD, "Password 模式"),
    CLIENT_CREDENTIALS(SecurityConstants.CLIENT_CREDENTIALS, "Client Credentials 模式"),
    SOCIAL_AUTHENTICATION(SecurityConstants.SOCIAL_AUTHENTICATION, "Social Authentication 模式");

    private final String grant;
    private final String description;

    private static final Map<Integer, GrantType> indexMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (GrantType grantType : GrantType.values()) {
            indexMap.put(grantType.ordinal(), grantType);
            toJsonStruct.add(grantType.ordinal(),
                    ImmutableMap.<String, Object>builder()
                            .put("value", grantType.getGrant())
                            .put("key", grantType.name())
                            .put("text", grantType.getDescription())
                            .put("index", grantType.ordinal())
                            .build());
        }
    }

    GrantType(String grant, String description) {
        this.grant = grant;
        this.description = description;
    }

    public String getGrant() {
        return grant;
    }

    public String getDescription() {
        return description;
    }

    public static GrantType getGrant(Integer grant) {
        return indexMap.get(grant);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }
}
