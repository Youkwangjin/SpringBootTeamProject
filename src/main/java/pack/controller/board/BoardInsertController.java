package pack.controller.board;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import pack.dto.board.BoardDTO;
import pack.service.board.BoardInsertService;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardInsertController {

    private final BoardInsertService boardInsertService;

    @GetMapping("/insert")
    public String BoardInsertForm() {
        return "board/board-insert-admin";
    }

    @PostMapping("/insert")
    public String BoardInsertProcess(BoardDTO boardDTO, HttpServletRequest request) {
        boolean b = boardInsertService.insert(boardDTO, request);
        if (b) {
            return "redirect:/board/list/admin?page=1";
        } else {
            return "redirect:/error";
        }
    }
}

