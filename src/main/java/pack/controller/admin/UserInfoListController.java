package pack.controller.admin;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.form.FormDTO;
import pack.dto.user.UserDTO;
import pack.service.admin.UserInfoListService;

@Controller
@AllArgsConstructor
public class UserInfoListController {

    private final UserInfoListService userInfoListService;

    @GetMapping("/userList")
    public String userList(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int sPage = Math.max(page, 1);

        ArrayList<UserDTO> sList = (ArrayList<UserDTO>) userInfoListService.getUserList();
        ArrayList<UserDTO> result = userInfoListService.getUserListData(sList, sPage);

        int user_records = userInfoListService.userCount();

        model.addAttribute("lists", result);
        model.addAttribute("pageSu", userInfoListService.getUserPageSu());
        model.addAttribute("page", sPage);
        model.addAttribute("user_records", user_records);

        return "user/user";
    }

    @PostMapping("/userSearch")
    public String userSearch(@RequestParam(value = "page", defaultValue = "1") int page, FormDTO bean, Model model) {
        int sPage = Math.max(page, 1);
        ArrayList<UserDTO> userList = (ArrayList<UserDTO>) userInfoListService.getUserSearch(bean);
        ArrayList<UserDTO> userResult = userInfoListService.getUserListData(userList, sPage);

        model.addAttribute("lists", userResult);
        model.addAttribute("pageSu", userInfoListService.getUserPageSu());
        model.addAttribute("page", sPage);
        return "user/user";
    }

    @PostMapping("/userDelete")
    public String userDelete(@RequestParam("user_id") String user_id,
                             @RequestParam(value = "page", defaultValue = "1") int page) {
        if (userInfoListService.userDelete(user_id))
            return "redirect:/userList?page=" + page;
        else
            return "redirect:/error";
    }
}