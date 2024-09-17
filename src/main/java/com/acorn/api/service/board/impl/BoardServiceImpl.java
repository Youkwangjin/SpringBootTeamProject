package com.acorn.api.service.board.impl;

import com.acorn.api.dto.board.BoardResponseDTO;
import com.acorn.api.model.board.Board;
import com.acorn.api.repository.board.BoardRepository;
import com.acorn.api.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<BoardResponseDTO> getBoardListData() {

        List<Board> boardListData = boardRepository.selectBoardListData();

        return boardListData.stream()
                .map(board -> BoardResponseDTO.builder()
                        .boardId(board.getBoardId())
                        .boardTitle(board.getBoardTitle())
                        .boardWriter(board.getBoardWriter())
                        .boardContents(board.getBoardContents())
                        .boardContentsText(board.getBoardContentsText())
                        .boardHits(board.getBoardHits())
                        .boardCreated(board.getBoardCreated())
                        .boardUpdated(board.getBoardUpdated())
                        .build())
                .collect(Collectors.toList());
    }
}
