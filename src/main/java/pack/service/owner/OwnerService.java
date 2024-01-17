package pack.service.owner;


import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.owner.OwnerDAO;
import pack.dto.owner.OwnerDTO;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;

    public String registerOwner(OwnerDTO ownerDto) {
        boolean isRegistered = ownerDAO.ownerInsertData(ownerDto);
        if (isRegistered) {
            return "owner/owner-login";
        } else {
            return "owner/owner-join";
        }
    }

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

    public String updateOwnerInfo(OwnerDTO ownerDto, HttpSession session) {
        boolean isUpdated = ownerDAO.ownerUpdate(ownerDto);
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


    public String deleteOwnerInfo(OwnerDTO ownerDto) {
        try {
            boolean isDeleted = ownerDAO.ownerDelete(ownerDto);
            if (isDeleted) {
                return "owner/owner-login";
            } else {
                return "redirect:/";
            }
        } catch (Exception e) {
            return "owner/owner-error";
        }
    }
}
