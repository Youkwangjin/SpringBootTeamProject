package pack.dao.admin;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pack.dto.admin.AdminDTO;
import pack.mapper.admin.AdminMapper;


@Repository
@AllArgsConstructor
public class AdminDAO {

	private final AdminMapper adminMapper;
    private static final Logger logger = LoggerFactory.getLogger(AdminDAO.class);

    public AdminDTO adminLoginProcess(String admin_id, String admin_pwd) {
        return adminMapper.adminLoginProcess(admin_id, admin_pwd);
    }
    @Transactional
    public boolean containerApprove(AdminDTO adminDTO) {
        try {
            int containerApproveRe = adminMapper.containerApprove(adminDTO);
            if(containerApproveRe > 0) {
                logger.info("창고 승인 성공! cont_no: {}", adminDTO.getCont_no());
                return true;
            } else {
                logger.info("창고 승인 데이터 없음! cont_no: {}", adminDTO.getCont_no());
                return false;
            }
        } catch (Exception e) {
            logger.info("창고 승인 실패! cont_no: {}", adminDTO.getCont_no());
            return false;
        }
    }

    @Transactional
    public boolean containerDeny(AdminDTO adminDTO) {
        try{
            int containerDenyRe = adminMapper.containerDeny(adminDTO);
            if (containerDenyRe > 0) {
                logger.info("창고 거절 성공! cont_no: {}", adminDTO.getCont_no());
                return true;
            } else {
                logger.info("창고 거절 데이터 없음! cont_no: {}", adminDTO.getCont_no());
                return false;
            }
        } catch (Exception e) {
            logger.info("창고 거절 실패! cont_no: {}", adminDTO.getCont_no());
            return false;
        }
    }
}
