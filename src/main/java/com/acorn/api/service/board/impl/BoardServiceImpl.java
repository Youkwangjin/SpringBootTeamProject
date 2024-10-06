package com.acorn.api.service.board.impl;

import com.acorn.api.dto.board.BoardRegisterDTO;
import com.acorn.api.dto.board.BoardResponseDTO;
import com.acorn.api.model.board.Board;
import com.acorn.api.repository.board.BoardRepository;
import com.acorn.api.service.board.BoardService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    @Override
    public List<BoardResponseDTO> getBoardListData() {

        List<Board> boardListData = boardRepository.selectBoardListData();

        return boardListData.stream()
                .map(board -> BoardResponseDTO.builder()
                        .rowNum(board.getRowNum())
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

    @Override
    public String getAuthenticatedUserName() {
        Object principal = CommonSecurityUtil.getAuthenticatedUUId();

        if (principal == null) {
            throw new AccessDeniedException("Unauthorized access - user is not logged in");
        }
        return CommonSecurityUtil.getAuthenticatedName();
    }

    @Override
    public void boardDataSave(BoardRegisterDTO boardRegisterData) {

        String encodedBoardPassword = passwordEncoder.encode(boardSaveDTO.getBoardPassword());

        Board newBoardSaveData = Board.builder()
                .boardTitle(boardSaveDTO.getBoardTitle())
                .boardWriter(boardSaveDTO.getBoardWriter())
                .boardPassword(encodedBoardPassword)
                .boardContents(boardSaveDTO.getBoardContents())
                .boardContentsText(Jsoup.parse(boardSaveDTO.getBoardContents()).text())
                .build();

        boardRepository.boardSave(newBoardSaveData);
    }
}