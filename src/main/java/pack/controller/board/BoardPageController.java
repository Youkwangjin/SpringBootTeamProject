package pack.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardPageController {

    @GetMapping("/board/list")
    public String boardList() {
        return "board/board-list";
    }
}
