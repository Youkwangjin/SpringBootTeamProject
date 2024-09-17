package com.acorn.api.service.board;

import com.acorn.api.dto.board.BoardResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<BoardResponseDTO> getBoardListData();
}
