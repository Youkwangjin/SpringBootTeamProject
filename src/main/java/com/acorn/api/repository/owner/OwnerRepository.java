package com.acorn.api.repository.owner;

import com.acorn.api.entity.owner.Owner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OwnerRepository {

    Integer selectOwnerIdKey();

    Boolean isEmailDuplicate(@Param("ownerEmail") String ownerEmail);

    Boolean isBusinessNumDuplicate(@Param("ownerBusinessNum") String ownerBusinessNum);

    Boolean isTelDuplicate(@Param("ownerTel") String ownerTel);

    Boolean isCompanyNameDuplicate(@Param("ownerCompanyName") String ownerCompanyName);

    void ownerRegister(Owner owner);

    Owner findByOwnerBusinessNum(String ownerBusinessNum);

    Owner selectAllOwnerData(@Param("ownerId") Integer ownerId);

    void ownerUpdate(Owner owner);

    void ownerDelete(Owner owner);
}