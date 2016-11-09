package org.zerock.controller;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.MemberVO;
import org.zerock.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MemberDAOImplTest {
	@Inject
	private MemberDAO dao;
	
/*	@Test
	public void testGetTime() {
		System.out.println(dao.getTime());
	}*/

	@Test
	public void testInsertMember() {
		MemberVO vo = new MemberVO();
		vo.setUserid("user10");
		vo.setUserpw("1234");
		vo.setUsername("홍길동");
		vo.setEmail("aa@aa.com");
		
		dao.insertMember(vo);
	}

}
