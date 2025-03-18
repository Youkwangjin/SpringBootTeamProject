package com.acorn.api.service.container.impl;

import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.dto.container.ContainerRegisterDTO;
import com.acorn.api.entity.container.Container;
import com.acorn.api.entity.container.ContainerFile;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.container.ContainerFileRepository;
import com.acorn.api.repository.container.ContainerRepository;
import com.acorn.api.service.container.ContainerService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {

    @Value("${file.upload.path.board}")
    private String uploadDir;
    private final ContainerRepository containerRepository;
    private final ContainerFileRepository containerFileRepository;
    private final FileComponent fileComponent;

    @Override
    public List<ContainerListDTO> getContainerListData(ContainerListDTO listData) {
        listData.setTotalCount(containerRepository.selectListCountByRequest(listData));
        List<Container> containerListData = containerRepository.selectContainerListData(listData);

        return containerListData.stream()
                .map(containerList -> {
                    final Integer rowNum = containerList.getRowNum();
                    final Integer containerId = containerList.getContainerId();
                    final String containerName = containerList.getContainerName();
                    final BigDecimal containerSize = containerList.getContainerSize();
                    final Integer containerStatus = containerList.getContainerStatus();
                    final Integer containerApprovalStatus = containerList.getContainerApprovalStatus();
                    final LocalDateTime containerCreated = containerList.getContainerCreated();

                    return ContainerListDTO.builder()
                            .rowNum(rowNum)
                            .containerId(containerId)
                            .containerName(containerName)
                            .containerSize(containerSize)
                            .containerStatus(containerStatus)
                            .containerApprovalStatus(containerApprovalStatus)
                            .containerCreated(containerCreated)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void containerRegister(ContainerRegisterDTO registerData) {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer containerOwnerId = registerData.getContainerOwnerId();
        final Integer containerId = containerRepository.selectContainerIdKey();
        final String containerName = registerData.getContainerName();
        final String containerAddr = registerData.getContainerAddr();
        final BigDecimal containerLatitude = registerData.getContainerLatitude();
        final BigDecimal containerLongitude = registerData.getContainerLongitude();
        final String containerContents = registerData.getContainerContents();
        final BigDecimal containerSize = registerData.getContainerSize();
        final Integer containerPrice = registerData.getContainerPrice();
        final Integer containerStatus = ContainerStatus.CONTAINER_STATUS_PENDING.getCode();
        final Integer containerApprovalStatus = ContainerStatus.CONTAINER_APPROVAL_STATUS_PENDING.getCode();
        final List<MultipartFile> containerFiles = registerData.getContainerFiles();

        if (currentOwnerId == null && containerOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(currentOwnerId, containerOwnerId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Container newContainerRegisterData = Container.builder()
                .containerId(containerId)
                .containerName(containerName)
                .containerSize(containerSize)
                .containerPrice(containerPrice)
                .containerAddr(containerAddr)
                .containerLatitude(containerLatitude)
                .containerLongitude(containerLongitude)
                .containerContents(containerContents)
                .containerContentsText(Jsoup.parse(containerContents).text())
                .containerStatus(containerStatus)
                .containerApprovalStatus(containerApprovalStatus)
                .containerOwnerId(containerOwnerId)
                .build();
        containerRepository.containerRegister(newContainerRegisterData);

        if (containerFiles != null && !containerFiles.isEmpty()) {
            for (MultipartFile multipartFile : containerFiles) {
                final Integer containerFileId = containerFileRepository.selectContainerFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", containerId, containerFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                ContainerFile newContainerFile = ContainerFile.builder()
                        .containerFileId(containerFileId)
                        .containerOriginalFileName(originalFileName)
                        .containerStoredFileName(storedFileName)
                        .containerFilePath(filePath)
                        .containerFileExtNm(fileExtNm)
                        .containerFileSize(fileSize)
                        .containerId(containerId)
                        .build();
                containerFileRepository.containerFileSave(newContainerFile);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }
}