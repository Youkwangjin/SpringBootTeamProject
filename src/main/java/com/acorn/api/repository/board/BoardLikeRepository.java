package com.acorn.api.repository.board;

import com.acorn.api.entity.board.BoardLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardLikeRepository {

    Integer selectBoardLikeIdKey();

    List<BoardLike> selectBoardLikeByBoardId(@Param("boardLikeBoardId") Integer boardId);

    BoardLike selectBoardLikeCountByBoardId(@Param("boardLikeBoardId") Integer boardId, @Param("boardLikeUserId") Integer currentUserId, @Param("boardLikeOwnerId") Integer currentOwnerId);

    BoardLike selectBoardLikeByCurrentId(@Param("boardLikeBoardId") Integer boardId, @Param("boardLikeUserId") Integer currentUserId, @Param("boardLikeOwnerId") Integer currentOwnerId);

    void saveBoardLike(BoardLike BoardLike);

    void updateBoardLike(BoardLike boardLike);
}