package com.acorn.api.service.board.impl;

import com.acorn.api.code.common.ApiErrorCode;
import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.dto.board.request.BoardCommentSaveReqDTO;
import com.acorn.api.entity.board.Board;
import com.acorn.api.entity.board.BoardComment;
import com.acorn.api.exception.global.AcontainerException;
import com.acorn.api.repository.board.BoardCommentRepository;
import com.acorn.api.repository.board.BoardRepository;
import com.acorn.api.service.board.BoardCommentService;
import com.acorn.api.utils.CommonSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardCommentServiceImpl implements BoardCommentService {

    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;

    @Override
    @Transactional
    public void boardCommentSave(BoardCommentSaveReqDTO reqestData) {
        final Integer currnetUserId = CommonSecurityUtil.getCurrentUserId();
        final Integer currentOwnerId = CommonSecurityUtil.getCurrentOwnerId();
        final Integer boardId = reqestData.getBoardId();
        final String boardCommentContents = reqestData.getBoardCommentContents();
        final String boardCommentYn = reqestData.getBoardCommentYn();

        if (currnetUserId != null && currentOwnerId != null) {
            throw new AcontainerException(ApiHttpErrorCode.UNAUTHORIZED_ERROR);
        }

        Board boardData = boardRepository.selectBoardDetailData(boardId);
        if (boardData == null) {
            throw new AcontainerException(ApiErrorCode.BOARD_NOT_FOUND);
        }

        final Integer boardCommentId = boardCommentRepository.selectBoardCommentIdKey();

        BoardComment savedBoardComment = BoardComment.builder()
                .boardCommentId(boardCommentId)
                .boardCommentBoardId(boardId)
                .boardCommentUserId(currnetUserId)
                .boardCommentOwnerId(currentOwnerId)
                .boardCommentContents(boardCommentContents)
                .boardCommentYn(boardCommentYn)
                .build();

        boardCommentRepository.saveBoardComment(savedBoardComment);
    }
}