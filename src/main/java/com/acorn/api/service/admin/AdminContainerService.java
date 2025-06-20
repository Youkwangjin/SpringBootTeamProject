package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.response.AdminContainerDetailResDTO;
import com.acorn.api.dto.admin.request.AdminContainerManagementReqDTO;
import com.acorn.api.dto.container.request.ContainerListReqDTO;
import com.acorn.api.dto.container.response.ContainerListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminContainerService {

    List<ContainerListResDTO> getContainerList(ContainerListReqDTO listData);

    AdminContainerDetailResDTO getContainerData(Integer containerId);

    void processReviewRequest(AdminContainerManagementReqDTO requestData);

    void processApprovalRequest(AdminContainerManagementReqDTO requestData);

    void processRejectRequest(AdminContainerManagementReqDTO requestData);

    void processCancelReview(AdminContainerManagementReqDTO requestData);

    void processCancelApproval(AdminContainerManagementReqDTO requestData);

    void processCancelReject(AdminContainerManagementReqDTO requestData);
}