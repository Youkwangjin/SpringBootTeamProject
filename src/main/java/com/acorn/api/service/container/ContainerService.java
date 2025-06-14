package com.acorn.api.service.container;

import com.acorn.api.dto.container.request.ContainerDeleteReqDTO;
import com.acorn.api.dto.container.request.ContainerListReqDTO;
import com.acorn.api.dto.container.request.ContainerRegisterReqDTO;
import com.acorn.api.dto.container.request.ContainerUpdateReqDTO;
import com.acorn.api.dto.container.response.ContainerDetailResDTO;
import com.acorn.api.dto.container.response.ContainerListResDTO;
import com.acorn.api.dto.container.response.ContainerMapListResDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContainerService {

    List<ContainerListResDTO> getContainerListData(ContainerListReqDTO listData);

    List<ContainerMapListResDTO> getContainersForMap();

    void containerRegister(ContainerRegisterReqDTO registerData);

    ContainerDetailResDTO getContainerData(Integer containerId);

    void containerUpdate(ContainerUpdateReqDTO updateData);

    void containerDelete(@Valid ContainerDeleteReqDTO deleteData);
}