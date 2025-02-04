package com.acorn.api.repository.board;

import com.acorn.api.common.PaginationRequest;
import com.acorn.api.entity.board.Board;
import com.acorn.api.entity.board.BoardFile;
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

    public Integer selectBoardFileIdKey() {
        return sql.selectOne("Board.selectBoardFileIdKey");
    }

    public int selectListCountByRequest(PaginationRequest pagination) {
        return sql.selectOne("Board.selectListCountByRequest", pagination);
    }

    public List<Board> selectBoardListData(PaginationRequest pagination) {
        return sql.selectList("Board.selectBoardListData", pagination);
    }

    public void boardSave(Board newBoardSaveData) {
        sql.insert("Board.insertBoard", newBoardSaveData);
    }

    public void insertBoardFile(BoardFile boardFile) {
        sql.insert("Board.insertBoardFile", boardFile);
    }

    public Board selectBoardDetailData(Integer boardId) {
        return sql.selectOne("Board.selectBoardDetailData", boardId);
    }

    public void updateBoardHits(Integer boardId) {
        sql.update("Board.updateBoardHits", boardId);
    }
}