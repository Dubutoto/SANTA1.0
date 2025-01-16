package com.example.santa.board.controller;



import com.example.santa.board.service.BoardService;
import com.example.santa.board.vo.BoardVO;
import com.example.santa.login.vo.UserDetailsVO;
import com.example.santa.reply.service.ReplyService;
import com.example.santa.reply.vo.ReplyVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

//@Log4j2
@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService; //댓글리스트


    //입력한 값을 db처리해달라고 요청!!
    @PostMapping("create2")
    public String createBoard(BoardVO boardVO) {
        System.out.println("=================>> " + boardVO);
        boardVO.setCreatedAt(LocalDateTime.now()); // private LocalDateTime createdAt;
        int result = boardService.insertBoard(boardVO);
        return "redirect:/board/board"; //redirect:==> controller의 board/board를 요청!
    }
//
//    @GetMapping("read/{boardId}")
//    public ResponseEntity<BoardVO> selectBoardByNo(@PathVariable int boardId) {
//        BoardVO board = boardService.selectBoardByNo(boardId);
//        return ResponseEntity.ok(board);
//    }

    @GetMapping("board")
    public String selectBoardAll(Model model, HttpSession session) {

        UserDetailsVO userDetails = (UserDetailsVO) session.getAttribute("userDetails");
        model.addAttribute("userId", userDetails.getUserId());
        //session.setAttribute("userId", 2);
        List<BoardVO> list = boardService.selectBoardAll();
        model.addAttribute("list", list);
        return "board/board";
    }



    // 게시글 상세 페이지
    @GetMapping("/read")
    public String read(int boardId, Model model) {
        System.out.println("=====> " + boardId);
        //게시판 상세 내용 + 댓글 리스트도 필요!!!
        BoardVO boardVO = boardService.selectById(boardId);
        System.out.println("boardVO " + boardVO);

        List<ReplyVO> list = replyService.selectReplyByBoardId(boardId);
        System.out.println("list " + list);
        if (boardVO == null) {
            return "error/404"; // 게시글이 없으면 404 에러 페이지로 이동
        }
        model.addAttribute("boardVO", boardVO);
        model.addAttribute("list", list);
        return "board/read"; // 상세 페이지 템플릿



    }

    // 게시글 작성 페이지로 이동
    @GetMapping("create")
    public String create(BoardVO boardVO, Model model) {
        return "board/create"; // 게시글 작성 페이지 템플릿
    }

//    @PutMapping("no")
//    public ResponseEntity<Integer> updateBoard(@PathVariable int boardId, @RequestBody BoardVO boardVO) {
//        boardVO.setBoardId(boardId);
//        int result = boardService.updateBoard(boardVO);
//        return ResponseEntity.ok(result);
//    }
//
//    @DeleteMapping("no")
//    public ResponseEntity<Integer> deleteBoard(@PathVariable int boardId) {
//        int result = boardService.deleteBoard(boardId);
//        return ResponseEntity.ok(result);
//    }
//
//    @GetMapping("search")
//    public ResponseEntity<List<BoardVO>> searchBoardsByContent(@RequestParam String keyword) {
//        List<BoardVO> boards = boardService.getBoardsByContent(keyword);
//        return ResponseEntity.ok(boards);
//    }


}