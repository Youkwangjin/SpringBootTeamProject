package com.acorn.api.repository.container;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.container.Container;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContainerRepository {

    Integer selectContainerIdKey();

    Integer selectListCountByRequest(PaginationRequest pagination);

    List<Container> selectContainerListData(PaginationRequest pagination);

    void containerRegister(Container container);

    Container selectContainerDetailData(@Param("containerId") Integer containerId);

    void containerUpdate(Container container);
}