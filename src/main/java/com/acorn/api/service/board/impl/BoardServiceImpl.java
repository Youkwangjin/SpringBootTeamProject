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

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FileComponent fileComponent;

    @Value("${file.upload.path.board}")
    private String uploadDir;

    @Override
    public List<BoardListDTO> getBoardListData(BoardListDTO listData) {
        listData.setTotalCount(boardRepository.selectListCountByRequest(listData));
        List<Board> boardListData = boardRepository.selectBoardListData(listData);

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

        boolean isAuthor = hasEditPermission(currentUserId, currentOwnerId, boardUserId, boardOwnerId);

        boardRepository.updateBoardHits(boardId);

        List<BoardFile> boardFileEntities = detailData.getBoardFilesList();
        final List<BoardFileDTO> boardFileData = boardFileEntities.stream()
                .map(boardFile -> {
                    final Integer boardFileId = boardFile.getBoardFileId();
                    final String boardOriginalFileName = boardFile.getBoardOriginalFileName();
                    final String boardStoredFileName = boardFile.getBoardStoredFileName();
                    final String boardFilePath = boardFile.getBoardFilePath();
                    final String boardFileExtNm = boardFile.getBoardFileExtNm();
                    final String boardFileSize = boardFile.getBoardFileSize();

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
                .boardFiles(boardFileData)
                .build();
    }

    @Override
    @Transactional
    public void boardDataSave(BoardSaveDTO saveData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer boardId = boardRepository.selectBoardIdKey();
        final String boardTitle = saveData.getBoardTitle();
        final String boardWriter = saveData.getBoardWriter();
        final String boardPassword = passwordEncoder.encode(saveData.getBoardPassword());
        final String boardContents = saveData.getBoardContents();
        final String boardContentsText = Jsoup.parse(boardContents).text();
        final List<MultipartFile> boardFiles = saveData.getBoardFiles();

        if (currentUserId == null && currentOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Board saveBoardData = Board.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardWriter(boardWriter)
                .boardPassword(boardPassword)
                .boardContents(boardContents)
                .boardContentsText(boardContentsText)
                .boardUserId(currentUserId)
                .boardOwnerId(currentOwnerId)
                .build();

        boardRepository.saveBoard(saveBoardData);

        if(boardFiles!= null && !boardFiles.isEmpty()) {
            for(MultipartFile multipartFile : boardFiles) {
                final Integer boardFileId = boardFileRepository.selectBoardFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", boardId, boardFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                BoardFile saveBoardFileData = BoardFile.builder()
                        .boardFileId(boardFileId)
                        .boardOriginalFileName(originalFileName)
                        .boardStoredFileName(storedFileName)
                        .boardFilePath(filePath)
                        .boardFileExtNm(fileExtNm)
                        .boardFileSize(fileSize)
                        .boardId(boardId)
                        .build();

                boardFileRepository.saveBoardFile(saveBoardFileData);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }

    @Override
    @Transactional
    public void boardDataUpdate(BoardUpdateDTO updateData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer boardId = updateData.getBoardId();
        final String boardTitle = updateData.getBoardTitle();
        final String boardPassword = updateData.getBoardPassword();
        final String boardWriter = updateData.getBoardWriter();
        final String boardContents = updateData.getBoardContents();
        final String boardContentsText = Jsoup.parse(boardContents).text();
        final Integer boardUserId = updateData.getBoardUserId();
        final Integer boardOwnerId = updateData.getBoardOwnerId();
        final List<MultipartFile> boardFiles = updateData.getBoardFiles();

        if (!hasEditPermission(currentUserId, currentOwnerId, boardUserId, boardOwnerId)) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        if (boardUserId == null && boardOwnerId == null) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Board detailData = boardRepository.selectBoardDetailData(boardId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.BOARD_NOT_FOUND);
        }

        final String existingBoardPassword = detailData.getBoardPassword();
        if (StringUtils.isBlank(boardPassword) || !passwordEncoder.matches(boardPassword, existingBoardPassword)) {
            throw new AcontainerException(ApiValidationErrorCode.PASSWORD_STRENGTH_ERROR);
        }

        Board updateBoardData = Board.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardWriter(boardWriter)
                .boardContents(boardContents)
                .boardContentsText(boardContentsText)
                .build();

        boardRepository.updateBoard(updateBoardData);

        List<Integer> boardFileIds = updateData.getBoardFileIds();
        if (boardFileIds == null) {
            boardFileIds = new ArrayList<>();
        }

        List<BoardFile> existingFiles = boardFileRepository.selectFilesByBoardId(boardId);
        List<Integer> deletedFileIds = new ArrayList<>();
        for (BoardFile boardFile : existingFiles) {
            if (!boardFileIds.contains(boardFile.getBoardFileId())) {
                deletedFileIds.add(boardFile.getBoardFileId());
            }
        }

        for (Integer boardFileId : deletedFileIds) {
            BoardFile boardFile = boardFileRepository.selectFilesByBoardFileId(boardFileId);
            if (boardFile == null) {
                throw new AcontainerException(ApiErrorCode.FILE_NOT_FOUND);
            }
            final String filePath = boardFile.getBoardFilePath();
            final String storedFileName = boardFile.getBoardStoredFileName();

            fileComponent.delete(filePath, storedFileName);
            boardFileRepository.deleteBoardFile(boardFileId);
        }

        if(boardFiles != null && !boardFiles.isEmpty()) {
            for(MultipartFile multipartFile : boardFiles) {
                final Integer boardFileId = boardFileRepository.selectBoardFileIdKey();
                final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
                final String storedFileName = String.format("[%s_%s]%s", boardId, boardFileId, UUID.randomUUID().toString().replaceAll("-", ""));
                final String filePath = uploadDir;
                final String fileExtNm = FilenameUtils.getExtension(originalFileName);
                final String fileSize = String.valueOf(multipartFile.getSize());

                BoardFile newFileData = BoardFile.builder()
                        .boardFileId(boardFileId)
                        .boardOriginalFileName(originalFileName)
                        .boardStoredFileName(storedFileName)
                        .boardFilePath(filePath)
                        .boardFileExtNm(fileExtNm)
                        .boardFileSize(fileSize)
                        .boardId(boardId)
                        .build();

                boardFileRepository.saveBoardFile(newFileData);
                fileComponent.upload(filePath, storedFileName, multipartFile);
            }
        }
    }

    @Override
    @Transactional
    public void boardDataDelete(BoardDeleteDTO deleteData) {
        final Integer currentUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer boardId = deleteData.getBoardId();
        final Integer boardUserId = deleteData.getBoardUserId();
        final Integer boardOwnerId = deleteData.getBoardOwnerId();

        boolean isAuthor = hasEditPermission(currentUserId, currentOwnerId, boardUserId, boardOwnerId);
        if (!isAuthor) {
            throw new AcontainerException(ApiHttpErrorCode.FORBIDDEN_ERROR);
        }

        Board detailData = boardRepository.selectBoardDetailData(boardId);
        if (detailData == null) {
            throw new AcontainerException(ApiErrorCode.BOARD_NOT_FOUND);
        }

        List<BoardFile> existingFiles = boardFileRepository.selectFilesByBoardId(boardId);
        if (existingFiles != null && !existingFiles.isEmpty()) {
            for (BoardFile boardFile : existingFiles) {
                final Integer boardFileId = boardFile.getBoardFileId();
                final String storedFileName = boardFile.getBoardStoredFileName();
                final String filePath = boardFile.getBoardFilePath();

                fileComponent.delete(filePath, storedFileName);
                boardFileRepository.deleteBoardFile(boardFileId);
            }
        }

        Board deleteBoardData = Board.builder()
                .boardId(boardId)
                .build();

        boardRepository.deleteBoard(deleteBoardData);
    }

    private boolean hasEditPermission(Integer currentUserId, Integer currentOwnerId, Integer boardUserId, Integer boardOwnerId) {
        return (currentUserId != null && Objects.equals(currentUserId, boardUserId)) ||
               (currentOwnerId != null && Objects.equals(currentOwnerId, boardOwnerId));
    }
}