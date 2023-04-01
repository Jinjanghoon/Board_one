package com.study.board_one.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Board1 { //Board1 이라는 클래스 안에 id, title, content 의 값이 담기게됨

  @Id // PRIMARY KEY를 의미함
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private String content;



}
