package pack.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.*;

import pack.dto.board.BoardDTO;


@Mapper
public interface BoardMapper {
    @Select("SELECT * FROM notice_board ORDER BY bdate DESC")
    List<BoardDTO> selectList();

    @Select("SELECT * FROM notice_board WHERE ${searchName} LIKE CONCAT('%', #{searchValue}, '%') ORDER BY gnum DESC, onum ASC")
    List<BoardDTO> searchList(BoardDTO boardDTO);

    @Select("SELECT * FROM notice_board WHERE num=#{num}")
    BoardDTO selectOne(String num);

    @Insert("INSERT INTO notice_board (num, admin_id, title, cont, bdate, readcnt, gnum) VALUES (#{num}, #{admin_id}, #{title}, #{cont}, #{bdate}, 0, #{gnum})")
    int insertData(BoardDTO boardDTO);

    @Update("UPDATE notice_board SET readcnt=readcnt + 1 WHERE num=#{num}")
    void updateReadCnt(String num);

    @Update("UPDATE notice_board SET title=#{title},cont=#{cont} WHERE num=#{num}")
    int updateData(BoardDTO boardDTO);

    @Delete("DELETE FROM notice_board WHERE num=#{num}")
    int deleteData(String num);

	@Select("SELECT MAX(num) FROM notice_board")
	Integer currentNum();

    @Select("SELECT COUNT(*) FROM notice_board")
    int totalCnt();


    @Update("UPDATE notice_board SET onum=onum + 1 WHERE onum >= #{onum} AND gnum=#{gnum}")
    int updateOnum(BoardDTO boardDTO);

    @Insert("INSERT INTO notice_board VALUES(#{num},#{title},#{cont},#{bdate},0,#{gnum},#{onum},#{nested})")
    int insertReData(BoardDTO boardDTO);
}

