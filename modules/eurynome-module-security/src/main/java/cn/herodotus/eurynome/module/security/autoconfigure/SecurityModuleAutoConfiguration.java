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

package cn.herodotus.eurynome.module.security.autoconfigure;

import cn.herodotus.engine.oauth2.core.properties.SecurityProperties;
import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.eurynome.module.security.configuration.MethodSecurityMetadataConfiguration;
import cn.herodotus.eurynome.module.security.processor.HerodotusRequestMappingScanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 安全模块自动配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/21 18:02
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
        SecurityProperties.class,
})
@Import({
        MethodSecurityMetadataConfiguration.class
})
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
public class SecurityModuleAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityModuleAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- SDK [Module Security] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanManager requestMappingScanManager() {
        HerodotusRequestMappingScanManager herodotusRequestMappingScanManager = new HerodotusRequestMappingScanManager();
        log.debug("[Herodotus] |- Bean [Request Mapping Scan Manager] Auto Configure.");
        return herodotusRequestMappingScanManager;
    }
}
