package pack.controller.board;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import pack.dao.board.BoardDAO;


@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class DetailControllerBoard {

	private final BoardDAO boardDAO;
	
	@GetMapping("/detail")
	public String detailProcess(
			@RequestParam("num") String num,
			@RequestParam("page") String page, Model model) {
		// 조회수 증가 선행
		boardDAO.updateReadcnt(num);
		
		model.addAttribute("data", boardDAO.detail(num));
		model.addAttribute("page", page);
		
		return "/board/board-detail";
	}
	
	@RequestMapping("/detail")
    public String detail(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") != null) {
            return "redirect:board/detailAdmin";
        } else {
            return "/board/board-detail";
        }
    }
	
	
	@GetMapping("/detailAdmin")
	public String detailAdminProcess(
			@RequestParam("num") String num,
			@RequestParam("page") String page, Model model) {
		// 조회수 증가 선행
		boardDAO.updateReadcnt(num);
		
		model.addAttribute("data", boardDAO.detail(num));
		model.addAttribute("page", page);
		
		return "/board/board-detail";
	}
}
