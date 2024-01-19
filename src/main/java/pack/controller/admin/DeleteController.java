package pack.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.data.DataDAO;


@Controller
@AllArgsConstructor
public class DeleteController {

   private final DataDAO dataDao;
   
   @GetMapping("delete")
   public String delete(@RequestParam("cont_no")String cont_no) {
	   System.out.println(cont_no);
      boolean b = dataDao.containerDelete(cont_no);
      if(b)
    	 return "redirect:/registered";
      else
         return "/container/container-error";
   }
}