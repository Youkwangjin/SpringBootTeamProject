package pack.dao.board;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pack.dto.board.BoardDTO;
import pack.mapper.board.BoardMapper;


@Repository
@AllArgsConstructor
public class BoardDAO {

    private final BoardMapper boardMapper;

    public List<BoardDTO> listAll() {
        return boardMapper.selectList();
    }

    public List<BoardDTO> search(BoardDTO boardDTO) {
        return boardMapper.searchList(boardDTO);
    }

    public int totalCnt() {
        return boardMapper.totalCnt();
    }

    public boolean insert(BoardDTO boardDTO) {
        boolean b = false;
        int re = boardMapper.insertData(boardDTO);
        if (re > 0)
            b = true;
        return b;
    }

    public int currentNum() {
        Integer currentNum = boardMapper.currentNum();
        return Objects.requireNonNullElse(currentNum, 0);
    }

    public void updateReadcnt(String num) {
        boardMapper.updateReadCnt(num);
    }

    public BoardDTO detail(String num) {
        return boardMapper.selectOne(num);
    }

    public boolean update(BoardDTO boardDTO) {
        boolean b = false;
        int re = boardMapper.updateData(boardDTO);
        if (re > 0)
            b = true;
        return b;
    }

    public boolean delete(String num) {
        boolean b = false;
        int re = boardMapper.deleteData(num);
        if (re > 0)
            b = true;
        return b;
    }

    // 댓글
    public boolean updateOnum(BoardDTO boardDTO) {
        // 댓글에서 onum 갱신
        boolean b = false;
        int re = boardMapper.updateOnum(boardDTO);
        if (re > 0)
            b = true;
        return b;
    }

    public boolean insertReply(BoardDTO boardDTO) {
        boolean b = false;
        int re = boardMapper.insertReData(boardDTO);
        if (re > 0)
            b = true;
        return b;
    }
}
