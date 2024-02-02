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

package org.apache.dolphinscheduler.api.controller;

import static org.apache.dolphinscheduler.api.enums.Status.QUERY_AUDIT_LOG_LIST_PAGING;

import org.apache.dolphinscheduler.api.dto.AuditDto;
import org.apache.dolphinscheduler.api.dto.auditLog.AuditObjectTypeDto;
import org.apache.dolphinscheduler.api.dto.auditLog.AuditOperationTypeDto;
import org.apache.dolphinscheduler.api.exceptions.ApiException;
import org.apache.dolphinscheduler.api.service.AuditService;
import org.apache.dolphinscheduler.api.utils.PageInfo;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.constants.Constants;
import org.apache.dolphinscheduler.dao.entity.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "AUDIT_LOG_TAG")
@RestController
@RequestMapping("projects/audit")
public class AuditLogController extends BaseController {

    @Autowired
    AuditService auditService;

    /**
     * query audit log list paging
     *
     * @param loginUser         login user
     * @param pageNo            page number
     * @param objectTypeCodes       object type codes
     * @param operationTypeCodes    operation type codes
     * @param startDate         start time
     * @param endDate           end time
     * @param userName          user name
     * @param pageSize          page size
     * @return      audit log content
     */
    @Operation(summary = "queryAuditLogListPaging", description = "QUERY_AUDIT_LOG")
    @Parameters({
            @Parameter(name = "startDate", description = "START_DATE", schema = @Schema(implementation = String.class)),
            @Parameter(name = "endDate", description = "END_DATE", schema = @Schema(implementation = String.class)),
            @Parameter(name = "objectTypeCodes", description = "OBJECT_TYPE_CODES", schema = @Schema(implementation = String.class)),
            @Parameter(name = "operationTypeCodes", description = "OPERATION_TYPE_CODES", schema = @Schema(implementation = String.class)),
            @Parameter(name = "userName", description = "USER_NAME", schema = @Schema(implementation = String.class)),
            @Parameter(name = "objectName", description = "OBJECT_NAME", schema = @Schema(implementation = String.class)),
            @Parameter(name = "pageNo", description = "PAGE_NO", required = true, schema = @Schema(implementation = int.class, example = "1")),
            @Parameter(name = "pageSize", description = "PAGE_SIZE", required = true, schema = @Schema(implementation = int.class, example = "20"))
    })
    @GetMapping(value = "/audit-log-list")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(QUERY_AUDIT_LOG_LIST_PAGING)
    public Result<PageInfo<AuditDto>> queryAuditLogListPaging(@Parameter(hidden = true) @RequestAttribute(value = Constants.SESSION_USER) User loginUser,
                                                              @RequestParam("pageNo") Integer pageNo,
                                                              @RequestParam("pageSize") Integer pageSize,
                                                              @RequestParam(value = "objectTypeCodes", required = false) String objectTypeCodes,
                                                              @RequestParam(value = "operationTypeCodes", required = false) String operationTypeCodes,
                                                              @RequestParam(value = "startDate", required = false) String startDate,
                                                              @RequestParam(value = "endDate", required = false) String endDate,
                                                              @RequestParam(value = "userName", required = false) String userName,
                                                              @RequestParam(value = "objectName", required = false) String objectName) {
        checkPageParams(pageNo, pageSize);
        PageInfo<AuditDto> auditDtoPageInfo = auditService.queryLogListPaging(
                loginUser,
                objectTypeCodes,
                operationTypeCodes,
                startDate,
                endDate,
                userName,
                objectName,
                pageNo,
                pageSize);
        return Result.success(auditDtoPageInfo);
    }

    /**
     * query audit log operation type list
     *
     * @return object type list
     */
    @Operation(summary = "queryAuditOperationTypeList", description = "QUERY_AUDIT_OPERATION_TYPE_LIST")
    @GetMapping(value = "/audit-log-operation-type")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(QUERY_AUDIT_LOG_LIST_PAGING)
    public Result<List<AuditOperationTypeDto>> queryAuditOperationTypeList() {
        return Result.success(AuditOperationTypeDto.getOperationTypeDtoList());
    }

    /**
     * query audit log object type list
     *
     * @return object type list
     */
    @Operation(summary = "queryAuditObjectTypeList", description = "QUERY_AUDIT_OBJECT_TYPE_LIST")
    @GetMapping(value = "/audit-log-object-type")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(QUERY_AUDIT_LOG_LIST_PAGING)
    public Result<List<AuditObjectTypeDto>> queryAuditObjectTypeList() {
        return Result.success(AuditObjectTypeDto.getObjectTypeDtoList());
    }
}
