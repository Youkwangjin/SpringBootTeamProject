package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.AdminContainerDetailResponseDTO;
import com.acorn.api.dto.admin.AdminResponseDTO;
import com.acorn.api.dto.admin.ContainerManagementRequestDTO;
import com.acorn.api.dto.container.ContainerListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    AdminResponseDTO getAdminData();

    List<ContainerListDTO> getContainerList(ContainerListDTO listData);

    AdminContainerDetailResponseDTO getContainerData(Integer containerId);

    void processReviewRequest(ContainerManagementRequestDTO requestData);

    void processApprovalRequest(ContainerManagementRequestDTO requestData);
}