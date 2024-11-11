package com.acorn.api.service.board.impl;

import com.acorn.api.dto.board.BoardSaveDTO;
import com.acorn.api.dto.board.BoardListDTO;
import com.acorn.api.model.board.Board;
import com.acorn.api.model.board.BoardFile;
import com.acorn.api.repository.board.BoardRepository;
import com.acorn.api.service.board.BoardService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    @Override
    public List<BoardListDTO> getBoardListData() {

        List<Board> boardListData = boardRepository.selectBoardListData();

        return boardListData.stream()
                .map(board -> BoardListDTO.builder()
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
    public void boardDataSave(BoardSaveDTO boardSaveDTO) {
        Object principal = CommonSecurityUtil.getAuthenticatedUUId();

        if (principal == null) {
            throw new AccessDeniedException("Unauthorized access - user is not logged in");
        }
        String encodedBoardPassword = passwordEncoder.encode(boardSaveDTO.getBoardPassword());

        Board newBoardSaveData = Board.builder()
                .boardTitle(boardSaveDTO.getBoardTitle())
                .boardWriter(boardSaveDTO.getBoardWriter())
                .boardPassword(encodedBoardPassword)
                .boardContents(boardSaveDTO.getBoardContents())
                .boardContentsText(Jsoup.parse(boardSaveDTO.getBoardContents()).text())
                .build();
        Board savedBoard = boardRepository.boardSave(newBoardSaveData);

        if(boardSaveDTO.getBoardFiles() != null && !boardSaveDTO.getBoardFiles().isEmpty()) {
            for(MultipartFile file : boardSaveDTO.getBoardFiles()) {
                String originalFileName = file.getOriginalFilename();
                String storedFileName = String.format("[%s_%s]%s", newBoardSaveData.getBoardId(), UUID.randomUUID().toString().replaceAll("-", ""), originalFileName);
                String fileSize = String.valueOf(file.getSize());

                BoardFile boardFile = BoardFile.builder()
                        .boardOriginalFileName(originalFileName)
                        .boardStoredFileName(storedFileName)
                        .boardFileSize(fileSize)
                        .boardId(savedBoard.getBoardId())
                        .build();
                boardRepository.insertBoardFile(boardFile);
            }
        }
    }
}