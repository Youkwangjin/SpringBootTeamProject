package com.acorn.api.service.board.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.board.BoardDetailDTO;
import com.acorn.api.dto.board.BoardSaveDTO;
import com.acorn.api.dto.board.BoardListDTO;
import com.acorn.api.entity.board.Board;
import com.acorn.api.entity.board.BoardFile;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.board.BoardRepository;
import com.acorn.api.service.board.BoardService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Value("${file.upload.path.board}")
    private String uploadDir;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    @Override
    public List<BoardListDTO> getBoardListData(BoardListDTO boardListDTO) {
        boardListDTO.setTotalCount(boardRepository.selectListCountByRequest(boardListDTO));
        List<Board> boardListData = boardRepository.selectBoardListData(boardListDTO);

        return boardListData.stream()
                .map(board -> BoardListDTO.builder()
                        .rowNum(board.getRowNum())
                        .boardId(board.getBoardId())
                        .boardTitle(board.getBoardTitle())
                        .boardWriter(board.getBoardWriter())
                        .boardCreated(board.getBoardCreated())
                        .boardHits(board.getBoardHits())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String getAuthenticatedUserName() {
        Object principal = CommonSecurityUtil.getCurrentId();

        if (principal == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }
        return CommonSecurityUtil.getAuthenticatedName();
    }

    @Override
    @Transactional
    public void boardDataSave(BoardSaveDTO boardSaveDTO) {
        Integer boardUserId = CommonSecurityUtil.getCurrentUserId();
        Integer boardOwnerId = CommonSecurityUtil.getCurrentOwnerId();

        if (boardUserId == null && boardOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        final Integer boardId = boardRepository.selectBoardIdKey();
        final String boardTitle = boardSaveDTO.getBoardTitle();
        final String boardWriter = boardSaveDTO.getBoardWriter();
        final String encodedBoardPassword = passwordEncoder.encode(boardSaveDTO.getBoardPassword());
        final String boardContents = boardSaveDTO.getBoardContents();

        Board newBoardSaveData = Board.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardWriter(boardWriter)
                .boardPassword(encodedBoardPassword)
                .boardContents(boardContents)
                .boardContentsText(Jsoup.parse(boardContents).text())
                .boardUserId(boardUserId)
                .boardOwnerId(boardOwnerId)
                .build();
        boardRepository.boardSave(newBoardSaveData);

        if(boardSaveDTO.getBoardFiles() != null && !boardSaveDTO.getBoardFiles().isEmpty()) {
            for(MultipartFile file : boardSaveDTO.getBoardFiles()) {
                final Integer boardFileId = boardRepository.selectBoardFileIdKey();
                final String originalFileName = FilenameUtils.getName(file.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", boardId, boardFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(file.getSize());

                BoardFile boardFile = BoardFile.builder()
                        .boardFileId(boardFileId)
                        .boardOriginalFileName(originalFileName)
                        .boardStoredFileName(storedFileName)
                        .boardFilePath(filePath)
                        .boardFileExtNm(fileExtNm)
                        .boardFileSize(fileSize)
                        .boardId(boardId)
                        .build();
                boardRepository.insertBoardFile(boardFile);
            }
        }
    }

    @Override
    public BoardDetailDTO getBoardDetailData(Long boardId) {
        /*
            1. 상세보기에는 목록, 수정, 삭제 버튼이 있다
            2. 자신이 작성한 게시글만 수정, 삭제 버튼이 보이고 아니면 목록만 보여진다.
         */
        return null;
    }
}