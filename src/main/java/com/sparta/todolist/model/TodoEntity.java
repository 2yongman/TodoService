package com.sparta.todolist.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Id를 자동적으로 생성하겠다.
    private Long id; // 이 오브젝트의 아이디

    @Column
    private String userId; // 사용자 아이디

    @Column
    private String title; // 투두 제목

    @Column
    private boolean done; // true - todo를 완료한 경우
}
