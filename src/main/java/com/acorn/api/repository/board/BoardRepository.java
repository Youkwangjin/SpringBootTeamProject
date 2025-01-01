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

    public Integer selectBoardIdKey() {
        return sql.selectOne("Board.selectBoardIdKey");
    }

    public List<Board> selectBoardListData() {
        return sql.selectList("Board.selectBoardListData");
    }

    public int boardSave(Board newBoardSaveData) {
        return sql.insert("Board.insertBoard", newBoardSaveData);
    }

    public void insertBoardFile(BoardFile boardFile) {
        sql.insert("Board.insertBoardFile", boardFile);
    }
}