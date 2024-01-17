package pack.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.data.DataDAO;
import pack.dao.admin.AdminDAO;
import pack.dto.admin.AdminDTO;
import pack.dto.container.ContainerDTO;


@Controller
@AllArgsConstructor
public class ApproveController {

	private final DataDAO dataDao;

	private final AdminDAO adminDao;
	
	@GetMapping("/detail")
	public String detail(@RequestParam("cont_no")String cont_no, Model model) {
		ContainerDTO containerDto = dataDao.condetail(cont_no);
		model.addAttribute("containerDto",containerDto);
		return "/admin/cont_approve";
	}
	
	
	@PostMapping("/apprProcess")
	public String approveProcess(AdminDTO adminDTO) {
		System.out.println("bean" + adminDTO);
		boolean b = adminDao.approve(adminDTO);
		 System.out.println("b" + b); 
		if (b)
			return "redirect:/registered";
		else
			return "owner/error";
	}
	
	@PostMapping("/denyProcess")
	public String denyProcess(AdminDTO adminDTO) {
		System.out.println("bean" + adminDTO);
		boolean b = adminDao.deny(adminDTO);
		 System.out.println("b" + b); 
		if (b)
			return "redirect:/registered";
		else
			return "owner/error";
	}
}