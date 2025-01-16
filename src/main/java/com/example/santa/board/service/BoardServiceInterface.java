package com.example.santa.board.service;

import com.example.santa.board.vo.BoardVO;

import java.util.List;

public interface BoardServiceInterface {
    int insertBoard(BoardVO boardVO);

    BoardVO selectById(int boardId);

    List<BoardVO> selectBoardAll();

    int updateBoard(BoardVO boardVO);

    int deleteBoard(int boardId);
}
