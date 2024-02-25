package pack.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.board.BoardDTO;
import pack.service.board.BoardUpdateService;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardUpdateController {

    private final BoardUpdateService boardUpdateService;

    @GetMapping("/update")
    public String BoardUpdate(@RequestParam("num") String num,
                              @RequestParam("page") String page,
                              Model model) {
        BoardDTO dto = boardUpdateService.detail(num);
        model.addAttribute("data", dto);
        model.addAttribute("page", page);
        return "board/board-update";
    }

    @PostMapping("/update")
    public String BoardUpdateProcess(BoardDTO boardDTO,
                                     @RequestParam("page") String page) {
        boolean b = boardUpdateService.update(boardDTO);
        if (b) {
            return "redirect:/board/list/admin?num=" + boardDTO.getNum() + "&page=" + page;
        } else {
            return "board/board-error";
        }
    }
}