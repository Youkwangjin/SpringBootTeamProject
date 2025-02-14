package com.acorn.api.service.board.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.component.FileComponent;
import com.acorn.api.dto.board.*;
import com.acorn.api.entity.board.Board;
import com.acorn.api.entity.board.BoardFile;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.board.BoardFileRepository;
import com.acorn.api.repository.board.BoardRepository;
import com.acorn.api.service.board.BoardService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                .map(boardList -> {
                    final Integer rowNum = boardList.getRowNum();
                    final Integer boardId = boardList.getBoardId();
                    final String boardTitle = boardList.getBoardTitle();
                    final String boardWriter = boardList.getBoardWriter();
                    final Integer boardHits = boardList.getBoardHits();
                    final LocalDateTime boardCreated = boardList.getBoardCreated();

                    return BoardListDTO.builder()
                            .rowNum(rowNum)
                            .boardId(boardId)
                            .boardTitle(boardTitle)
                            .boardWriter(boardWriter)
                            .boardHits(boardHits)
                            .boardCreated(boardCreated)
                            .build();
                })
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

        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final String boardTitle = detailData.getBoardTitle();
        final String boardWriter = detailData.getBoardWriter();
        final String boardContents = detailData.getBoardContents();
        final String boardContentsText = detailData.getBoardContentsText();
        final Integer boardHits = detailData.getBoardHits();
        final LocalDateTime boardCreated = detailData.getBoardCreated();
        final Integer boardUserId = detailData.getBoardUserId();
        final Integer boardOwnerId = detailData.getBoardOwnerId();

        boolean isAuthor = Objects.equals(currentUserId, boardUserId) || Objects.equals(currentOwnerId, boardOwnerId);

        boardRepository.updateBoardHits(boardId);

        List<BoardFile> boardFileEntities = detailData.getBoardFilesList();
        final List<BoardFileDTO> boardFileDTOs = boardFileEntities.stream()
                .map(file -> {
                    final Integer boardFileId = file.getBoardFileId();
                    final String boardOriginalFileName = file.getBoardOriginalFileName();
                    final String boardStoredFileName = file.getBoardStoredFileName();
                    final String boardFilePath = file.getBoardFilePath();
                    final String boardFileExtNm = file.getBoardFileExtNm();
                    final String boardFileSize = file.getBoardFileSize();

                    return BoardFileDTO.builder()
                            .boardFileId(boardFileId)
                            .boardOriginalFileName(boardOriginalFileName)
                            .boardStoredFileName(boardStoredFileName)
                            .boardFilePath(boardFilePath)
                            .boardFileExtNm(boardFileExtNm)
                            .boardFileSize(boardFileSize)
                            .build();
                })
                .collect(Collectors.toList());

        return BoardDetailDTO.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardWriter(boardWriter)
                .boardContents(boardContents)
                .boardContentsText(boardContentsText)
                .boardHits(boardHits + 1)
                .boardCreated(boardCreated)
                .boardUserId(boardUserId)
                .boardOwnerId(boardOwnerId)
                .isAuthor(isAuthor)
                .boardFiles(boardFileDTOs)
                .build();
    }

    @Override
    @Transactional
    public void boardDataUpdate(BoardUpdateDTO updateData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer boardId = updateData.getBoardId();
        final String boardTitle = updateData.getBoardTitle();
        final String boardWriter = updateData.getBoardWriter();
        final String boardContents = updateData.getBoardContents();
        final Integer boardUserId = updateData.getBoardUserId();
        final Integer boardOwnerId = updateData.getBoardOwnerId();

        boolean isAuthor = Objects.equals(currentUserId, boardUserId) || Objects.equals(currentOwnerId, boardOwnerId);
        if (!isAuthor) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Board detailData = boardRepository.selectBoardDetailData(boardId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.BOARD_NOT_FOUND);
        }

        if (StringUtils.isNotBlank(updateData.getBoardPassword()) && !passwordEncoder.matches(updateData.getBoardPassword(), detailData.getBoardPassword())) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        Board newBoardUpdateData = Board.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardWriter(boardWriter)
                .boardContents(boardContents)
                .boardContentsText(Jsoup.parse(boardContents).text())
                .build();
        boardRepository.boardUpdate(newBoardUpdateData);

        List<Integer> boardFileIds = updateData.getBoardFileIds();
        if (boardFileIds == null) {
            boardFileIds = new ArrayList<>();
        }

        List<BoardFile> existingFiles = boardFileRepository.findByBoardId(boardId);

        List<Integer> deletedFileIds = new ArrayList<>();
        for (BoardFile boardFile : existingFiles) {
            if (!boardFileIds.contains(boardFile.getBoardFileId())) {
                deletedFileIds.add(boardFile.getBoardFileId());
            }
        }

        for (Integer boardFileId : deletedFileIds) {
            BoardFile boardFile = boardFileRepository.findById(boardFileId);
            if (boardFile == null) {
                throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
            }

            fileComponent.delete(boardFile.getBoardFilePath(), boardFile.getBoardStoredFileName());
            boardFileRepository.boardFileDelete(boardFileId);
        }

        if(updateData.getBoardFiles() != null && !updateData.getBoardFiles().isEmpty()) {
            for(MultipartFile multipartFile : updateData.getBoardFiles()) {
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
}