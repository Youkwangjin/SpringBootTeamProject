package com.acorn.api.service.admin.impl;

import com.acorn.api.dto.admin.AdminOwnerListDTO;
import com.acorn.api.entity.owner.Owner;
import com.acorn.api.repository.owner.OwnerRepository;
import com.acorn.api.service.admin.AdminOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOwnerServiceImpl implements AdminOwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public List<AdminOwnerListDTO> getOwnerList(AdminOwnerListDTO listData) {
        listData.setTotalCount(ownerRepository.selectAdminOwnerListCountByRequest(listData));
        List<Owner> ownerListData = ownerRepository.selectAdminOwnerListData(listData);
        return ownerListData.stream()
                .map(ownerList -> {
                    final Integer rowNum = ownerList.getRowNum();
                    final Integer ownerId = ownerList.getOwnerId();
                    final String ownerBusinessNum = ownerList.getOwnerBusinessNum();
                    final String ownerNm = ownerList.getOwnerNm();
                    final String ownerCompanyName = ownerList.getOwnerCompanyName();
                    final LocalDateTime ownerCreated = ownerList.getOwnerCreated();

                    return AdminOwnerListDTO.builder()
                            .rowNum(rowNum)
                            .ownerId(ownerId)
                            .ownerBusinessNum(ownerBusinessNum)
                            .ownerNm(ownerNm)
                            .ownerCompanyName(ownerCompanyName)
                            .ownerCreated(ownerCreated)
                            .build();

                })
                .collect(Collectors.toList());
    }
}