package pack.controller.contact;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.contact.ContactDAO;

@Controller
@AllArgsConstructor
public class ContactDeleteController {

    private final ContactDAO contactDao;
	
	@GetMapping("/contactDelete")
	public String delete(@RequestParam("contact_no")String contact_no) {
		boolean b = contactDao.deleteContact(contact_no);
	      if(b)
	    	 return "redirect:/contactAdmin?page=1";
	      else
	         return "/container/container-error";
	   }
}
