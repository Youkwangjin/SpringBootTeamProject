package pack.service.board.boardimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.board.BoardDAO;
import pack.service.board.BoardDeleteService;

@Service
@RequiredArgsConstructor
public class BoardDeleteServiceImpl implements BoardDeleteService {

    private final BoardDAO boardDAO;

    @Override
    public boolean delete(String num) {
        return boardDAO.delete(num);
    }
}

