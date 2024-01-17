package pack.controller.admin;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.form.FormDTO;
import pack.dao.data.DataDAO;
import pack.dto.container.ContainerDTO;
import pack.dto.owner.OwnerDTO;
import pack.dto.user.UserDTO;

@Controller

public class ListController {
   @Autowired
   private DataDAO dataDao;
   
   private int tot;
   private int plist = 10;
   
   public ArrayList<UserDTO> getUserListData(ArrayList<UserDTO> list, int page){
         ArrayList<UserDTO> result = new ArrayList<UserDTO>();
         
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
   public String userList(@RequestParam("page")int page, Model model) {
      int sPage = page;
       if (page <= 0) sPage = 1;
       
      ArrayList<UserDTO> sList = (ArrayList<UserDTO>)dataDao.getUserAll();
      ArrayList<UserDTO> result = getUserListData(sList, sPage);

      
      int user_records = dataDao.usercount();

      model.addAttribute("lists", result);
      model.addAttribute("pageSu", getuserPageSu());
      model.addAttribute("page", sPage);
      model.addAttribute("user_records", user_records);
       
      return "../templates/user/user";
   }
   
   @PostMapping("userSearch")
   public String userSearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page, FormDTO bean, Model model) {  //넘어가니까 Model 사용
      int sPage = page;
       if (page <= 0) sPage = 1 ;
      ArrayList<UserDTO> userList = (ArrayList<UserDTO>)dataDao.getUserSearch(bean);
      ArrayList<UserDTO> userResult = getUserListData(userList, sPage);
      
      model.addAttribute("lists", userResult);
      model.addAttribute("pageSu", getuserPageSu());
      model.addAttribute("page", sPage);
      return "/user/user";
   }
   
   @PostMapping("userdelete")
   public String userDelete(@RequestParam("user_id")String user_id,
         @RequestParam(name = "page", defaultValue = "1") int page) {
      if(dataDao.userdelete(user_id))
         return "redirect:user?page=" + page;
      else
         return "redirect:error";
   }

   public ArrayList<OwnerDTO> getownerListData(ArrayList<OwnerDTO> list, int page){
         ArrayList<OwnerDTO> ownresult = new ArrayList<OwnerDTO>();
         
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
      
      ArrayList<OwnerDTO> ownerlist = (ArrayList<OwnerDTO>)dataDao.getOwnerAll();
      ArrayList<OwnerDTO> ownerresult = getownerListData(ownerlist, spage);
      
      int owner_records = dataDao.getownerrecords();
      
      model.addAttribute("lists2", ownerresult);
      model.addAttribute("pagesu", getownerPageSu());
      model.addAttribute("page", spage);
      model.addAttribute("owner_records",owner_records);
      return "../templates/owner/owner";
   }
   
   @PostMapping("ownersearch")
   public String ownersearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page, FormDTO bean, Model model) {  //넘어가니까 Model 사용
      int spage = page;
       if (page <= 0) spage = 1 ;
      
      ArrayList<OwnerDTO> slist2 = (ArrayList<OwnerDTO>)dataDao.getOwnerSearch(bean);
      ArrayList<OwnerDTO> result = getownerListData(slist2, spage);
      
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


   public ArrayList<ContainerDTO> getregisteredListData(ArrayList<ContainerDTO> list, int page){
         ArrayList<ContainerDTO> regresult = new ArrayList<ContainerDTO>();
         
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
       
      ArrayList<ContainerDTO> slist3 = (ArrayList<ContainerDTO>)dataDao.getConAll();
      ArrayList<ContainerDTO> result = getregisteredListData(slist3, spage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pagesu", getregisteredPageSu());
      model.addAttribute("page", spage);
      return "admin/cont_registered";
   }
   
   @PostMapping("regsearch")
   public String regsearch(@RequestParam(name="page", required = false, defaultValue = "1")int page, FormDTO bean, Model model) {
      int spage = page;
      if(page <= 0) spage = 1;
      
      ArrayList<ContainerDTO> slist3 = (ArrayList<ContainerDTO>)dataDao.getRegSearch(bean);
      ArrayList<ContainerDTO> result = getregisteredListData(slist3, spage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pagesu", getregisteredPageSu());
       model.addAttribute("page", spage);
      
      return "../templates/admin/cont_registered";
   }

}
