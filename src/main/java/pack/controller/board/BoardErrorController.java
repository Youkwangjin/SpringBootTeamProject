package pack.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardErrorController {
	@GetMapping("/error")
	public String error() {
		return "board/board-error";
	}
}
