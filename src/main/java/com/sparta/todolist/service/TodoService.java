package com.sparta.todolist.service;

import com.sparta.todolist.model.TodoEntity;
import com.sparta.todolist.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j // 로그 라이브러리
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public String testService() {
        TodoEntity todoEntity = TodoEntity.builder().title("My first todo item").build();
        todoRepository.save(todoEntity);
        //.get() : 값을 가져옴
        //TodoEntity 검색
        TodoEntity savedEntity = todoRepository.findById(todoEntity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity todoEntity) {
        //Validate
        validate(todoEntity);

        //엔티티를 데이터베이스에 저장하고 로그를 남김
        todoRepository.save(todoEntity);
        log.info("Entity Id : {} is saved.", todoEntity.getId());

        //저장된 엔티티를 포함하는 새 리스트 리턴
        return todoRepository.findByUserId(todoEntity.getUserId());
    }

    //검색
    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    //수정
    public List<TodoEntity> update(final TodoEntity entity) {
        //저장할 엔티티가 유효한지 확인.
        validate(entity);
        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
        original.ifPresent(todoEntity1 -> {
            todoEntity1.setTitle(entity.getTitle());
            todoEntity1.setDone(entity.isDone());
            todoRepository.save(todoEntity1);
        });
        return retrieve(entity.getUserId());
    }

    //삭제
    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try {
            todoRepository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);
            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    //Validation, 넘어온 데이터가 유효한지 검사하는 로직
    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }


}
