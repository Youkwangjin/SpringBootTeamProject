package pack.controller.contact;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
@AllArgsConstructor
public class ContactInsertController {


   private final ContactDAO contactDao;
   
   @GetMapping("/insertContactUser")
   public String insertUser() {
      return "/contact/contact-user";
   }
   
   @PostMapping("/insertContactUser")
   public String insertSubmitUser(ContactDTO contactDTO, Model model) {
      
      LocalDateTime currentDateTime = LocalDateTime.now();
      model.addAttribute("contact_date", currentDateTime);
      
      boolean b = contactDao.insertContact(contactDTO);
         
      if(b) {
         return "user/user-mypage";
      } else {
         return "container/container-error";
      }
   }
   
   @GetMapping("/insertContactOwner")
   public String insertOwner() {
      return "/contact/contact-owner";
   }
   
   @PostMapping("/insertContactOwner")
   public String insertSubmitOwner(ContactDTO contactDTO, Model model) {
      
      LocalDateTime currentDateTime = LocalDateTime.now();
      model.addAttribute("contact_date", currentDateTime);
      
      boolean b = contactDao.insertContact(contactDTO);
         
      if(b) {
         return "/owner/owner-mypage";
      } else {
         return "container/container-error";
      }
   }
   
}