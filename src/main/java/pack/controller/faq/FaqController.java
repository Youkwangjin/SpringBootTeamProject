package pack.controller.faq;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.faq.FaqDTO;
import pack.service.faq.FaqService;

import java.util.List;

@Controller
@AllArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @GetMapping("/faq")
    public String listProcess(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        List<FaqDTO> list = faqService.listFaq();
        List<FaqDTO> result = faqService.getListData(list, sPage);

        model.addAttribute("faq", result);
        model.addAttribute("pageSu", faqService.getPageSu());
        model.addAttribute("page", sPage);

        return "faq/faq";
    }

    @GetMapping("/faqUser")
    public String listProcessUser(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        List<FaqDTO> list = faqService.listFaq();
        List<FaqDTO> result = faqService.getListData(list, sPage);

        model.addAttribute("faq", result);
        model.addAttribute("pageSu", faqService.getPageSu());
        model.addAttribute("page", sPage);

        return "faq/faq-user";
    }

    @GetMapping("/faqOwner")
    public String listProcessOwner(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        List<FaqDTO> list = faqService.listFaq();
        List<FaqDTO> result = faqService.getListData(list, sPage);

        model.addAttribute("faq", result);
        model.addAttribute("pageSu", faqService.getPageSu());
        model.addAttribute("page", sPage);

        return "faq/faq-owner";
    }

    @GetMapping("/searchFaqUser")
    public String searchUser(@RequestParam(value = "searchPage", defaultValue = "1") int searchPage,
                             @RequestParam(name = "searchName", required = false) String searchName,
                             @RequestParam(name = "searchValue", required = false) String searchValue,
                             Model model) {
        int sPage = searchPage;
        if (searchPage <= 0) sPage = 1;

        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setSearchName(searchName);
        faqDTO.setSearchValue(searchValue);

        List<FaqDTO> sList = faqService.searchFaq(faqDTO);
        List<FaqDTO> sResult = faqService.getListData(sList, sPage);

        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("faq", sResult);
        model.addAttribute("pageSu", faqService.getSearchPageSu(faqDTO));
        model.addAttribute("page", sPage);

        return "faq/faq-user-search";
    }

    @GetMapping("/searchFaqOwner")
    public String searchOwner(@RequestParam(value = "searchPage") int searchPage,
                              @RequestParam(name = "searchName", required = false) String searchName,
                              @RequestParam(name = "searchValue", required = false) String searchValue,
                              Model model) {
        int sPage = searchPage;
        if (searchPage <= 0) sPage = 1;

        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setSearchName(searchName);
        faqDTO.setSearchValue(searchValue);

        List<FaqDTO> sList = faqService.searchFaq(faqDTO);
        List<FaqDTO> sResult = faqService.getListData(sList, sPage);

        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("faq", sResult);
        model.addAttribute("pageSu", faqService.getSearchPageSu(faqDTO));
        model.addAttribute("page", sPage);

        return "faq/faq-owner-search";
    }

    @GetMapping("/searchFaqAdmin")
    public String searchAdmin(@RequestParam(value = "searchPage", defaultValue = "1") int searchPage,
                              @RequestParam(name = "searchName", required = false) String searchName,
                              @RequestParam(name = "searchValue", required = false) String searchValue,
                              Model model) {
        int sPage = searchPage;
        if (searchPage <= 0) sPage = 1;

        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setSearchName(searchName);
        faqDTO.setSearchValue(searchValue);

        List<FaqDTO> sList = faqService.searchFaq(faqDTO);
        List<FaqDTO> sResult = faqService.getListData(sList, sPage);

        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("faqAdmin", sResult);
        model.addAttribute("pageSu", faqService.getSearchPageSu(faqDTO));
        model.addAttribute("page", sPage);

        return "faq/faq-admin-search";
    }

    @GetMapping("/faqAdmin")
    public String listProcessAdmin(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        List<FaqDTO> list = faqService.listFaq();
        List<FaqDTO> result = faqService.getListData(list, sPage);

        model.addAttribute("faqAdmin", result);
        model.addAttribute("pageSu", faqService.getPageSu());
        model.addAttribute("page", sPage);

        return "faq/faq-admin";
    }

    @GetMapping("/faqInsert")
    public String faqInsert() {
        return "faq/faq-insert";
    }

    @PostMapping("/faqInsert")
    public String faqInsertSubmit(FaqDTO faqDTO) {
        boolean b = faqService.insertFaq(faqDTO);
        if (b) {
            return "redirect:/faqAdmin?page=1";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/faqUpdate")
    public String faqUpdate(FaqDTO faqDTO) {
        boolean b = faqService.updateFaq(faqDTO);
        if (b) {
            return "redirect:/faqAdmin?page=1";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/faqDelete")
    public String faqDelete(@RequestParam("faq_no") String faq_no) {
        boolean b = faqService.deleteFaq(faq_no);
        if (b) {
            return "redirect:/faqAdmin?page=1";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/faqDetail")
    public String faqDetail(@RequestParam("faq_no") int faq_no, Model model) {
        FaqDTO detail = faqService.detailFaq(faq_no);
        model.addAttribute("detail", detail);
        return "faq/faq-detail";
    }
}