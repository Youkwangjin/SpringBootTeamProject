package pack.service.owner;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pack.dto.owner.OwnerDTO;

@Service
public interface OwnerService {
    String registerOwner(OwnerDTO ownerDto);
    String processLogin(String business_num, String owner_pwd, HttpSession session);
    String updateOwnerInfo(OwnerDTO ownerDto, HttpSession session);
    String deleteOwnerInfo(OwnerDTO ownerDto);
    boolean checkBusinessNum(String businessNum);
}

