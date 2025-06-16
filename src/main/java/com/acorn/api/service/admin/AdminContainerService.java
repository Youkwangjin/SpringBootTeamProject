package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.response.AdminContainerDetailResDTO;
import com.acorn.api.dto.admin.request.ContainerManagementReqDTO;
import com.acorn.api.dto.container.request.ContainerListReqDTO;
import com.acorn.api.dto.container.response.ContainerListResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminContainerService {

    List<ContainerListResDTO> getContainerList(ContainerListReqDTO listData);

    AdminContainerDetailResDTO getContainerData(Integer containerId);

    void processReviewRequest(ContainerManagementReqDTO requestData);

    void processApprovalRequest(ContainerManagementReqDTO requestData);

    void processRejectRequest(ContainerManagementReqDTO requestData);

    void processCancelReview(ContainerManagementReqDTO requestData);

    void processCancelApproval(ContainerManagementReqDTO requestData);

    void processCancelReject(ContainerManagementReqDTO requestData);
}