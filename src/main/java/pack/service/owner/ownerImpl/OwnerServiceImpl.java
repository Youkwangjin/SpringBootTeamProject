package pack.service.owner.ownerImpl;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.owner.OwnerDAO;
import pack.dto.owner.OwnerDTO;
import pack.service.owner.OwnerService;

@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerDAO ownerDAO;

    private boolean isEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean joinOwnerData(OwnerDTO ownerDto) {
        boolean b;

        b = isEmpty(ownerDto.getBusiness_num()) &&
                isEmpty(ownerDto.getOwner_pwd()) &&
                isEmpty(ownerDto.getOwner_name()) &&
                isEmpty(ownerDto.getOwner_tel()) &&
                isEmpty(ownerDto.getEmail()) &&
                // 정규식 표현 적용
                ownerDto.getBusiness_num().matches("^[0-9-]+$") &&
                ownerDto.getOwner_tel().matches("^[0-9-]+$") &&
                ownerDto.getOwner_pwd().equals(ownerDto.getOwner_repwd()) &&
                ownerDto.getOwner_name().matches("^[가-힣]{2,}$") &&
                ownerDto.getOwner_pwd().matches("^.{4,}$") &&
                ownerDto.getEmail().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");

        return b;
    }
    @Override
    public String registerOwner(OwnerDTO ownerDto) {
        if(joinOwnerData(ownerDto)) {
            boolean isSuccess = ownerDAO.ownerInsertData(ownerDto);
            if (isSuccess) {
                return "owner/owner-login";
            } else {
                return "owner/owner-join";
            }
        }
        return "owner/owner-join";
    }
    @Override
    public String processLogin(String business_num, String owner_pwd, HttpSession session) {
        OwnerDTO owner = ownerDAO.ownerLoginProcess(business_num, owner_pwd);
        if (owner != null) {
            session.setMaxInactiveInterval(1800);
            session.setAttribute("ownerSession", owner);
            session.setAttribute("business_num", owner.getBusiness_num());
            session.setAttribute("owner_name", owner.getOwner_name());
            return "owner/owner-mypage";
        } else {
            return "owner/owner-login";
        }
    }

    @Override
    public String updateOwnerInfo(OwnerDTO ownerDto, HttpSession session) {
        boolean isUpdated = ownerDAO.ownerDateUpdate(ownerDto);
        if (isUpdated) {
            OwnerDTO updatedOwner = ownerDAO.ownerLoginProcess(ownerDto.getBusiness_num(), ownerDto.getOwner_pwd());
            if (updatedOwner != null) {
                session.setAttribute("ownerSession", updatedOwner);
            }
            return "owner/owner-login";
        } else {
            return "owner/owner-update";
        }
    }

    @Override
    public String deleteOwnerInfo(OwnerDTO ownerDto) {
        try {
            boolean isDeleted = ownerDAO.ownerDateDelete(ownerDto);
            if (isDeleted) {
                return "owner/owner-login";
            } else {
                return "redirect:/";
            }
        } catch (Exception e) {
            return "owner/owner-error";
        }
    }

    @Override
    public boolean checkBusinessNum(String businessNum) {
        return ownerDAO.checkBusinessNum(businessNum);
    }
}
