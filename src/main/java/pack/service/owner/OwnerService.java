package pack.service.owner;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pack.dto.owner.OwnerDTO;

import java.util.Map;

@Service
public interface OwnerService {
    String registerOwner(OwnerDTO ownerDto);
    Map<String, Object> processLogin(String businessNum, String ownerPwd, HttpSession session);
    String updateOwnerInfo(OwnerDTO ownerDto, HttpSession session);
    String deleteOwnerInfo(OwnerDTO ownerDto, HttpSession session);
    boolean checkBusinessNum(String businessNum);
}

