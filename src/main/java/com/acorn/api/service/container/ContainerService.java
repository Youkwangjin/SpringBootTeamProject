package com.acorn.api.service.container;

import com.acorn.api.dto.container.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContainerService {

    List<ContainerListDTO> getContainerListData(ContainerListDTO listData);

    void containerRegister(ContainerRegisterDTO registerData);

    ContainerDetailDTO getContainerData(Integer containerId);

    void containerUpdate(ContainerUpdateDTO updateData);

    void containerDelete(@Valid ContainerDeleteDTO deleteData);
}