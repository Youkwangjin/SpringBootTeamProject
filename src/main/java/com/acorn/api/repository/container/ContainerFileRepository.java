package com.acorn.api.repository.container;

import com.acorn.api.entity.container.ContainerFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContainerFileRepository {

    Integer selectContainerFileIdKey();

    void containerFileSave(ContainerFile containerFile);
}