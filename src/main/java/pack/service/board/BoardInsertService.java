package pack.service.board;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import pack.dto.board.BoardDTO;

@Service
public interface BoardInsertService {
    boolean insert(BoardDTO boardDTO, HttpServletRequest request);
}

