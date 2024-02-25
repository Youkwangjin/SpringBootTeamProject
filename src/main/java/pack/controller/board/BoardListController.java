package pack.controller.board;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.dto.board.BoardDTO;
import pack.service.board.BoardDetailService;
import pack.service.board.BoardListService;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardListController {

    private final BoardListService boardListService;
    private final BoardDetailService boardDetailService;


    // 게시판 목록 처리
    @GetMapping("/list")
    public String listProcess(@RequestParam("page") int page, Model model) {
        int sPage = page <= 0 ? 1 : page;

        ArrayList<BoardDTO> list = (ArrayList<BoardDTO>) boardListService.listAll();
        ArrayList<BoardDTO> result = boardListService.getListData(list, sPage);

        model.addAttribute("data", result);
        model.addAttribute("pageSu", boardListService.getPageSu());
        model.addAttribute("page", sPage);

        return "board/board-list";
    }

    @GetMapping("/list/admin")
    public String listAdminProcess(@RequestParam("page") int page, HttpServletRequest request, Model model) {
        // 관리자 인증 체크
        if (!boardDetailService.isAdmin(request.getSession())) {
            return "redirect:/admin/login";
        }
        List<BoardDTO> totalList = boardListService.listAll();
        List<BoardDTO> boardList = boardListService.getListData(new ArrayList<>(totalList), page);
        model.addAttribute("data", boardList);
        // 전체 페이지 수를 계산
        int pageSu = boardListService.getPageSu();
        model.addAttribute("pageSu", pageSu);
        model.addAttribute("page", page);
        return "board/board-list-admin";
    }


    @GetMapping("/list/user")
    public String listUserProcess(@RequestParam("page") int page, Model model) {
        return listProcess(page, model);
    }

    @GetMapping("/list/owner")
    public String listOwnerProcess(@RequestParam("page") int page, Model model) {
        return listProcess(page, model);
    }

    @PostMapping("/search")
    public String searchProcess(BoardDTO boardDTO, Model model) {
        ArrayList<BoardDTO> list = (ArrayList<BoardDTO>) boardListService.search(boardDTO);

        model.addAttribute("data", list);
        model.addAttribute("pageSu", boardListService.getPageSu());
        model.addAttribute("page", 1);
        return "board/board-list";
    }
}

