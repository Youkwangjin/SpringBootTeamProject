package pack.mapper.data;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import pack.dto.board.BoardDTO;


@Mapper
public interface DataMapping {
	List<BoardDTO> selectList();
	List<BoardDTO> searchList(BoardDTO boardDTO);
	BoardDTO selectOne(String num);
	
	int insertData(BoardDTO boardDTO);
	int updateData(BoardDTO boardDTO);
	int deleteData(String num);

	int updateReadCnt(String num);
	int currentNum();
	int totalCnt();
	String selectPass(String num);
	
	int updateOnum(BoardDTO boardDTO);
	int insertReData(BoardDTO boardDTO);

}
