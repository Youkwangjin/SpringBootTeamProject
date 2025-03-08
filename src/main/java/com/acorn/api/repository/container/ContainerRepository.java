package com.acorn.api.repository.container;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.container.Container;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContainerRepository {

    Integer selectListCountByRequest(PaginationRequest pagination);

    List<Container> selectContainerListData(PaginationRequest pagination);
}