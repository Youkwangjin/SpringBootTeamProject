package pack.dao.board;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pack.dto.board.BoardDTO;
import pack.mapper.board.BoardMapper;


@Repository
@AllArgsConstructor
public class BoardDAO {

	private final BoardMapper boardMapper;

	public List<BoardDTO> listAll() {
		List<BoardDTO> list = boardMapper.selectList();
		System.out.println(list);
		return list;
	}

	public List<BoardDTO> search(BoardDTO boardDTO) {
		List<BoardDTO> list = boardMapper.searchList(boardDTO);
		System.out.println(list);
		return list;
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
		return boardMapper.currentNum();
	}

	public void updateReadcnt(String num) {
		boardMapper.updateReadCnt(num);
	}

	public BoardDTO detail(String num) {
		BoardDTO boardDTO = boardMapper.selectOne(num);
		System.out.println(boardDTO);
		return boardDTO;
	}

	public boolean update(BoardDTO boardDTO) {
		boolean b = false;
		int re = boardMapper.updateData(boardDTO);
		if (re > 0)
			b = true;
		return b;
	}

	public String selectPass(String num) { // 수정시 비밀번호 비교용
		return boardMapper.selectPass(num);
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
