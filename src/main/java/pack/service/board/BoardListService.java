package pack.service.board;

import org.springframework.stereotype.Service;
import pack.dto.board.BoardDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public interface BoardListService {
    ArrayList<BoardDTO> getListData(ArrayList<BoardDTO> list, int page);
    int getPageSu();
    List<BoardDTO> listAll();
    List<BoardDTO> search(BoardDTO boardDTO);
}
