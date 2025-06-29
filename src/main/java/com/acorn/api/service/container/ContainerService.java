package com.acorn.api.service.container;

import com.acorn.api.dto.container.request.*;
import com.acorn.api.dto.container.response.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContainerService {

    List<ContainerListResDTO> getContainerListData(ContainerListReqDTO listData);

    List<ContainerReservationListResDTO> getContainerReservationListData(ContainerReservationListReqDTO listData);

    List<ContainerMapListResDTO> getContainersForMap();

    void containerRegister(ContainerRegisterReqDTO registerData);

    ContainerDetailResDTO getContainerData(Integer containerId);

    void containerUpdate(ContainerUpdateReqDTO updateData);

    void containerDelete(@Valid ContainerDeleteReqDTO deleteData);

    ContainerFileDownloadResDTO containerFileDownload(Integer containerId, Integer containerFileId);
}