package pack.dao.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.dto.admin.AdminDTO;
import pack.repository.admin.ApproveMapper;
import pack.repository.admin.AdminMapper;


@Repository
@AllArgsConstructor
public class AdminDAO {

	private final AdminMapper adminMapper;

	private final ApproveMapper approveMapper;

    public AdminDTO adminLoginProcess(String admin_id, String admin_pwd) {
        return adminMapper.adminLoginProcess(admin_id, admin_pwd);
    }

    @Transactional
    public boolean approve(AdminDTO adminDTO) {
        boolean b = false;
        System.out.println("approve 메서드 시작");
        try {
            int re = approveMapper.approve(adminDTO);
            System.out.println("SQL 실행 결과: " + re);
            if (re > 0)
                b = true;
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        return b;
    }
    public boolean deny(AdminDTO adminDTO) {
        boolean b = false;
        System.out.println("deny 메서드 시작");
        try {
            int re = approveMapper.deny(adminDTO);
            System.out.println("SQL 실행 결과: " + re);
            if (re > 0)
                b = true;
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
        }
        return b;
    }
}
