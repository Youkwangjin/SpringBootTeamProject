package com.acorn.api.repository.owner;

import com.acorn.api.model.owner.Owner;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OwnerRepository {

    private final SqlSessionTemplate sql;

    public Integer selectOwnerIdKey() {
        return sql.selectOne("Owner.selectOwnerIdKey");
    }

    public int isEmailDuplicate(String ownerEmail) {
        return sql.selectOne("Owner.emailDuplicates", ownerEmail);
    }

    public int isBusinessNumDuplicate(String ownerBusinessNum) {
        return sql.selectOne("Owner.businessNumDuplicates", ownerBusinessNum);
    }

    public int isTelDuplicate(String ownerTel) {
        return sql.selectOne("Owner.telDuplicates", ownerTel);
    }

    public int isCompanyNameDuplicate(String ownerCompanyName) {
        return sql.selectOne("Owner.companyNameDuplicates", ownerCompanyName);
    }

    public void ownerRegister(Owner newRegisterDataOwner) {
        sql.insert("Owner.ownerRegister", newRegisterDataOwner);
    }

    public Owner findByOwnerBusinessNum(String ownerBusinessNum) {
        return  sql.selectOne("Owner.findByOwnerBusinessNum", ownerBusinessNum);
    }

    public Owner selectAllOwnerData(String ownerUUId) {
        return sql.selectOne("Owner.selectAllOwnerData", ownerUUId);
    }

    public void ownerUpdate(Owner updateOwner) {
        sql.update("Owner.ownerUpdate", updateOwner);
    }

    public void ownerDelete(Owner owner) {
        sql.delete("Owner.ownerDelete", owner);
    }
}