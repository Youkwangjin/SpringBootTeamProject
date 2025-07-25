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

    Integer selectAdminListCountByRequest(PaginationRequest pagination);

    List<Container> selectContainerListData(PaginationRequest pagination);

    List<Container> selectAdminContainerListData(PaginationRequest pagination);

    List<Container> selectContainerAllData(@Param("containerOwnerId") Integer ownerId);

    List<Container> selectContainerMapListData(@Param("containerStatus") Integer containerStatus, @Param("containerApprovalStatus") Integer containerApprovalStatus);

    Container selectContainerDetailData(@Param("containerId") Integer containerId);

    Container selectAdminContainerDetailData(@Param("containerId") Integer containerId);

    void containerRegister(Container container);

    void containerUpdate(Container container);

    void containerDelete(Container container);

    void updateContainerApproval(Container container);

    void updateContainerStatus(Container container);

    void updateContainerApprovalAndStatus(Container container);
}