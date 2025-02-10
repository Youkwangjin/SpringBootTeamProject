package com.acorn.api.service.board.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.board.BoardDetailDTO;
import com.acorn.api.dto.board.BoardFileDTO;
import com.acorn.api.dto.board.BoardSaveDTO;
import com.acorn.api.dto.board.BoardListDTO;
import com.acorn.api.entity.board.Board;
import com.acorn.api.entity.board.BoardFile;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.board.BoardFileRepository;
import com.acorn.api.repository.board.BoardRepository;
import com.acorn.api.service.board.BoardService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
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
    private final BoardFileRepository boardFileRepository;
    private final FileComponent fileComponent;

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
            for(MultipartFile multipartFile : boardSaveDTO.getBoardFiles()) {
                final Integer boardFileId = boardFileRepository.selectBoardFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", boardId, boardFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                BoardFile boardFile = BoardFile.builder()
                        .boardFileId(boardFileId)
                        .boardOriginalFileName(originalFileName)
                        .boardStoredFileName(storedFileName)
                        .boardFilePath(filePath)
                        .boardFileExtNm(fileExtNm)
                        .boardFileSize(fileSize)
                        .boardId(boardId)
                        .build();
                boardFileRepository.boardFileSave(boardFile);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }

    @Override
    public BoardDetailDTO getBoardDetailData(Integer boardId) {
        Board detailData = boardRepository.selectBoardDetailData(boardId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.BOARD_NOT_FOUND);
        }

        //boardRepository.updateBoardHits(boardId);

        List<BoardFileDTO> boardFileDTOs = detailData.getBoardFilesList().stream()
                .map(file -> BoardFileDTO.builder()
                        .boardFileId(file.getBoardFileId())
                        .boardOriginalFileName(file.getBoardOriginalFileName())
                        .boardStoredFileName(file.getBoardStoredFileName())
                        .boardFilePath(file.getBoardFilePath())
                        .boardFileExtNm(file.getBoardFileExtNm())
                        .boardFileSize(file.getBoardFileSize())
                        .boardFileCreated(file.getBoardFileCreated())
                        .boardFileUpdated(file.getBoardFileUpdated())
                        .build())
                .collect(Collectors.toList());

        return BoardDetailDTO.builder()
                .boardId(detailData.getBoardId())
                .boardTitle(detailData.getBoardTitle())
                .boardWriter(detailData.getBoardWriter())
                .boardContents(detailData.getBoardContents())
                .boardCreated(detailData.getBoardCreated())
                .boardHits(detailData.getBoardHits() + 1)
                .boardFiles(boardFileDTOs)
                .build();
    }
}