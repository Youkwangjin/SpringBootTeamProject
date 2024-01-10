package pack.controller.faq;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.faq.FaqDao;
import pack.model.faq.FaqDto;

@Controller
public class FaqController {

   @Autowired 
   public FaqDao faqDao;
   
   private int tot;
   private int plist = 10;
   
   public ArrayList<FaqDto> getListData(ArrayList<FaqDto> list, int page){
      ArrayList<FaqDto> result = new ArrayList<FaqDto>();
      
      int start = (page - 1) * plist;
      int end = Math.min(start + plist, list.size());
      
      for (int i = start; i < end; i++) {
         result.add(list.get(i));
      }
      return result;
   }
   
   public int getPageSu() { // 총 페이지 수 얻기
      tot = faqDao.totalFaq();
      int pagesu = tot / plist;
      if(tot % plist > 0) pagesu += 1;
      return pagesu; 
   }
   
   public int getSearchPageSu(FaqBean bean) {
      ArrayList<FaqDto> searchtot = (ArrayList<FaqDto>)faqDao.searchFaq(bean);
      tot = searchtot.size();
      int pagesu = tot / plist;
      if(tot % plist > 0) pagesu += 1;
      return pagesu;
   }

   @GetMapping("searchfaquser")
   public String searchUser(@RequestParam(value = "searchpage", defaultValue = "1" ) int searchpage,
                        @RequestParam(name = "searchName", required = false) String searchName,
                        @RequestParam(name = "searchValue", required = false) String searchValue,
                        Model model) {
       int spage = searchpage;
       if (searchpage <= 0) spage = 1;
       FaqBean bean = new FaqBean();
       bean.setSearchName(searchName);
       bean.setSearchValue(searchValue);

       
       ArrayList<FaqDto> slist = (ArrayList<FaqDto>) faqDao.searchFaq(bean);
       ArrayList<FaqDto> sresult = getListData(slist, spage); 

       model.addAttribute("searchName", searchName);
       model.addAttribute("searchValue", searchValue);
       model.addAttribute("faq", sresult);
       model.addAttribute("pagesu", getSearchPageSu(bean));
       model.addAttribute("page", spage);
       
      return "../templates/faq/faqusersearch";
   }
   
   @GetMapping("/searchfaqowner")
   public String searchOwner(@RequestParam(value = "searchpage") int searchpage,
                            @RequestParam(name = "searchName", required = false) String searchName,
                            @RequestParam(name = "searchValue", required = false) String searchValue,
                            Model model) {
       int spage = searchpage;
       if (searchpage <= 0) spage = 1;
       FaqBean bean = new FaqBean();
       bean.setSearchName(searchName);
       bean.setSearchValue(searchValue);
       
       ArrayList<FaqDto> slist = (ArrayList<FaqDto>) faqDao.searchFaq(bean);
       ArrayList<FaqDto> sresult = getListData(slist, spage);
       
       model.addAttribute("searchName", searchName);
       model.addAttribute("searchValue", searchValue);
       model.addAttribute("faq", sresult);
       model.addAttribute("pagesu", getSearchPageSu(bean));
       model.addAttribute("page", spage);
       
       return "../templates/faq/faqownersearch";
   }
   
   @GetMapping("searchfaqadmin")
   public String searchadmin(@RequestParam(value = "searchpage", defaultValue = "1" ) int searchpage,
                        @RequestParam(name = "searchName", required = false) String searchName,
                        @RequestParam(name = "searchValue", required = false) String searchValue,
                        Model model) {
      
      int spage = searchpage;
       if (searchpage <= 0) spage = 1;
       FaqBean bean = new FaqBean();
       bean.setSearchName(searchName);
       bean.setSearchValue(searchValue);
       
       ArrayList<FaqDto> slist = (ArrayList<FaqDto>) faqDao.searchFaq(bean);
       ArrayList<FaqDto> sresult = getListData(slist, spage);
       
       model.addAttribute("searchName", searchName);
       model.addAttribute("searchValue", searchValue);
       model.addAttribute("faqadmin", sresult);
       model.addAttribute("pagesu", getSearchPageSu(bean));
       model.addAttribute("page", spage);
      return "../templates/faq/faqadminsearch";
   }
   
   @GetMapping("faq")
   public String listProcess(@RequestParam("page")int page, Model model) {
      int spage = page;
      if (page <= 0) spage = 1;
      
      ArrayList<FaqDto> list = (ArrayList<FaqDto>)faqDao.listFaq();
      ArrayList<FaqDto> result = getListData(list, spage);
      
      model.addAttribute("faq", result);
      model.addAttribute("pagesu", getPageSu());
      model.addAttribute("page", spage);
      
      return "../templates/faq/faq";
   }
   
   @GetMapping("faquser")
   public String listProcessUser(@RequestParam("page")int page, Model model) {
      int spage = page;
      if (page <= 0) spage = 1;
      
      ArrayList<FaqDto> list = (ArrayList<FaqDto>)faqDao.listFaq();
      ArrayList<FaqDto> result = getListData(list, spage);
      
      model.addAttribute("faq", result);
      model.addAttribute("pagesu", getPageSu());
      model.addAttribute("page", spage);
      
      return "../templates/faq/faquser";
   }
   
   @GetMapping("faqowner")
   public String listProcessOwner(@RequestParam("page")int page, Model model) {
      int spage = page;
      if (page <= 0) spage = 1;
      
      ArrayList<FaqDto> list = (ArrayList<FaqDto>)faqDao.listFaq();
      ArrayList<FaqDto> result = getListData(list, spage);
      
      model.addAttribute("faq", result);
      model.addAttribute("pagesu", getPageSu());
      model.addAttribute("page", spage);
      
      return "../templates/faq/faqowner";
   }
   
   @GetMapping("faqadmin")
   public String listProcessadmin(@RequestParam("page")int page, Model model) {
      int spage = page;
      if (page <= 0) spage = 1;
      
      ArrayList<FaqDto> list = (ArrayList<FaqDto>)faqDao.listFaq();
      ArrayList<FaqDto> result = getListData(list, spage);
      
      model.addAttribute("faqadmin", result);
      model.addAttribute("pagesu", getPageSu());
      model.addAttribute("page", spage);
      
      return "../templates/faq/faqadmin";
   }
   
   @GetMapping("faqinsert")
   public String faqinsert() {
      return "../templates/faq/faqinsert";
   }
   
   @PostMapping("faqinsert")
   public String faqinsertSubmit(FaqBean bean) {
      boolean b = faqDao.insertFaq(bean);
      
      if(b) {
         return "redirect:/faqadmin?page=1";
      } else {
         return "error";
      }
   }
   
   @PostMapping("faqupdate")
   public String faqupdate(FaqBean bean) {
      boolean b = faqDao.updateFaq(bean);
      
      if(b) {
         return "redirect:/faqadmin?page=1";
      } else {
         return "error";
      }
   }
   
   @PostMapping("faqdelete")
   public String faqdelete(@RequestParam("faq_no")String faq_no) {
      boolean b = faqDao.deleteFaq(faq_no);
      if(b) {
         return "redirect:/faqadmin?page=1";
      } else {
         return "error";
      }
   }
   
   @GetMapping("faqdetail")
   public String faqdetail(@RequestParam("faq_no")int faq_no, Model model) {
      FaqDto detail = faqDao.detailFaq(faq_no);
      model.addAttribute("detail", detail);
      return "../templates/faq/faqdetail";
   }
}