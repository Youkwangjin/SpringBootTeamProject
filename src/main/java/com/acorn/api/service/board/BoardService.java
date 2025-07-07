package com.acorn.api.service.board;

import com.acorn.api.dto.board.request.BoardDeleteReqDTO;
import com.acorn.api.dto.board.request.BoardLikeReqDTO;
import com.acorn.api.dto.board.request.BoardSaveReqDTO;
import com.acorn.api.dto.board.request.BoardUpdateReqDTO;
import com.acorn.api.dto.board.response.BoardDetailResDTO;
import com.acorn.api.dto.board.response.BoardFileDownloadResDTO;
import com.acorn.api.dto.board.response.BoardLikeResDTO;
import com.acorn.api.dto.board.response.BoardListResDTO;
import com.acorn.api.dto.common.CommonListReqDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<BoardListResDTO> getBoardListData(CommonListReqDTO listData);

    String getAuthenticatedUserName();

    void boardDataSave(BoardSaveReqDTO saveData);

    BoardDetailResDTO getBoardDetailData(Integer boardId);

    void boardDataUpdate(BoardUpdateReqDTO updateData);

    void boardDataDelete(BoardDeleteReqDTO deleteData);

    BoardFileDownloadResDTO boardFileDownload(Integer boardId, Integer boardFileId);

    BoardLikeResDTO boardLike(BoardLikeReqDTO requestData);
}