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

package cn.herodotus.eurynome.bpmn.logic.processor;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.JacksonUtils;
import cn.herodotus.eurynome.bpmn.logic.domain.base.BaseEntity;
import cn.herodotus.eurynome.bpmn.logic.domain.debezium.Message;
import cn.herodotus.eurynome.bpmn.logic.domain.enums.DebeziumEvent;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: CRUD 消息抽象处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 15:41
 */
public abstract class AbstractProcessor<T extends BaseEntity> {

    private static final Logger log = LoggerFactory.getLogger(AbstractProcessor.class);

    protected boolean execute(String body, Class<T> clazz) {
        Message<T> response = this.convert(body, clazz);
        if (ObjectUtils.isNotEmpty(response)) {
            DebeziumEvent event = this.parseEvent(response);
            if (ObjectUtils.isNotEmpty(event)) {
                this.operate(event, response);

                log.info("[Herodotus] |- Sync data base on Debezium for  [{}] succeed!", event.name());
                return true;
            }
        }

        log.info("[Herodotus] |- Sync data base on Debezium for [{}] failed!", body);
        return false;
    }

    private Message<T> convert(String body, Class<T> clazz) {
        if (StringUtils.isNotBlank(body)) {

            TypeFactory typeFactory = JacksonUtils.getTypeFactory();
            JavaType javaType = typeFactory.constructParametricType(Message.class, clazz);
            Message<T> response = JacksonUtils.toObject(body, javaType);

            if (ObjectUtils.isNotEmpty(response)) {
                log.debug("[Herodotus] |- Convert Object is : [{}]", response);
                return response;
            }
        }
        log.error("[Herodotus] |- JSON parse the string body error!");
        return null;
    }

    private DebeziumEvent parseEvent(Message<T> response) {
        String action = response.getPayload().getOperation();
        DebeziumEvent event = DebeziumEvent.getDebeziumEvent(action);
        if (ObjectUtils.isNotEmpty(event)) {
            log.debug("[Herodotus] |- The  Debezium event is : [{}]", event.name());
            return event;
        }

        return null;
    }

    public abstract void delete(T entity);

    public abstract T saveOrUpdate(T entity);

    private void operate(DebeziumEvent event, Message<T> message) {
        switch (event) {
            case DELETE:
                delete(message.getPayload().getBefore());
                break;
            default:
                saveOrUpdate(message.getPayload().getAfter());
                break;
        }
    }
}
