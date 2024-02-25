package pack.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import pack.service.board.BoardDetailService;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardDetailController {

    private final BoardDetailService boardDetailService;

    @GetMapping("/detail")
    public String BoardDetailProcess(
            HttpServletRequest request,
            @RequestParam("num") String num,
            @RequestParam("page") String page, Model model) {

        if (boardDetailService.isAdmin(request.getSession())) {
            model.addAttribute("data", boardDetailService.detail(num));
            model.addAttribute("page", page);
            return "board/board-detail-admin";
        } else {
            boardDetailService.updateReadcnt(num);
            model.addAttribute("data", boardDetailService.detail(num));
            model.addAttribute("page", page);
            return "board/board-detail";
        }
    }

    @GetMapping("/detail/admin")
    public String BoardDetailAdminProcess(
            @RequestParam("num") String num,
            @RequestParam("page") String page, Model model) {
        boardDetailService.updateReadcnt(num);

        model.addAttribute("data", boardDetailService.detail(num));
        model.addAttribute("page", page);

        return "board/board-detail-admin";
    }


}
