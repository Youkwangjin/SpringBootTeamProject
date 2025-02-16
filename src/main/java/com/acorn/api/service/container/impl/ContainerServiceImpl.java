package com.acorn.api.service.container.impl;

import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.entity.container.Container;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.service.container.ContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {

    private final ContainerRepository containerRepository;

    @Override
    public List<ContainerListDTO> getContainerListData(ContainerListDTO listData) {
        listData.setTotalCount(containerRepository.selectListCountByRequest(listData));
        List<Container> containerListData = containerRepository.selectContainerListData(listData);

        return containerListData.stream()
                .map(containerList -> {
                    final Integer rowNum = containerList.getRowNum();
                    final Integer containerId = containerList.getContainerId();
                    final String containerName = containerList.getContainerName();
                    final Integer containerSize = containerList.getContainerSize();
                    final Integer containerStatus = containerList.getContainerStatus();
                    final LocalDateTime containerCreated = containerList.getContainerCreated();

                    return ContainerListDTO.builder()
                            .rowNum(rowNum)
                            .containerId(containerId)
                            .containerName(containerName)
                            .containerSize(containerSize)
                            .containerStatus(containerStatus)
                            .containerCreated(containerCreated)
                            .build();
                })
                .collect(Collectors.toList());
    }
}