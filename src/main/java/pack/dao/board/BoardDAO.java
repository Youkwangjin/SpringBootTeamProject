package pack.dao.board;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pack.dto.board.BoardDTO;
import pack.repository.data.DataMapping;


@Repository
@AllArgsConstructor
public class BoardDAO {

	private final DataMapping dataMapping;

	public List<BoardDTO> listAll() {
		List<BoardDTO> list = dataMapping.selectList();
		System.out.println(list);
		return list;
	}

	public List<BoardDTO> search(BoardDTO boardDTO) {
		List<BoardDTO> list = dataMapping.searchList(boardDTO);
		System.out.println(list);
		return list;
	}

	public int totalCnt() {
		return dataMapping.totalCnt();
	}

	public boolean insert(BoardDTO boardDTO) {
		boolean b = false;
		int re = dataMapping.insertData(boardDTO);
		if (re > 0)
			b = true;
		return b;
	}

	public int currentNum() {
		return dataMapping.currentNum();
	}

	public void updateReadcnt(String num) {
		dataMapping.updateReadCnt(num);
	}

	public BoardDTO detail(String num) {
		BoardDTO boardDTO = dataMapping.selectOne(num);
		System.out.println(boardDTO);
		return boardDTO;
	}

	public boolean update(BoardDTO boardDTO) {
		boolean b = false;
		int re = dataMapping.updateData(boardDTO);
		if (re > 0)
			b = true;
		return b;
	}

	public String selectPass(String num) { // 수정시 비밀번호 비교용
		return dataMapping.selectPass(num);
	}

	public boolean delete(String num) {
		boolean b = false;
		int re = dataMapping.deleteData(num);
		if (re > 0)
			b = true;
		return b;
	}

	// 댓글
	public boolean updateOnum(BoardDTO boardDTO) {
		// 댓글에서 onum 갱신
		boolean b = false;
		int re = dataMapping.updateOnum(boardDTO);
		if (re > 0)
			b = true;
		return b;
	}

	public boolean insertReply(BoardDTO boardDTO) {
		boolean b = false;
		int re = dataMapping.insertReData(boardDTO);
		if (re > 0)
			b = true;
		return b;
	}
}
