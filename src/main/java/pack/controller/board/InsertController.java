package pack.controller.board;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import pack.dao.board.BoardDAO;
import pack.dto.board.BoardDTO;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class InsertController {

	private final BoardDAO boardDAO;
	
	@GetMapping("/insert")
	public String insertForm() {
		return "board/board-insert";
	}
	
	@PostMapping("/insert")
	public String insertProcess(BoardDTO boardDTO) {
		boardDTO.setBdate();

		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) ip = req.getRemoteAddr();

		int newNum = boardDAO.currentNum() + 1;
		boardDTO.setNum(newNum);
		boardDTO.setGnum(newNum);
		
		boolean b = boardDAO.insert(boardDTO);
		if(b) {
			return "redirect:listAdmin?page=1";
		}else {
			return "redirect:error";
		}
	}
}
