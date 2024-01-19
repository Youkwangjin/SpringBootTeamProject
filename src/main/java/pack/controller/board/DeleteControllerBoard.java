package pack.controller.board;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dao.board.BoardDAO;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class DeleteControllerBoard {

	private final BoardDAO daoImpl;
		
	@GetMapping("delete")
	public String del(@RequestParam("num")String num,
			 		  @RequestParam("page")String page) {
		if(daoImpl.delete(num))
			return "redirect:listAdmin?page=" + page;
		else
			return "redirect:error";
	}
}
