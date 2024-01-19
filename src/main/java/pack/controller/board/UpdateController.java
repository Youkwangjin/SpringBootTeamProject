package pack.controller.board;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.board.BoardDAO;

import pack.dto.board.BoardDTO;


@Controller
@RequestMapping("/board")
@AllArgsConstructor
public class UpdateController {


    private final BoardDAO daoImpl;
    
    @GetMapping("/update")
    public String edit(@RequestParam("num") String num,
                       @RequestParam("page") String page,
                       Model model) {
        BoardDTO dto = daoImpl.detail(num);
        model.addAttribute("data", dto);
        model.addAttribute("page", page);
        return "/board/board-update";
    }
    
    @PostMapping("/update")
    public String editProcess(BoardDTO boardDTO,
                              @RequestParam("page") String page) {
        boolean b = daoImpl.update(boardDTO);
        if (b) {
            return "redirect:detailAdmin?num=" + boardDTO.getNum() + "&page=" + page;
            
        } else {
            return "/board/board-error";
        }
    }
}