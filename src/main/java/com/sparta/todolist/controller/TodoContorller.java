package com.sparta.todolist.controller;

import com.sparta.todolist.dto.ResponseDto;
import com.sparta.todolist.dto.TodoDto;
import com.sparta.todolist.model.TodoEntity;
import com.sparta.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoContorller {

    @Autowired
    private TodoService todoService;

    @GetMapping("test")
    public ResponseEntity<?> responseEntity() {
        String str = todoService.testService(); // 테스트 서비스 이용
        List<String> stringList = new ArrayList<>();
        stringList.add(str);
        ResponseDto<String> responseDto = ResponseDto.<String>builder().data(stringList).build();
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto) {
        try {
            String temporaryUserId = "temporary-user";
            //TodoEntity로 변환한다.
            TodoEntity entity = TodoDto.toEntity(dto);
            //id를 null로 초기화. 생성 당시 id가 없어야 한다.
            entity.setId(null);
            //임시 사용자 아이디 설정. 인증 인가 기능이 없으므로 한 사용자만 로그인 없이 사용할 수 있는 애플리케이션인 셈
            entity.setUserId(temporaryUserId);
            //서비스를 이용해 Todo 엔티티를 생성
            List<TodoEntity> entities = todoService.create(entity);
            //자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
            //변환된 TodoDto 리스트를 이용해 ResponseDto를 초기화
            ResponseDto<TodoDto> response =
                    ResponseDto.<TodoDto>builder()
                            .data(dtos)
                            .build();
            //ResponseDto를 리턴
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            //혹 예외가 있으면 dto 대신 error에 메시지를 넣어 리턴
            String error = e.getMessage();
            ResponseDto<TodoDto> responseDto = ResponseDto.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";

        // 서비스 메서드의 retrieve()메서드를 사용해 Todo 리스트 가져온다.
        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto dto) {
        String temporaryUserId = "temporary-user";
        TodoEntity entity = TodoDto.toEntity(dto);
        entity.setUserId(temporaryUserId);
        List<TodoEntity> entities = todoService.update(entity);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto dto) {
        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDto.toEntity(dto);
            entity.setUserId(temporaryUserId);
            List<TodoEntity> entities = todoService.delete(entity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}