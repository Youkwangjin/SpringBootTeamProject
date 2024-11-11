package com.acorn.api.service.board;

import com.acorn.api.dto.board.BoardSaveDTO;
import com.acorn.api.dto.board.BoardListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<BoardListDTO> getBoardListData();

    String getAuthenticatedUserName();

    void boardDataSave(BoardSaveDTO boardSaveDTO);
}
