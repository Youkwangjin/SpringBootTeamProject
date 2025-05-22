package com.acorn.api.service.admin;

import com.acorn.api.dto.admin.ContainerManagementRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AdminContainerService {
    void processCancelReview(ContainerManagementRequestDTO requestData);
}