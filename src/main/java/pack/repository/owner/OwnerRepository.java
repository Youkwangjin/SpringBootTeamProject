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

    public void ownerRegister(Owner owner) {
        sql.insert("Owner.ownerRegister", owner);
    }
}