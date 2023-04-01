package com.study.board_one.repository;

import com.study.board_one.entity.Board1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board1, Integer> { // JpaRepository<엔티티 파일명, @Id를 지정해준 데이터타입>

}
