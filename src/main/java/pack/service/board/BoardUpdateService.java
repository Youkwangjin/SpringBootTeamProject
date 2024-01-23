package pack.service.board;

import org.springframework.stereotype.Service;
import pack.dto.board.BoardDTO;

@Service
public interface BoardUpdateService {
    BoardDTO detail(String num);
    boolean update(BoardDTO boardDTO);
}

