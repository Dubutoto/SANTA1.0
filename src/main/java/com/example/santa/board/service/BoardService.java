package com.example.santa.board.service;

import com.example.santa.board.mapper.BoardMapper;
import com.example.santa.board.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService implements BoardServiceInterface {

    private final BoardMapper boardMapper;

    @Override
    public int insertBoard(BoardVO boardVO) {
        return boardMapper.insertBoard(boardVO);
    }


    @Override
    public BoardVO selectById(int boardId) {
        return boardMapper.selectById(boardId);
    }

    /**
     * 모든 게시글을 조회합니다.
     * @return 모든 게시글 정보의 리스트
     */
    @Override
    public List<BoardVO> selectBoardAll() {
        return boardMapper.selectBoardAll();
    }

    /**
     * 게시글 정보를 업데이트합니다.
     * @param boardVO 업데이트할 게시글 정보
     * @return 업데이트된 행의 수
     */
    @Override
    public int updateBoard(BoardVO boardVO) {
        return boardMapper.updateBoard(boardVO);
    }


    @Override
    public int deleteBoard(int boardId) {
        return boardMapper.deleteBoard(boardId);
    }



}