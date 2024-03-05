package pack.service.board.boardimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.board.BoardDAO;
import pack.dto.board.BoardDTO;
import pack.service.board.BoardUpdateService;

@Service
@RequiredArgsConstructor
public class BoardUpdateServiceImpl implements BoardUpdateService {

    private final BoardDAO boardDAO;

    @Override
    public BoardDTO detail(String num) {
        return boardDAO.detail(num);
    }

    @Override
    public boolean update(BoardDTO boardDTO) {
        return boardDAO.update(boardDTO);
    }
}

