package com.sparta.todolist.controller;

import com.sparta.todolist.dto.ResponseDto;
import com.sparta.todolist.dto.TestRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // http와 관련된 코드 및 요청/응답 매핑을 스프링이 해준다.
public class TestController {

    @GetMapping("/test")
    public String testController() {
        return "Hello,Spring boot!";
    }

    //Pathvariable
    @GetMapping("/{id}")
    public String pathvariableTest(@PathVariable(required = false) int id) { //@Pathvariable : 요청 매개변수를 받을 때 사용한다.
        return "Hello World! Id : " + id;
    }

    //RequestParam : ?id={id}와 같이 요청 매개변수로 넘어오는 값을 변수로 받을 수 있다.
    @GetMapping("/testRequestParam")
    public String requestParamTest(@RequestParam String id) {
        return id;
    }

    //@RequestBody : String이나 int 같은 기본 자료형이 아닌 오브젝트처럼 복잡한 자료형을 요청할 때 사용한다.
    @GetMapping("testtest")
    public String requestBodyTest(@RequestBody TestRequestDto testRequestDto) {
        return "test ID : " + testRequestDto.getId() + "message : " + testRequestDto.getMessage();
    }

    @GetMapping("testResponseBody")
    public ResponseDto<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello! world! I'm ResponseDto");
        ResponseDto<String> responseDto = ResponseDto.<String>builder().data(list).build(); //<String> -> 제네릭화
        return responseDto;
    }
    //@ResponseEntity : 헤더와 HTTP status를 자유자재로 조작할 수 있다.
    @GetMapping("ResponseEntityTest")
    public ResponseEntity<?> responseEntity(){
        List<String> list = new ArrayList<>();
        list.add("반갑습니다. Hello ResponseEntity!");
        ResponseDto<String> responseDto = ResponseDto.<String>builder().data(list).build();
        //http를 400으로 지정 , badRequest : 400에러,ok도 가능 , body : body에 responseDto 변수를 넣음.
        return ResponseEntity.badRequest().body(responseDto);
    }

}
