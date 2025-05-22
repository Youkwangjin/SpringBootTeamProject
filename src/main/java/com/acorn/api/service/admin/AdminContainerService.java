package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.AdminContainerDetailResponseDTO;
import com.acorn.api.dto.admin.ContainerManagementRequestDTO;
import com.acorn.api.dto.container.ContainerListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminContainerService {

    List<ContainerListDTO> getContainerList(ContainerListDTO listData);

    AdminContainerDetailResponseDTO getContainerData(Integer containerId);

    void processReviewRequest(ContainerManagementRequestDTO requestData);

    void processApprovalRequest(ContainerManagementRequestDTO requestData);

    void processRejectRequest(ContainerManagementRequestDTO requestData);

    void processCancelReview(ContainerManagementRequestDTO requestData);

    void processCancelApproval(ContainerManagementRequestDTO requestData);

    void processCancelReject(ContainerManagementRequestDTO requestData);
}