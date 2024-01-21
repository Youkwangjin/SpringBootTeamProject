package pack.controller.board;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dao.board.BoardDAO;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class BoardDeleteController {

	private final BoardDAO boardDAO;
		
	@GetMapping("/delete")
	public String del(@RequestParam("num")String num,
			 		  @RequestParam("page")String page) {
		if(boardDAO.delete(num))
			return "redirect:listAdmin?page=" + page;
		else
			return "redirect:error";
	}
}
