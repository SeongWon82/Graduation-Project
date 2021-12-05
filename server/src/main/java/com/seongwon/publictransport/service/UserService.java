package com.seongwon.publictransport.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seongwon.publictransport.domain.User;
import com.seongwon.publictransport.repository.JpaUserRepository;
import com.seongwon.publictransport.repository.UserRepository;

@Transactional
public class UserService {

	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	} 
	// 중복회원 검증
	private void vaildDuplicateUser(User user)
	{
		repository.findById(user.getId()).ifPresent(m->{
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}	
	public HttpStatus join(User user) 
	{
		if(user == null)
			return HttpStatus.BAD_REQUEST;
		
		if(user.getPwd() == null && user.getUser_type().equals("native"))
			return HttpStatus.BAD_REQUEST;
		
		vaildDuplicateUser(user);
		switch(user.getUser_type())
		{
			case "native":
				repository.insert(user);
				break;
			case "kakao":
				repository.insert(user);
				break;
			default:
				return HttpStatus.BAD_REQUEST;
		}
		return HttpStatus.OK;
	}
	
	public HttpStatus login(User user)
	{
		Optional<User> find = repository.findById(user.getId());
		
		// 존재하지 않는 회원
		if(find.isEmpty())
			return HttpStatus.NOT_FOUND;
		else
		{
			if(find.get().getPwd().equals(user.getPwd()))
				return HttpStatus.OK;
			else
				return HttpStatus.NO_CONTENT;
		}
	}
	
	public HttpStatus oauth(User user)
	{
		Optional<User> find = repository.findById(user.getId());
		
		if(find.isEmpty())
			join(user);
		if(user.getUser_type().equals("kakao"))
			return HttpStatus.OK;
		else
			return HttpStatus.NOT_FOUND;
	}
	
	public Optional<User> findOne(String id)
	{
		return repository.findById(id);
	}
	
}
