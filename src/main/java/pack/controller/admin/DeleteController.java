package pack.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.model.DataDao;


@Controller
public class DeleteController {
   @Autowired
   private DataDao dataDao;
   
   @GetMapping("delete")
   public String delete(@RequestParam("cont_no")String cont_no) {
	   System.out.println(cont_no);
      boolean b = dataDao.condelete(cont_no);
      if(b)
    	 return "redirect:/registered";
      else
         return "error";
   }
}