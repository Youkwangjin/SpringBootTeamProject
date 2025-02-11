package com.acorn.api.controller.board;

import com.acorn.api.dto.board.BoardDetailDTO;
import com.acorn.api.dto.board.BoardListDTO;
import com.acorn.api.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardPageController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public String boardList(BoardListDTO boardListDTO, Model model) {
        List<BoardListDTO> boardListData = boardService.getBoardListData(boardListDTO);
        model.addAttribute("boardData", boardListData);
        model.addAttribute("request", boardListDTO);
        return "board/board-list";
    }

    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        String boardWriter = boardService.getAuthenticatedUserName();
        model.addAttribute("boardWriter", boardWriter);
        return "board/board-write";
    }

    @GetMapping("/board/detail/{boardId}")
    public String boardDetail(@PathVariable("boardId") Integer boardId, Model model) {
        BoardDetailDTO detailData = boardService.getBoardDetailData(boardId);
        model.addAttribute("boardDetailData", detailData);
        return "board/board-detail";
    }

    @GetMapping("/board/update/{boardId}")
    public String boardUpdate(@PathVariable("boardId") Integer boardId, Model model) {
        BoardDetailDTO detailData = boardService.getBoardDetailData(boardId);
        model.addAttribute("boardDetailData", detailData);
        return "board/board-update";
    }
}