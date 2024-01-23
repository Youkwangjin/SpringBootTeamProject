package pack.service.board.boardimpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.board.BoardDAO;
import pack.dto.board.BoardDTO;
import pack.service.board.BoardInsertService;

@Service
@AllArgsConstructor
public class BoardInsertServiceImpl implements BoardInsertService {

    private final BoardDAO boardDAO;

    @Override
    public boolean insert(BoardDTO boardDTO, HttpServletRequest request) {
        boardDTO.setBdate();

        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = request.getRemoteAddr();
        boardDTO.setIp(ip);

        int newNum = boardDAO.currentNum() + 1;
        boardDTO.setNum(newNum);
        boardDTO.setGnum(newNum);

        return boardDAO.insert(boardDTO);
    }
}

