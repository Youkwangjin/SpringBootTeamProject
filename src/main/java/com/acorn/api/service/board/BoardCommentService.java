package com.acorn.api.service.board;

import com.acorn.api.dto.board.request.BoardCommentReplySaveReqDTO;
import com.acorn.api.dto.board.request.BoardCommentSaveReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface BoardCommentService {

    void boardCommentSave(BoardCommentSaveReqDTO reqestData);

    void boardCommentReplySave(BoardCommentReplySaveReqDTO reqestData);
}