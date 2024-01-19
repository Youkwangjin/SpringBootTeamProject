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
      
      public int getUserPageSu() {
         tot = dataDao.totalUser();
         int pageSu = tot / plist;
         if(tot % plist > 0) pageSu += 1;
         return pageSu;
      }

   @GetMapping("/user")
   public String userList(@RequestParam("page")int page, Model model) {
      int sPage = page;
       if (page <= 0) sPage = 1;
       
      ArrayList<UserDTO> sList = (ArrayList<UserDTO>)dataDao.getUserAll();
      ArrayList<UserDTO> result = getUserListData(sList, sPage);

      
      int user_records = dataDao.userCount();

      model.addAttribute("lists", result);
      model.addAttribute("pageSu", getUserPageSu());
      model.addAttribute("page", sPage);
      model.addAttribute("user_records", user_records);
       
      return "/user/user";
   }
   
   @PostMapping("userSearch")
   public String userSearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page, FormDTO bean, Model model) {  //넘어가니까 Model 사용
      int sPage = page;
       if (page <= 0) sPage = 1 ;
      ArrayList<UserDTO> userList = (ArrayList<UserDTO>)dataDao.getUserSearch(bean);
      ArrayList<UserDTO> userResult = getUserListData(userList, sPage);
      
      model.addAttribute("lists", userResult);
      model.addAttribute("pageSu", getUserPageSu());
      model.addAttribute("page", sPage);
      return "/user/user";
   }
   
   @PostMapping("/userDelete")
   public String userDelete(@RequestParam("user_id")String user_id,
         @RequestParam(name = "page", defaultValue = "1") int page) {
      if(dataDao.userDelete(user_id))
         return "redirect:user?page=" + page;
      else
         return "redirect:error";
   }

   public ArrayList<OwnerDTO> getOwnerListData(ArrayList<OwnerDTO> list, int page){
         ArrayList<OwnerDTO> ownerResult = new ArrayList<OwnerDTO>();
         
         int start = (page - 1) * plist;
         int end = Math.min(start + plist, list.size());
         
         for (int i = start; i < end; i++) {
             ownerResult.add(list.get(i));
         }
         return ownerResult;
      }
      
      public int getOwnerPageSu() {
         tot = dataDao.totalOwner();
         int pageSu = tot / plist;
         if(tot % plist > 0) pageSu += 1;
         return pageSu;
      }

   @GetMapping("/owner")
   public String ownerList(@RequestParam("page")int page, Model model) {
      int Spage = page;
       if (page <= 0) Spage = 1;
      
      ArrayList<OwnerDTO> ownerList = (ArrayList<OwnerDTO>)dataDao.getOwnerAll();
      ArrayList<OwnerDTO> ownerResult = getOwnerListData(ownerList, Spage);
      
      int owner_records = dataDao.getOwnerRecords();
      
      model.addAttribute("lists2", ownerResult);
      model.addAttribute("pageSu", getOwnerPageSu());
      model.addAttribute("page", Spage);
      model.addAttribute("owner_records",owner_records);
      return "/owner/owner";
   }
   
   @PostMapping("/ownerSearch")
   public String ownerSearch(@RequestParam(name = "page", required = false, defaultValue = "1")int page, FormDTO bean, Model model) {  //넘어가니까 Model 사용
      int Spage = page;
       if (page <= 0) Spage = 1 ;
      
      ArrayList<OwnerDTO> sList2 = (ArrayList<OwnerDTO>)dataDao.getOwnerSearch(bean);
      ArrayList<OwnerDTO> result = getOwnerListData(sList2, Spage);
      
      model.addAttribute("lists2", result);
      model.addAttribute("pageSu", getOwnerPageSu());
      model.addAttribute("page", Spage);
      
      return "../templates/owner/owner";
   }

   @PostMapping("/ownerDelete")
   public String ownerDel(@RequestParam("business_num") String business_num,
           @RequestParam(name = "page", defaultValue = "1") int page) {
       try {
           if (dataDao.ownerDelete(business_num)) {
               return "redirect:owner?page=" + page;
           } else {
               return "redirect:error";
           }
       } catch (Exception e) {
           return "/owner/error";
       }
   }


   public ArrayList<ContainerDTO> getRegisteredListData(ArrayList<ContainerDTO> list, int page){
         ArrayList<ContainerDTO> regResult = new ArrayList<ContainerDTO>();
         
         int start = (page - 1) * plist;
         int end = Math.min(start + plist, list.size());
         
         for (int i = start; i < end; i++) {
             regResult.add(list.get(i));
         }
         return regResult;
      }
      
      public int getRegisteredPageSu() {
         tot = dataDao.totalRegistered();
         int pageSu = tot / plist;
         if(tot % plist > 0) pageSu += 1;
         return pageSu;
      }
   
   
   @GetMapping("/registered")
   public String registeredList(@RequestParam(name = "page", required = false, defaultValue = "1") int page,Model model) {
      int Spage = page;
       if (page <= 0) Spage = 1;
       
      ArrayList<ContainerDTO> sList3 = (ArrayList<ContainerDTO>)dataDao.getConAll();
      ArrayList<ContainerDTO> result = getRegisteredListData(sList3, Spage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pageSu", getRegisteredPageSu());
      model.addAttribute("page", Spage);
      return "admin/admin-cont-registered";
   }
   
   @PostMapping("/regSearch")
   public String regSearch(@RequestParam(name="page", required = false, defaultValue = "1")int page, FormDTO formDTO, Model model) {
      int sPage = page;
      if(page <= 0) sPage = 1;
      
      ArrayList<ContainerDTO> sList3 = (ArrayList<ContainerDTO>)dataDao.getRegSearch(formDTO);
      ArrayList<ContainerDTO> result = getRegisteredListData(sList3, sPage);
      
      model.addAttribute("lists3", result);
      model.addAttribute("pageSu", getRegisteredPageSu());
       model.addAttribute("page", sPage);
      
      return "/admin/admin-cont-registered";
   }

}
