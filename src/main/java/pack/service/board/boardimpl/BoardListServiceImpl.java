package pack.service.board.boardimpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pack.dao.board.BoardDAO;
import pack.dto.board.BoardDTO;
import pack.service.board.BoardListService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardListServiceImpl implements BoardListService {

    private final BoardDAO boardDAO;
    private final int pList = 15; // 페이지당 게시글 수

    @Override
    public ArrayList<BoardDTO> getListData(ArrayList<BoardDTO> list, int page) {
        ArrayList<BoardDTO> result = new ArrayList<>();
        int start = (page - 1) * pList;
        int size = Math.min(pList, list.size() - start);
        for (int i = 0; i < size; i++) {
            result.add(i, list.get(start + i));
        }
        return result;
    }

    @Override
    public int getPageSu() {
        int tot = boardDAO.totalCnt();
        int pageSu = tot / pList;
        if (tot % pList > 0) pageSu += 1;
        return pageSu;
    }

    @Override
    public List<BoardDTO> listAll() {
        return boardDAO.listAll();
    }

    @Override
    public List<BoardDTO> search(BoardDTO boardDTO) {
        return boardDAO.search(boardDTO);
    }
}

