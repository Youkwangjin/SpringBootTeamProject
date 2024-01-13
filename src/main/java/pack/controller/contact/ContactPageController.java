package pack.controller.contact;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
public class ContactPageController {

	@Autowired
	private ContactDAO contactDao;
	
	private int tot;
	private int plist = 10;
	
	public ArrayList<ContactDTO> getListData(ArrayList<ContactDTO> list, int page){
		ArrayList<ContactDTO> result = new ArrayList<ContactDTO>();
		
		int start = (page - 1) * plist; // 0, 10, 20
		int end = Math.min(start + plist, list.size());
		
		for (int i = start; i < end; i++) {
			result.add(list.get(i));
		}
		return result;
	}
	
	public int getPageSu() { // 총 페이지 수 얻기
		tot = contactDao.totalContact();
		int pagesu = tot / plist;
		if(tot % plist > 0) pagesu += 1;

		return pagesu;
	}
	
	@GetMapping("contactadmin")
	public String listProcess(@RequestParam("page")int page, Model model) {
		int spage = page;
		if (page <= 0) spage = 1;
		
		ArrayList<ContactDTO> list = (ArrayList<ContactDTO>)contactDao.listContact();
		ArrayList<ContactDTO> result = getListData(list, spage);
		
		model.addAttribute("contactadmin", result);
		model.addAttribute("pagesu", getPageSu());
		model.addAttribute("page", spage);
		
		return "../templates/contact/contactadmin";
	}
}
