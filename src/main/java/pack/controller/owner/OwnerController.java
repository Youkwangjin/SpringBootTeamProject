package pack.controller.owner;


import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("ownerJoinGo")
    public String ownerChoice(HttpSession session) {
        if (session.getAttribute("ownerSession") != null) {
            return "redirect:/ownerSessionKeep";
        }
        return "owner/owner-join";
    }

    @GetMapping("/ownerLoginGo")
    public String ownerLoginGo(HttpSession session) {
        if (session.getAttribute("ownerSession") != null) {
            return "redirect:/ownerSessionKeep";
        }
        return "owner/owner-login";
    }

    @GetMapping("/ownerUpdate")
    public String ownerUpdatePage(Model model, HttpSession session) {
        OwnerDTO owner = (OwnerDTO) session.getAttribute("ownerSession");
        model.addAttribute("ownerSession", owner);

        return "owner/owner-update";
    }

    @GetMapping("/ownerDelete")
    public String ownerDeletePage(Model model, HttpSession session) {
        OwnerDTO owner = (OwnerDTO) session.getAttribute("ownerSession");
        model.addAttribute("ownerSession", owner);
        return "owner/owner-delete";
    }

    @GetMapping("/ownerLogoutGo")
    public String ownerLogoutProcess(HttpSession session) {
        session.removeAttribute("ownerSession");
        return "redirect:/";
    }

    @GetMapping("/ownerSessionKeep")
    public String ownerSessionKeep(HttpSession session) {
        OwnerDTO ownerSession = (OwnerDTO) session.getAttribute("ownerSession");
        if (ownerSession != null) {
            return "owner/owner-mypage";
        } else {
            return "/index/index";
        }
    }

    @PostMapping("ownerJoinClick")
    public String ownerLoginOK(OwnerDTO ownerDto) {
        return ownerService.registerOwner(ownerDto);
    }

    @PostMapping("ownerLogSuccess")
    public String processLoginForm(@RequestParam("business_num") String business_num,
                                   @RequestParam("owner_pwd") String owner_pwd,
                                   HttpSession session) {
        return ownerService.processLogin(business_num, owner_pwd, session);
    }


    @PostMapping("ownerInfoUpdate")
    public String ownerInfoUpdate(OwnerDTO ownerDto, HttpSession session) {
        return ownerService.updateOwnerInfo(ownerDto, session);
    }

    @PostMapping("/ownerInfoDelete")
    public String ownerInfoDelete(OwnerDTO ownerDto) {
        return ownerService.deleteOwnerInfo(ownerDto);
    }

    @PostMapping("/checkBusinessNum")
    @ResponseBody
    public Map<String, Object> checkBusinessNum(@RequestParam("business_num") String businessNum) {
        boolean isDuplicate = ownerService.checkBusinessNum(businessNum);
        Map<String, Object> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }
}
