package pack.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.form.FormDTO;
import pack.dto.owner.OwnerDTO;
import pack.service.admin.OwnerInfoListService;

import java.util.ArrayList;

@Controller
@AllArgsConstructor
public class OwnerInfoListController {

    private final OwnerInfoListService ownerInfoListService;

    @GetMapping("/ownerList")
    public String ownerList(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int sPage = Math.max(page, 1);

        ArrayList<OwnerDTO> ownerList = (ArrayList<OwnerDTO>) ownerInfoListService.getOwnerList();
        ArrayList<OwnerDTO> ownerResult = ownerInfoListService.getOwnerListData(ownerList, sPage);

        int owner_records = ownerInfoListService.getOwnerRecords();

        model.addAttribute("lists2", ownerResult);
        model.addAttribute("pageSu", ownerInfoListService.getOwnerPageSu());
        model.addAttribute("page", sPage);
        model.addAttribute("owner_records", owner_records);
        return "/owner/owner";
    }

    @PostMapping("/ownerSearch")
    public String ownerSearch(@RequestParam(value = "page", defaultValue = "1") int page, FormDTO bean, Model model) {
        int sPage = Math.max(page, 1);
        ArrayList<OwnerDTO> sList2 = (ArrayList<OwnerDTO>) ownerInfoListService.getOwnerSearch(bean);
        ArrayList<OwnerDTO> result = ownerInfoListService.getOwnerListData(sList2, sPage);

        model.addAttribute("lists2", result);
        model.addAttribute("pageSu", ownerInfoListService.getOwnerPageSu());
        model.addAttribute("page", sPage);

        return "/owner/owner";
    }

    @PostMapping("/ownerDelete")
    public String ownerDelete(@RequestParam("business_num") String business_num,
                              @RequestParam(value = "page", defaultValue = "1") int page) {
        if (ownerInfoListService.ownerDelete(business_num))
            return "redirect:/ownerList?page=" + page;
        else
            return "redirect:/error";
    }
}
