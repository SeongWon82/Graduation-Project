package com.seongwon.publictransport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seongwon.publictransport.domain.BusArrivalItem;
import com.seongwon.publictransport.domain.BusArrivalList;
import com.seongwon.publictransport.domain.RealtimeArrivalList;
import com.seongwon.publictransport.domain.TrainArrivalList;
import com.seongwon.publictransport.domain.User;
import com.seongwon.publictransport.repository.JpaUserRepository;
import com.seongwon.publictransport.repository.UserRepository;
import com.seongwon.publictransport.service.UserService;
import com.seongwon.publictransport.websocket.MonitorThread;
import com.seongwon.publictransport.websocket.SubThread;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

@SpringBootTest
@Transactional
class ServerApplicationTests {
	
//	@Test
//	void contextLoads() {
//		
//		User user = new User();
//		user.setId("123");
//		user.setPwd("234");
//		user.setEmail("hong@naver.com");
//		
//		String id = userService.join(user);
//		
//		User finder = userService.findOne(id).get();
//		assertThat(user.getId()).isEqualTo(finder.getId());
//	}
//	@Test
//	void 로그인1() // 성공 케이스
//	{
//		User user = new User();
//		user.setId("1");
//		user.setPwd("1234");
//		user.setEmail("temp@mail.com");
//		user.setUser_type("native");
//		
//		assertThat(userService.login(user)).isEqualTo(HttpStatus.OK);
//	}
//	@Test
//	void 로그인2() // 실패 케이스 1
//	{
//		User user = new User();
//		user.setId("1");
//		user.setPwd("2345");
//		user.setEmail("temp@mail.com");
//		user.setUser_type("native");
//		
//		assertThat(userService.login(user)).isEqualTo(HttpStatus.NO_CONTENT);
//	}
//	@Test
//	void 로그인3() // 실패 케이스 2
//	{
//		User user = new User();
//		user.setId("2");
//		user.setPwd("4567");
//		user.setEmail("temp@mail.com");
//		user.setUser_type("native");
//		
//		assertThat(userService.login(user)).isEqualTo(HttpStatus.NOT_FOUND);
//	}
//	@Test
//	void 카카오로그인1() // 카카오 로그인 성공 케이스 1
//	{
//		User user = new User();
//		user.setId("1004");
//		user.setEmail("temp@mail.com");
//		user.setUser_type("kakao");
//		
//		HttpStatus hs = userService.oauth(user);
//		
//		Optional<User> find = userService.findOne(user.getId());
//		if(!find.isEmpty())
//		{
//			System.out.println(find.get().getId());
//			System.out.println(find.get().getEmail());
//			System.out.println(find.get().getUser_type());
//		}
//		
//		assertThat(hs).isEqualTo(HttpStatus.OK);
//	}
	
	
}
