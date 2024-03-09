package pack.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ResponseBody;
import pack.dto.owner.OwnerDTO;
import pack.service.owner.OwnerService;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/owner/join")
    public String ownerJoinPage(HttpSession session) {
        if (session.getAttribute("ownerSession") != null) {
            return "redirect:/owner/mypage";
        }
        return "owner/owner-join";
    }

    @GetMapping("/owner/login")
    public String ownerLoginPage(HttpSession session) {
        if (session.getAttribute("ownerSession") != null) {
            return "redirect:/owner/mypage";
        }
        return "owner/owner-login";
    }

    @GetMapping("/owner/update")
    public String ownerUpdatePage(Model model, HttpSession session) {
        OwnerDTO owner = (OwnerDTO) session.getAttribute("ownerSession");
        model.addAttribute("ownerSession", owner);
        if (owner != null) {
            return "owner/owner-update";
        } else {
            return "owner/owner-login";
        }
    }

    @GetMapping("/owner/delete")
    public String ownerDeletePage(Model model, HttpSession session) {
        OwnerDTO owner = (OwnerDTO) session.getAttribute("ownerSession");
        model.addAttribute("ownerSession", owner);
        if (owner != null) {
            return "owner/owner-delete";
        } else {
            return "owner/owner-login";
        }
    }

    @GetMapping("/owner/logout")
    public String ownerLogout(HttpSession session) {
        session.removeAttribute("ownerSession");
        return "redirect:/";
    }

    @GetMapping("/owner/mypage")
    public String ownerSession(HttpSession session) {
        OwnerDTO ownerSession = (OwnerDTO) session.getAttribute("ownerSession");
        if (ownerSession != null) {
            return "owner/owner-mypage";
        } else {
            return "owner/owner-login";
        }
    }

    @PostMapping("/owner/join")
    public String ownerJoin(OwnerDTO ownerDto) {
        return ownerService.registerOwner(ownerDto);
    }

    @PostMapping("/owner/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> ownerLogin(@RequestParam("business_num") String businessNum,
                                                                @RequestParam("owner_pwd") String ownerPwd,
                                                                HttpSession session) {
        Map<String, Object> result = ownerService.processLogin(businessNum, ownerPwd, session);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/owner/update")
    public String ownerUpdate(OwnerDTO ownerDto, HttpSession session) {
        return ownerService.updateOwnerInfo(ownerDto, session);
    }

    @PostMapping("/owner/delete")
    public String ownerDelete(OwnerDTO ownerDto, HttpSession session) {
        return ownerService.deleteOwnerInfo(ownerDto, session);
    }

    @PostMapping("/check/businessNum")
    @ResponseBody
    public Map<String, Object> checkBusinessNum(@RequestParam("business_num") String businessNum) {
        boolean isDuplicate = ownerService.checkBusinessNum(businessNum);
        Map<String, Object> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }
}
