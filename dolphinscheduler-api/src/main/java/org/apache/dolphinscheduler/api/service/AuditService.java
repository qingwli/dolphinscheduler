/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.api.service;

import org.apache.dolphinscheduler.api.dto.AuditDto;
import org.apache.dolphinscheduler.api.utils.PageInfo;
import org.apache.dolphinscheduler.common.enums.AuditObjectType;
import org.apache.dolphinscheduler.common.enums.AuditOperationType;
import org.apache.dolphinscheduler.dao.entity.AuditLog;
import org.apache.dolphinscheduler.dao.entity.User;

import java.util.List;

/**
 * audit information service
 */
public interface AuditService {


    void addAudit(AuditLog auditLog);

    void addAudit(List<AuditLog> auditLogList, long duration);

    void addQuartzLog(int processId);

    /**
     * query audit log list
     *
     * @param loginUser         login user
     * @param resourceType      resource type
     * @param operationType     operation type
     * @param startTime         start time
     * @param endTime           end time
     * @param userName          query user name
     * @param pageNo            page number
     * @param pageSize          page size
     * @return                  audit log string
     */
    PageInfo<AuditDto> queryLogListPaging(User loginUser, AuditObjectType resourceType,
                                          AuditOperationType operationType, String startTime,
                                          String endTime, String userName,
                                          Integer pageNo, Integer pageSize);

    String getObjectNameByObjectId(Long objectId, AuditObjectType objectType);
}
