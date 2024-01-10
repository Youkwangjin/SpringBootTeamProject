package pack.controller.admin;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.controller.FormBean;
import pack.model.DataDao;
import pack.model.container.ContainerDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

@Controller

public class ListController {
   @Autowired
   private DataDao dataDao;
   
   private int tot;
   private int plist = 10;
   
   public ArrayList<UserDto> getuserListData(ArrayList<UserDto> list, int page){
         ArrayList<UserDto> result = new ArrayList<UserDto>();
         
         int start = (page - 1) * plist;
         int end = Math.min(start + plist, list.size());
         for (int i = start; i < end; i++) {
            result.add(list.get(i));  
         }
         return result;
    }
      
      public int getuserPageSu() {
         tot = dataDao.totalUser();
         int pagesu = tot / plist;
         if(tot % plist > 0) pagesu += 1;
         return pagesu;
      }

   @GetMapping("/user")
   public String userlist(@RequestParam("page")int page, Model model) {
      int spage = page;
       if (page <= 0) spage = 1;
       
      ArrayList<UserDto> slist = (ArrayList<UserDto>)dataDao.getUserAll();
      ArrayList<UserDto> result = getuserListData(slist, spage);

      
      int user_records = dataDao.usercount();

      model.addAttribute("lists", result);
      model.addAttribute("pagesu", getuserPageSu());
      model.addAttribute("page", spage);
      model.addAttribute("user_records", user_records);
       
      return "../templates/user/user";
   }
   
   @PostMapping("usersearch")  // user에서 검색하기
   public String usersearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page,FormBean bean, Model model) {  //넘어가니까 Model 사용
      int spage = page;
       if (page <= 0) spage = 1 ;
      ArrayList<UserDto> userlist = (ArrayList<UserDto>)dataDao.getUserSearch(bean);
      ArrayList<UserDto> userresult = getuserListData(userlist, spage);
      
      model.addAttribute("lists", userresult);
      model.addAttribute("pagesu", getuserPageSu());
      model.addAttribute("page", spage);
      return "../templates/user/user";
   }
   
   @PostMapping("userdelete")
   public String userdel(@RequestParam("user_id")String user_id,
         @RequestParam(name = "page", defaultValue = "1") int page) {
      if(dataDao.userdelete(user_id))
         return "redirect:user?page=" + page;
      else
         return "redirect:error";
   }

   public ArrayList<OwnerDto> getownerListData(ArrayList<OwnerDto> list, int page){
         ArrayList<OwnerDto> ownresult = new ArrayList<OwnerDto>();
         
         int start = (page - 1) * plist;
         int end = Math.min(start + plist, list.size());
         
         for (int i = start; i < end; i++) {
            ownresult.add(list.get(i));
         }
         return ownresult;
      }
      
      public int getownerPageSu() {
         tot = dataDao.totalOwner();
         int pagesu = tot / plist;
         if(tot % plist > 0) pagesu += 1;
         return pagesu;
      }

   @GetMapping("/owner")
   public String ownerlist(@RequestParam("page")int page, Model model) {
      int spage = page;
       if (page <= 0) spage = 1;
      
      ArrayList<OwnerDto> ownerlist = (ArrayList<OwnerDto>)dataDao.getOwnerAll();
      ArrayList<OwnerDto> ownerresult = getownerListData(ownerlist, spage);
      
      int owner_records = dataDao.getownerrecords();
      
      model.addAttribute("lists2", ownerresult);
      model.addAttribute("pagesu", getownerPageSu());
      model.addAttribute("page", spage);
      model.addAttribute("owner_records",owner_records);
      return "../templates/owner/owner";
   }
   
   @PostMapping("ownersearch")
   public String ownersearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page, FormBean bean, Model model) {  //넘어가니까 Model 사용
      int spage = page;
       if (page <= 0) spage = 1 ;
      
      ArrayList<OwnerDto> slist2 = (ArrayList<OwnerDto>)dataDao.getOwnerSearch(bean);
      ArrayList<OwnerDto> result = getownerListData(slist2, spage);
      
      model.addAttribute("lists2", result);
      model.addAttribute("pagesu", getownerPageSu());
      model.addAttribute("page", spage);
      
      return "../templates/owner/owner";
   }

   @PostMapping("ownerdelete")
   public String ownerdel(@RequestParam("business_num") String business_num,
           @RequestParam(name = "page", defaultValue = "1") int page) {
       try {
           if (dataDao.ownerdelete(business_num)) {
               return "redirect:owner?page=" + page;
           } else {
               return "redirect:error";
           }
       } catch (Exception e) {
           return "/owner/error";
       }
   }


   public ArrayList<ContainerDto> getregisteredListData(ArrayList<ContainerDto> list, int page){
         ArrayList<ContainerDto> regresult = new ArrayList<ContainerDto>();
         
         int start = (page - 1) * plist;
         int end = Math.min(start + plist, list.size());
         
         for (int i = start; i < end; i++) {
            regresult.add(list.get(i));
         }
         return regresult;
      }
      
      public int getregisteredPageSu() {
         tot = dataDao.totalRegistered();
         int pagesu = tot / plist;
         if(tot % plist > 0) pagesu += 1;
         return pagesu;
      }
   
   
   @GetMapping("/registered")
   public String registeredlist(@RequestParam(name = "page", required = false, defaultValue = "1") int page,Model model) {
      int spage = page;
       if (page <= 0) spage = 1;
       
      ArrayList<ContainerDto> slist3 = (ArrayList<ContainerDto>)dataDao.getConAll();
      ArrayList<ContainerDto> result = getregisteredListData(slist3, spage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pagesu", getregisteredPageSu());
      model.addAttribute("page", spage);
      return "../templates/admin/cont_registered";
   }
   
   @PostMapping("regsearch")
   public String regsearch(@RequestParam(name="page", required = false, defaultValue = "1")int page, FormBean bean, Model model) {
      int spage = page;
      if(page <= 0) spage = 1;
      
      ArrayList<ContainerDto> slist3 = (ArrayList<ContainerDto>)dataDao.getRegSearch(bean);
      ArrayList<ContainerDto> result = getregisteredListData(slist3, spage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pagesu", getregisteredPageSu());
       model.addAttribute("page", spage);
      
      return "../templates/admin/cont_registered";
   }

}
