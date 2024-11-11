package com.acorn.api.controller.board;

import com.acorn.api.dto.board.BoardListDTO;
import com.acorn.api.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardPageController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public String boardList(Model model) {
        List<BoardListDTO> boardData = boardService.getBoardListData();
        model.addAttribute("boardData", boardData);
        return "board/board-list";
    }

    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        String boardWriter = boardService.getAuthenticatedUserName();
        model.addAttribute("boardWriter", boardWriter);
        return "board/board-write";
    }
}
