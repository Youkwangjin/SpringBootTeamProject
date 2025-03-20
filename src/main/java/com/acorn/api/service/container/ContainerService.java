package com.acorn.api.service.container;

import com.acorn.api.dto.container.ContainerDetailDTO;
import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.dto.container.ContainerRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContainerService {

    List<ContainerListDTO> getContainerListData(ContainerListDTO listData);

    void containerRegister(ContainerRegisterDTO registerData);

    ContainerDetailDTO getContainerData(Integer containerId);
}
