package com.acorn.api.repository.container;

import com.acorn.api.dto.container.ContainerListDTO;
import com.acorn.api.entity.container.Container;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContainerRepository {

    Integer selectListCountByRequest(ContainerListDTO listData);

    List<Container> selectContainerListData(ContainerListDTO listData);
}