package pack.dao.owner;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import pack.dto.owner.OwnerDTO;
import pack.mapper.owner.OwnerMapper;


@Repository
@AllArgsConstructor
public class OwnerDAO {


    private final OwnerMapper ownerMapper;

    private static final Logger logger = LoggerFactory.getLogger(OwnerDAO.class);

    public boolean ownerInsertData(OwnerDTO ownerDto) {
        try {
            int ownerInsertRe = ownerMapper.ownerInsertData(ownerDto);
            return ownerInsertRe > 0;
        } catch (Exception e) {
            logger.error("공급자 회원 가입 에러", e);
            return false;
        }
    }

    public OwnerDTO ownerLoginProcess(String business_num, String owner_pwd) {
        return ownerMapper.ownerLoginProcess(business_num, owner_pwd);
    }

    public boolean ownerDateUpdate(OwnerDTO ownerDto) {
        try {
            int ownerDateUpdateRe = ownerMapper.ownerUpdate(ownerDto);
            if (ownerDateUpdateRe > 0) {
                logger.info("공급자 회원수정 성공! business_num: {}", ownerDto.getBusiness_num());
                return true;
            } else {
                logger.info("공급자 데이터 없음! business_num: {}", ownerDto.getBusiness_num());
                return false;
            }
        } catch (Exception e) {
            logger.info("공급자 회원수정 실패! business_num: {}", ownerDto.getBusiness_num());
            return false;
        }
    }

    public boolean ownerDateDelete(OwnerDTO ownerDto) {
        try {
            int ownerDateDeleteRe = ownerMapper.ownerDelete(ownerDto);
            if (ownerDateDeleteRe > 0) {
                logger.info("공급자 회원탈퇴 성공! business_num: {}", ownerDto.getBusiness_num());
                return true;
            } else {
                logger.info("공급자 데이터 없음! business_num: {}", ownerDto.getBusiness_num());
                return false;
            }
        } catch (Exception e) {
            logger.info("공급자 회원탈퇴 실패! business_num: {}", ownerDto.getBusiness_num());
            return false;
        }
    }

    public boolean checkBusinessNum(String businessNum) {
        try {
            int count = ownerMapper.checkBusinessNum(businessNum);
            return count > 0;
        } catch (Exception e) {
            logger.error("사업자 번호 중복 확인 에러", e);
            return false;
        }
    }
}
