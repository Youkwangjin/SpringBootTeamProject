package pack.service.board.boardimpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.board.BoardDAO;
import pack.dto.board.BoardDTO;
import pack.service.board.BoardDetailService;

@Service
@RequiredArgsConstructor
public class BoardDetailServiceImpl implements BoardDetailService {

    private final BoardDAO boardDAO;

    @Override
    public boolean isAdmin(HttpSession session) {
        return session.getAttribute("adminSession") != null;
    }
    @Override
    public void updateReadcnt(String num) {
        boardDAO.updateReadcnt(num);
    }

    @Override
    public BoardDTO detail(String num) {
        return boardDAO.detail(num);
    }
}
