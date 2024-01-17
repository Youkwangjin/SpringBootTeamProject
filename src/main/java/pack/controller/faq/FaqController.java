package pack.controller.faq;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.fqa.FaqDAO;
import pack.dto.faq.FaqDTO;

@Controller
public class FaqController {
    @Autowired
    public FaqDAO faqDao;
    private int tot;
    private final int pList = 10;

    public ArrayList<FaqDTO> getListData(ArrayList<FaqDTO> list, int page) {
        ArrayList<FaqDTO> result = new ArrayList<FaqDTO>();

        int start = (page - 1) * pList;
        int end = Math.min(start + pList, list.size());

        for (int i = start; i < end; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public int getPageSu() {
        tot = faqDao.totalFaq();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
    }

    public int getSearchPageSu(FaqDTO faqDTO) {
        ArrayList<FaqDTO> searchTot = (ArrayList<FaqDTO>) faqDao.searchFaq(faqDTO);
        tot = searchTot.size();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
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


        ArrayList<FaqDTO> sList = (ArrayList<FaqDTO>) faqDao.searchFaq(faqDTO);
        ArrayList<FaqDTO> sResult = getListData(sList, sPage);

        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("faq", sResult);
        model.addAttribute("pageSu", getSearchPageSu(faqDTO));
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

        ArrayList<FaqDTO> sList = (ArrayList<FaqDTO>) faqDao.searchFaq(faqDTO);
        ArrayList<FaqDTO> sResult = getListData(sList, sPage);

        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("faq", sResult);
        model.addAttribute("pageSu", getSearchPageSu(faqDTO));
        model.addAttribute("page", sPage);

        return "faq/faq-owner-search";
    }

    @GetMapping("searchFaqAdmin")
    public String searchAdmin(@RequestParam(value = "searchPage", defaultValue = "1") int searchPage,
                              @RequestParam(name = "searchName", required = false) String searchName,
                              @RequestParam(name = "searchValue", required = false) String searchValue,
                              Model model) {
        int sPage = searchPage;
        if (searchPage <= 0) sPage = 1;
        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setSearchName(searchName);
        faqDTO.setSearchValue(searchValue);

        ArrayList<FaqDTO> sList = (ArrayList<FaqDTO>) faqDao.searchFaq(faqDTO);
        ArrayList<FaqDTO> sResult = getListData(sList, sPage);

        model.addAttribute("searchName", searchName);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("faqAdmin", sResult);
        model.addAttribute("pageSu", getSearchPageSu(faqDTO));
        model.addAttribute("page", sPage);
        return "faq/faq-admin-search";
    }

    @GetMapping("/faq")
    public String listProcess(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        ArrayList<FaqDTO> list = (ArrayList<FaqDTO>) faqDao.listFaq();
        ArrayList<FaqDTO> result = getListData(list, sPage);

        model.addAttribute("faq", result);
        model.addAttribute("pageSu", getPageSu());
        model.addAttribute("page", sPage);

        return "/faq/faq";
    }

    @GetMapping("/faqUser")
    public String listProcessUser(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        ArrayList<FaqDTO> list = (ArrayList<FaqDTO>) faqDao.listFaq();
        ArrayList<FaqDTO> result = getListData(list, sPage);

        model.addAttribute("faq", result);
        model.addAttribute("pageSu", getPageSu());
        model.addAttribute("page", sPage);

        return "faq/faq-user";
    }

    @GetMapping("/faqOwner")
    public String listProcessOwner(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        ArrayList<FaqDTO> list = (ArrayList<FaqDTO>) faqDao.listFaq();
        ArrayList<FaqDTO> result = getListData(list, sPage);

        model.addAttribute("faq", result);
        model.addAttribute("pageSu", getPageSu());
        model.addAttribute("page", sPage);

        return "faq/faq-owner";
    }

    @GetMapping("faqAdmin")
    public String listProcessAdmin(@RequestParam("page") int page, Model model) {
        int sPage = page;
        if (page <= 0) sPage = 1;

        ArrayList<FaqDTO> list = (ArrayList<FaqDTO>) faqDao.listFaq();
        ArrayList<FaqDTO> result = getListData(list, sPage);

        model.addAttribute("faqAdmin", result);
        model.addAttribute("pageSu", getPageSu());
        model.addAttribute("page", sPage);

        return "faq/faq-admin";
    }

    @GetMapping("/faqInsert")
    public String faqInsert() {
        return "faq/faq-insert";
    }

    @PostMapping("/faqInsert")
    public String faqInsertSubmit(FaqDTO faqDTO) {
        boolean b = faqDao.insertFaq(faqDTO);
        if (b) {
            return "redirect:/faqAdmin?page=1";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/faqUpdate")
    public String faqUpdate(FaqDTO faqDTO) {
        boolean b = faqDao.updateFaq(faqDTO);
        if (b) {
            return "redirect:/faqAdmin?page=1";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/faqDelete")
    public String faqDelete(@RequestParam("faq_no") String faq_no) {
        boolean b = faqDao.deleteFaq(faq_no);
        if (b) {
            return "redirect:/faqAdmin?page=1";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/faqDetail")
    public String faqDetail(@RequestParam("faq_no") int faq_no, Model model) {
        FaqDTO detail = faqDao.detailFaq(faq_no);
        model.addAttribute("detail", detail);
        return "faq/faq-detail";
    }
}