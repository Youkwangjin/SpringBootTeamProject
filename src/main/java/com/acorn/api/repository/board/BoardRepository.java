package com.acorn.api.repository.board;

import com.acorn.api.model.board.Board;
import com.acorn.api.model.board.BoardFile;
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

    public Board boardSave(Board newBoardSaveData) {
        int result = sql.insert("Board.insertBoard", newBoardSaveData);
        if (result > 0) {
            return newBoardSaveData;
        } else {
            return null;
        }
    }

    public void insertBoardFile(BoardFile boardFile) {
        sql.insert("Board.insertBoardFile", boardFile);
    }
}
