package pack.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import pack.model.board.BoardDaoImpl;

@Controller
@RequestMapping("/board")
public class InsertController {
	@Autowired
	private BoardDaoImpl daoImpl;
	
	@GetMapping("insert")
	public String insertform() {
		return "board/insert";
	}
	
	@PostMapping("insert")
	public String insertProcess(BoardBean bean) {
		bean.setBdate();

		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) ip = req.getRemoteAddr();

		int newNum = daoImpl.currentNum() + 1;
		bean.setNum(newNum);
		bean.setGnum(newNum);
		
		boolean b = daoImpl.insert(bean);
		if(b) {
			return "redirect:listAdmin?page=1";
		}else {
			return "redirect:error";
		}
	}
}
