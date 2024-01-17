package pack.dao.board;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pack.controller.board.BoardBean;
import pack.dto.board.BoardDTO;
import pack.repository.data.DataMapping;


@Repository
public class BoardDAO {

	@Autowired
	private DataMapping dataInterface;

	public List<BoardDTO> listAll() {
		List<BoardDTO> list = dataInterface.selectList();
		return list;
	}

	public List<BoardDTO> search(BoardBean bean) {
		List<BoardDTO> list = dataInterface.searchList(bean);
		return list;
	}

	public int totalCnt() {
		return dataInterface.totalCnt();
	}

	public boolean insert(pack.controller.board.BoardBean bean) {
		boolean b = false;
		int re = dataInterface.insertData(bean);
		if (re > 0)
			b = true;
		return b;
	}

	public int currentNum() {
		return dataInterface.currentNum();
	}

	public void updateReadcnt(String num) {
		dataInterface.updateReadcnt(num);
	}

	public BoardDTO detail(String num) {
		BoardDTO dto = dataInterface.selectOne(num);
		return dto;
	}

	public boolean update(pack.controller.board.BoardBean bean) {
		boolean b = false;
		int re = dataInterface.updateData(bean);
		if (re > 0)
			b = true;
		return b;
	}

	public String selectPass(String num) { // 수정시 비밀번호 비교용
		return dataInterface.selectPass(num);
	}

	public boolean delete(String num) {
		boolean b = false;
		int re = dataInterface.deleteData(num);
		if (re > 0)
			b = true;
		return b;
	}

	// 댓글
	public boolean updateOnum(pack.controller.board.BoardBean bean) {
		// 댓글에서 onum 갱신
		boolean b = false;
		int re = dataInterface.updateOnum(bean);
		if (re > 0)
			b = true;
		return b;
	}

	public boolean insertReply(pack.controller.board.BoardBean bean) {
		boolean b = false;
		int re = dataInterface.insertReData(bean);
		if (re > 0)
			b = true;
		return b;
	}
}