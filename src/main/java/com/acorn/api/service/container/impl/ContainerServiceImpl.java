package com.acorn.api.service.container.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.container.ContainerStatus;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.container.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContainerServiceImpl implements ContainerService {

    @Value("${file.upload.path.container}")
    private String uploadDir;
    private final ContainerRepository containerRepository;
    private final ContainerFileRepository containerFileRepository;
    private final FileComponent fileComponent;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public List<ContainerListDTO> getContainerListData(ContainerListDTO listData) {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        if (currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }
        listData.setContainerOwnerId(currentOwnerId);
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
    public List<ContainerMapListDTO> getContainersForMap() {
        final Integer containerStatusValue = ContainerStatus.CONTAINER_STATUS_AVAILABLE.getCode();
        final Integer containerApprovalStatusValue = ContainerStatus.CONTAINER_APPROVAL_STATUS_APPROVED.getCode();

        List<Container> containerListData = containerRepository.selectContainerMapListData(containerStatusValue, containerApprovalStatusValue);

        return containerListData.stream()
                .map(containerList -> {
                    final Integer containerId = containerList.getContainerId();
                    final String containerName = containerList.getContainerName();
                    final String containerAddr = containerList.getContainerAddr();
                    final BigDecimal containerSize = containerList.getContainerSize();
                    final Integer containerPrice = containerList.getContainerPrice();
                    final String containerContents = containerList.getContainerContents();
                    final String containerContentsText = containerList.getContainerContentsText();
                    final BigDecimal containerLatitude = containerList.getContainerLatitude();
                    final BigDecimal containerLongitude = containerList.getContainerLongitude();
                    final Integer containerStatus = containerList.getContainerStatus();
                    final Integer containerApprovalStatus = containerList.getContainerApprovalStatus();
                    final LocalDateTime containerCreated = containerList.getContainerCreated();
                    final String containerCreatedText = containerCreated.format(DATE_FORMATTER);
                    final String containerStatusText = ContainerStatus
                            .fromUseCode(containerStatus)
                            .getDescription();
                    final String containerApprovalStatusText = ContainerStatus
                            .fromApprovalCode(containerApprovalStatus)
                            .getDescription();

                    return ContainerMapListDTO.builder()
                            .containerId(containerId)
                            .containerName(containerName)
                            .containerAddr(containerAddr)
                            .containerSize(containerSize)
                            .containerPrice(containerPrice)
                            .containerContents(containerContents)
                            .containerContentsText(containerContentsText)
                            .containerLatitude(containerLatitude)
                            .containerLongitude(containerLongitude)
                            .containerStatus(containerStatus)
                            .containerStatusText(containerStatusText)
                            .containerApprovalStatusText(containerApprovalStatusText)
                            .containerApprovalStatus(containerApprovalStatus)
                            .containerCreated(containerCreated)
                            .containerCreatedText(containerCreatedText)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public ContainerDetailDTO getContainerData(Integer containerId) {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        if (currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Container containerDetailData = containerRepository.selectContainerDetailData(containerId);
        if (containerDetailData == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer containerOwnerId = containerDetailData.getContainerOwnerId();
        final String containerName = containerDetailData.getContainerName();
        final String ownerName = containerDetailData.getOwner().getOwnerNm();
        final BigDecimal containerSize = containerDetailData.getContainerSize();
        final Integer containerPrice = containerDetailData.getContainerPrice();
        final String containerAddr = containerDetailData.getContainerAddr();
        final BigDecimal containerLatitude = containerDetailData.getContainerLatitude();
        final BigDecimal containerLongitude = containerDetailData.getContainerLongitude();
        final String containerContents = containerDetailData.getContainerContents();

        if (!Objects.equals(currentOwnerId, containerOwnerId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        List<ContainerFile> containerFiles = containerDetailData.getContainerFiles();
        final List<ContainerFileDTO> containerFileDTOS = containerFiles.stream()
                .map( file -> {
                    final Integer containerFileId = file.getContainerFileId();
                    final String containerOriginalFileName = file.getContainerOriginalFileName();
                    final String containerStoredFileName = file.getContainerStoredFileName();
                    final String containerFilePath = file.getContainerFilePath();
                    final String containerFileExtNm = file.getContainerFileExtNm();
                    final String containerFileSize = file.getContainerFileSize();

                    return ContainerFileDTO.builder()
                            .containerFileId(containerFileId)
                            .containerOriginalFileName(containerOriginalFileName)
                            .containerStoredFileName(containerStoredFileName)
                            .containerFilePath(containerFilePath)
                            .containerFileExtNm(containerFileExtNm)
                            .containerFileSize(containerFileSize)
                            .build();
                })
                .collect(Collectors.toList());

        return ContainerDetailDTO.builder()
                .containerId(containerId)
                .ownerId(containerOwnerId)
                .containerName(containerName)
                .ownerName(ownerName)
                .containerSize(containerSize)
                .containerPrice(containerPrice)
                .containerAddr(containerAddr)
                .containerLatitude(containerLatitude)
                .containerLongitude(containerLongitude)
                .containerContents(containerContents)
                .containerFiles(containerFileDTOS)
                .build();
    }

    @Override
    @Transactional
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
        final Integer containerStatus = ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode();
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

    @Override
    @Transactional
    public void containerUpdate(ContainerUpdateDTO updateData) {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer containerOwnerId = updateData.getContainerOwnerId();
        final Integer containerId = updateData.getContainerId();
        final String containerName = updateData.getContainerName();
        final BigDecimal containerSize = updateData.getContainerSize();
        final Integer containerPrice = updateData.getContainerPrice();
        final String containerAddr = updateData.getContainerAddr();
        final BigDecimal containerLatitude = updateData.getContainerLatitude();
        final BigDecimal containerLongitude = updateData.getContainerLongitude();
        final String containerContents = updateData.getContainerContents();
        final String containerContentsText = Jsoup.parse(containerContents).text();

        if (currentOwnerId == null || containerOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(currentOwnerId, containerOwnerId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Container exsitedContainer = containerRepository.selectContainerDetailData(containerId);
        if (exsitedContainer == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer exsitedContainerApprovalStatus = exsitedContainer.getContainerApprovalStatus();
        final Integer exsitedContainerStatus = exsitedContainer.getContainerStatus();

        if (!Objects.equals(exsitedContainerApprovalStatus, ContainerStatus.CONTAINER_APPROVAL_STATUS_PENDING.getCode())) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_APPROVAL_NOT_PENDING);
        }

        if (!Objects.equals(exsitedContainerStatus, ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode())) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_MODIFY_DELETE_NOT_AVAILABLE);
        }

        Container newContainerUpdateData = Container.builder()
                .containerId(containerId)
                .containerName(containerName)
                .containerSize(containerSize)
                .containerPrice(containerPrice)
                .containerAddr(containerAddr)
                .containerLatitude(containerLatitude)
                .containerLongitude(containerLongitude)
                .containerContents(containerContents)
                .containerContentsText(containerContentsText)
                .build();

        containerRepository.containerUpdate(newContainerUpdateData);

        List<Integer> containaerFileIds = updateData.getContainerFileIds();
        if (containaerFileIds == null) {
            containaerFileIds = new ArrayList<>();
        }

        List<ContainerFile> existingFiles = containerFileRepository.selectFilesByContainerId(containerId);

        List<Integer> deletedFileIds = new ArrayList<>();
        for (ContainerFile containerFile : existingFiles) {
            if (!containaerFileIds.contains(containerFile.getContainerFileId())) {
                deletedFileIds.add(containerFile.getContainerFileId());
            }
        }

        for (Integer containerFileId : deletedFileIds) {
            ContainerFile containerFile = containerFileRepository.selectFilesByContainerFileId(containerFileId);
            if (containerFile == null) {
                throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
            }
            final String filePath = containerFile.getContainerFilePath();
            final String storedFileName = containerFile.getContainerStoredFileName();

            fileComponent.delete(filePath, storedFileName);
            containerFileRepository.containerFileDelete(containerFileId);
        }

        if (updateData.getContainerFiles() != null && !updateData.getContainerFiles().isEmpty()) {
            for (MultipartFile multipartFile : updateData.getContainerFiles()) {
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

    @Override
    @Transactional
    public void containerDelete(ContainerDeleteDTO deleteData) {
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer containerId = deleteData.getContainerId();
        final Integer ownerId = deleteData.getOwnerId();

        if (currentOwnerId == null || ownerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (!Objects.equals(currentOwnerId, ownerId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Container exsitedContainer = containerRepository.selectContainerDetailData(containerId);
        if (exsitedContainer == null) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_NOT_FOUND);
        }

        final Integer exsitedContainerApprovalStatus = exsitedContainer.getContainerApprovalStatus();
        final Integer exsitedContainerStatus = exsitedContainer.getContainerStatus();

        if (!Objects.equals(exsitedContainerApprovalStatus, ContainerStatus.CONTAINER_APPROVAL_STATUS_PENDING.getCode())) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_APPROVAL_NOT_PENDING);
        }

        if (!Objects.equals(exsitedContainerStatus, ContainerStatus.CONTAINER_STATUS_UNAVAILABLE.getCode())) {
            throw new AcontainerException(ApiErrorCode.CONTAINER_MODIFY_DELETE_NOT_AVAILABLE);
        }

        List<ContainerFile> existingFiles = containerFileRepository.selectFilesByContainerId(containerId);
        if (existingFiles != null && !existingFiles.isEmpty()) {
            for (ContainerFile containerFile : existingFiles) {
                final Integer containerFileId = containerFile.getContainerFileId();
                final String storedFileName = containerFile.getContainerStoredFileName();
                final String filePath = containerFile.getContainerFilePath();

                fileComponent.delete(filePath, storedFileName);
                containerFileRepository.containerFileDelete(containerFileId);
            }
        }

        Container deleteContainerData = Container.builder()
                .containerId(containerId)
                .build();

        containerRepository.containerDelete(deleteContainerData);
    }
}