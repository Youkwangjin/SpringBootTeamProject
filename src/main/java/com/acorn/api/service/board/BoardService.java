package com.acorn.api.service.board;

import com.acorn.api.dto.board.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<BoardListDTO> getBoardListData(BoardListDTO boardListDTO);

    String getAuthenticatedUserName();

    void boardDataSave(BoardSaveDTO saveData);

    BoardDetailDTO getBoardDetailData(Integer boardId);

    void boardDataUpdate(BoardUpdateDTO updateData);

    void boardDataDelete(BoardDeleteDTO deleteData);
}
