package com.study.board_one.controller;

import com.study.board_one.entity.Board1;
import com.study.board_one.repository.BoardRepository;
import com.study.board_one.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

  @Autowired
  private BoardService boardService;

  // getmapping은 어떤 URL로 접근할것인지 지정해주는 매퍼
  // http://localhost:8080/boardwrite 라는 URL로 접근시 boardwrite라는 html페이지를 보여주겠다고 선언하는 부분
  @GetMapping("/write")
  public String boardWrite() {

    return "boardwrite"; // 어떤 html파일로 이동해줄것인지 return 해주는 부분
  }

  // 데이터가 실제로 넘어오는지 확인해주는 공간
  @PostMapping("writedo")
  public String writeDo(Board1 board) { // String 형태로 title 값을 받아옴, String 형태로 content를 받아옴

    boardService.write(board);

    return "";
  }

  @GetMapping("list")
  public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable) {

    Page<Board1> list = boardService.boardlist(pageable);

    // pageable 에서 넘어온 현재 page를 가져올 수 있음. -> pageable은 0에서 시작하므로 +1 해서 1페이지부터 출력되도록 해줌
    int nowPage = list.getPageable().getPageNumber() + 1;
    // Math.max 매서드를 이용해 nowPage 가 1보다 작아질경우 더 큰값인 1을 잡아줌 -> 최소 1페이지라는 뜻
    int startPage = Math.max(nowPage - 4, 1);
    // Math.min으로 인해 noewPage + 5를 했을때 최대 페이지를 넘어갈 경우, list.getTotalPages() -> 마지막페이지를 띄움
    int endPage = Math.min(nowPage + 5, list.getTotalPages());

    // list라는 이름으로 보낼것, 무엇을 담을것인가? -> boardService 안의 boardlist() 라는 매서드에 담기는 값
    model.addAttribute("list", list);
    model.addAttribute("nowPage", nowPage);
    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

    return "boardlist";
  }

  @GetMapping("view")
  // Integer 데이터 타입으로 id를 받아옴
  // http://localhost:8080/view?id=1 -> ?id=1 의 값이 바로 아래의 Integer id 에 1의 값이 들어가고
  // 아래 boardService.boardView(id) 값 안에 1이 들어가면서 1번 게시물에 대한 데이터를 불러옴.
  public String boardView(Model model, Integer id) {

    // board 라는 이름으로 model 안에 boardService의 boardView 라는 메서드의 id값(Integer)의 값을 넣어줌.
    model.addAttribute("board", boardService.boardView(id));
    return "boardview";
  }

  // 게시글 삭제
  @GetMapping("delete")
  public String boardDelete(Integer id) {

    boardService.boardDelete(id);

    return "redirect:/list";
  }
  
  // 게시글 수정
  @GetMapping("modify/{id}")
  public String boardModify(@PathVariable("id") Integer id, Model model) {

    model.addAttribute("board", boardService.boardView(id));
    return "boardmodify";
  }

  // 게시글 업데이트
  @PostMapping("update/{id}")
  public String boardUpdate(@PathVariable("id") Integer id, Board1 board) {
    
    // boardTemp에 기존에 있던 글이 담겨져 들어오게됨
    Board1 boardTemp = boardService.boardView(id);
    boardTemp.setTitle(board.getTitle());
    boardTemp.setContent(board.getContent());

    // getTitle,getContent로 인해 수정한 내용이 담긴 boardTemp의 setTitle,setContent의 값을 write해서 새로 밀어넣음
    boardService.write(boardTemp);
    return "redirect:/list";
  }
}
