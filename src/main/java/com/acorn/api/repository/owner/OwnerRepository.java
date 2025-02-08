package com.acorn.api.repository.owner;

import com.acorn.api.entity.owner.Owner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OwnerRepository {

    Integer selectOwnerIdKey();

    Integer isEmailDuplicate(@Param("ownerEmail") String ownerEmail);

    Integer isBusinessNumDuplicate(@Param("ownerBusinessNum") String ownerBusinessNum);

    Integer isTelDuplicate(@Param("ownerTel") String ownerTel);

    Integer isCompanyNameDuplicate(@Param("ownerCompanyName") String ownerCompanyName);

    void ownerRegister(Owner owner);

    Owner findByOwnerBusinessNum(String ownerBusinessNum);

    Owner selectAllOwnerData(@Param("ownerId") Integer ownerId);

    void ownerUpdate(Owner owner);

    void ownerDelete(Owner owner);
}