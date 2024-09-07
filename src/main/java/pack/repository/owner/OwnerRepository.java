package pack.repository.owner;


import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import pack.model.owner.Owner;

@Repository
@RequiredArgsConstructor
public class OwnerRepository {

    private final SqlSessionTemplate sql;

    public boolean isEmailDuplicate(String ownerEmail) {
        int emailCount = sql.selectOne("Owner.emailDuplicates", ownerEmail);
        return emailCount > 0;
    }

    public boolean isBusinessNumDuplicate(String ownerBusinessNum) {
        int telCount = sql.selectOne("Owner.businessNumDuplicates", ownerBusinessNum);
        return telCount > 0;
    }

    public boolean isTelDuplicate(String ownerTel) {
        int telCount = sql.selectOne("Owner.telDuplicates", ownerTel);
        return telCount > 0;
    }

    public boolean isCompanyNameDuplicate(String ownerCompanyName) {
        int companyNameCount = sql.selectOne("Owner.companyNameDuplicates", ownerCompanyName);
        return companyNameCount > 0;
    }

    public void ownerRegister(Owner owner) {
        sql.insert("Owner.ownerRegister", owner);
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
