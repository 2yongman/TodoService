package com.sparta.todolist.repository;

import com.sparta.todolist.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long>{
    //sql = Select * From TodoRepository WHERE userId = {userId};
    List<TodoEntity> findByUserId(String userId);

}
