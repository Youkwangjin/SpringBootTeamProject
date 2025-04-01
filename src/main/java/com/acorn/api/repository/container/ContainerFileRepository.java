package com.acorn.api.repository.container;

import com.acorn.api.entity.container.ContainerFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContainerFileRepository {

    Integer selectContainerFileIdKey();

    void containerFileSave(ContainerFile containerFile);

    List<ContainerFile> selectFilesByContainerId(@Param("containerId") Integer containerId);

    ContainerFile selectFilesByContainerFileId(@Param("containerFileId") Integer containerFileId);

    void containerFileDelete(@Param("containerFileId") Integer containerFileId);
}