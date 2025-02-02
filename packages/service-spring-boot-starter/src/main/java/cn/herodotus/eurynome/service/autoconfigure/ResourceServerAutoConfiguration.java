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

package cn.herodotus.eurynome.service.autoconfigure;

import cn.herodotus.engine.oauth2.core.processor.HerodotusSecurityConfigureHandler;
import cn.herodotus.engine.oauth2.core.response.HerodotusAccessDeniedHandler;
import cn.herodotus.engine.oauth2.core.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.engine.oauth2.server.resource.converter.HerodotusJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p>Description: 资源服务器通用配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/21 23:56
 */
@EnableWebSecurity
public class ResourceServerAutoConfiguration {

    private final HerodotusSecurityConfigureHandler herodotusSecurityConfigureHandler;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public ResourceServerAutoConfiguration(HerodotusSecurityConfigureHandler herodotusSecurityConfigureHandler, JwtDecoder jwtDecoder) {
        this.herodotusSecurityConfigureHandler = herodotusSecurityConfigureHandler;
        this.jwtDecoder = jwtDecoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // @formatter:off
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(herodotusSecurityConfigureHandler.getPermitAllArray()).permitAll()
                                .antMatchers(herodotusSecurityConfigureHandler.getStaticResourceArray()).permitAll()
                                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                                .anyRequest().authenticated())
                .oauth2ResourceServer(configurer ->
                        configurer
                                .jwt(jwt -> jwt.decoder(jwtDecoder).jwtAuthenticationConverter(new HerodotusJwtAuthenticationConverter()))
                                .bearerTokenResolver(new DefaultBearerTokenResolver())
                                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint()));
        // @formatter:on
        return http.build();
    }
}
