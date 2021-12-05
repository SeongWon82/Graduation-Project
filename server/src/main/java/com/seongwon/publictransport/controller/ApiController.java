package com.seongwon.publictransport.controller;

import com.seongwon.publictransport.domain.User;
import com.seongwon.publictransport.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ApiController
{
  private final UserService userService;

  public ApiController(UserService userService)
  {
    this.userService = userService;
  }

  @PostMapping("/join")
  @ResponseBody
  public ResponseEntity<User> join(@RequestBody User user) {
	  return new ResponseEntity<>(null, userService.join(user));
  }
  
  // 로그인
  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<User> login(@RequestBody User user) {
	  return new ResponseEntity<>(user, userService.login(user));
  }
  
  //카카오 로그인
  @PostMapping("/oauth")
  @ResponseBody
  public ResponseEntity<User> oauth(@RequestBody User user) {
	  return new ResponseEntity<>(null, userService.oauth(user));
  }
  
}