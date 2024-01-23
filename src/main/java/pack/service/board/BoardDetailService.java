package pack.service.board;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pack.dto.board.BoardDTO;

@Service
public interface BoardDetailService {
    boolean isAdmin(HttpSession session);
    void updateReadcnt(String num);
    BoardDTO detail(String num);
}
