package pack.repository.data;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import pack.controller.board.BoardBean;
import pack.dto.board.BoardDTO;


@Mapper
public interface DataMapping {
	List<BoardDTO> selectList();
	List<BoardDTO> searchList(BoardBean bean);
	BoardDTO selectOne(String num);
	
	int insertData(BoardBean bean);
	int updateData(BoardBean bean);
	int deleteData(String num);
	
	int updateReadcnt(String num);
	int currentNum();
	int totalCnt();
	String selectPass(String num);
	
	int updateOnum(BoardBean bean);
	int insertReData(BoardBean bean);

}
