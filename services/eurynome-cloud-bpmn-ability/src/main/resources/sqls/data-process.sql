/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-bpmn-ability
 * File Name: data-process.sql
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

-- ----------------------------
-- 系统用户导入camunda
-- ----------------------------
INSERT INTO act_id_user (id_, first_, pwd_)
SELECT su.user_id    AS id_,
       su.user_name  AS first_,
       su."password" AS pwd_
FROM "sys_user" su