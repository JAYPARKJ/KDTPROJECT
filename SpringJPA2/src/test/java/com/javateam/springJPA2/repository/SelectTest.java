package com.javateam.springJPA2.repository;
 
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Objects;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.springJPA2.domain.TestVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class SelectTest {
	
	@Autowired
	TestRepository testRepo;
	List<TestVO> list;
	
	@BeforeEach
	public void setUp() {
		list = testRepo.findByName("오상욱");}
	// case-1) 이름으로 검색 메소드 점검 : findByName
	// case-1-2) 리스트 크기(길이) 점검
	// case-1-3) 특정값(ex. id, addrss, age 등) 점검 
	@Test
	void test() {
		list =testRepo.findByName("오상욱");
		// case-1-1) Null 점검
		assertNotNull(list);
				}

	// case-1-2) 리스트 크기(길이) 점검
	@Test
  	void testSize()
  	{ 
  		assertThat(1, equalTo(list.size()));
  	}
	// case-1-3) 특정값(ex. id, address, age 등) 점검 
	@Test
	void testTestVO() {
		
		TestVO expectedTestVO = TestVO.builder()
									  .id(2)
									  .name("오상욱")
									  .address("대전")
									  .age(27)
									  .build();
		
		TestVO  actualtestVO = list.get(0);
		
		log.info("exp hash : " + expectedTestVO.hashCode());
		log.info("act hash : " + actualtestVO.hashCode());
		
		// 한꺼번에 다수의 필드들을 비교
		//assertThat(expectedTestVO, equalTo(actualtestVO));
		
		assertTrue(expectedTestVO.equals(actualtestVO));
		/*
		 * 롬복이 데이터를 통해 hashCode만들어줌
		   커스텀 시 불편함 
		   source Generate hashCode() and equals()로 특정필드만 비교 가능
		   
	  @Override public int hashCode() { return Objects.hash(list, testRepo); }
	  
	  @Override public boolean equals(Object obj) { if (this == obj) return true;
	  if (obj == null) return false; if (getClass() != obj.getClass()) return
	  false; SelectTest other = (SelectTest) obj; return Objects.equals(list,
	  other.list) && Objects.equals(testRepo, other.testRepo); }
	 
		 **/
		
		
	}
	 
}
