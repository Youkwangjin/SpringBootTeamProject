package pack.controller.contact;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
public class ContactInsertController {

   @Autowired
   private ContactDAO contactDao;
   
   @GetMapping("insertcontactuser")
   public String insertUser() {
      return "../templates/contact/insert";
   }
   
   @PostMapping("insertcontactuser")
   public String insertSubmitUser(ContactDTO contactDTO, Model model) {
      
      LocalDateTime currentDateTime = LocalDateTime.now();
      model.addAttribute("contact_date", currentDateTime);
      
      boolean b = contactDao.insertContact(contactDTO);
         
      if(b) {
         return "user/user-mypage"; // 추가 후 목록 보기
      } else {
         return "error";
      }
   }
   
   @GetMapping("insertcontactowner")
   public String insertOwner() {
      return "../templates/contact/insert";
   }
   
   @PostMapping("insertcontactowner")
   public String insertSubmitOwner(ContactDTO contactDTO, Model model) {
      
      LocalDateTime currentDateTime = LocalDateTime.now();
      model.addAttribute("contact_date", currentDateTime);
      
      boolean b = contactDao.insertContact(contactDTO);
         
      if(b) {
         return "owner/owner-main"; // 추가 후 목록 보기
      } else {
         return "error";
      }
   }
   
}