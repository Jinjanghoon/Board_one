package com.study.board_one.service;

import com.study.board_one.entity.Board1;
import com.study.board_one.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

  @Autowired
  private BoardRepository boardRepository;

  // 글작성
  public void write(Board1 board) { //Board1 이라는 클래스에 board라는 변수를 넣어줌

    boardRepository.save(board);
  }

  // 글리스트 처리
  public List<Board1> boardlist() {

    return boardRepository.findAll();
  }

  // 특정 게시글 불러오기
  // Integer id 로 담아오면 id 값에 따라서 데이터를 받아옴
  // Integer 1 로 담아오면 1번 값에 담긴 데이터를 받아오게됨
  public Board1 boardView(Integer id) {

    return boardRepository.findById(id).get();
  }

}
