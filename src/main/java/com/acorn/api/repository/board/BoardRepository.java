package com.acorn.api.repository.board;

import com.acorn.api.model.board.Board;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final SqlSessionTemplate sql;

    public List<Board> selectBoardListData() {
        return sql.selectList("Board.selectBoardListData");
    }

    public void boardSave(Board newBoardSaveData) {
        sql.insert("Board.insertBoard", newBoardSaveData);
    }
}
