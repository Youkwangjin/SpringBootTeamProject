package com.acorn.api.service.container;

import com.acorn.api.dto.container.ContainerListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContainerService {

    List<ContainerListDTO> getContainerListData(ContainerListDTO listData);
}
