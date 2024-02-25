package pack.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.service.board.BoardDeleteService;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardDeleteController {

    private final BoardDeleteService boardDeleteService;

    @GetMapping("/delete")
    public String boardDelete(@RequestParam("num") String num,
                              @RequestParam("page") String page) {
        if (boardDeleteService.delete(num))
            return "redirect:/board/list/admin?page=" + page;
        else
            return "redirect:/board/error";
    }
}

