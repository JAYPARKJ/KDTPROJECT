package com.javateam.springJPA2.repository;

import static org.hamcrest.CoreMatchers.equalTo;
//import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
//import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.springJPA2.domain.TestVO;
import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j

class DAOTest2 {

	@Autowired
	TestRepository testRepo;
	
	// case-1) 4명 레코드를 삽입(샘플 데이터 입력) <- 롤백하는게 아니라 반영해줌
	//@Rollback(true)
	@Transactional
	@Rollback(true) // 의도적으로 샘플데이터 집어넣겠다는 의미 
	@Test
	void testInsert() {
		TestVO testVO;
		List<TestVO> list = new ArrayList<>();
		testVO = TestVO.builder()
	            		.name("오상욱")
	            		.address("대전")
	            		.age(27)
	            		.build();
				 list.add(testVO);
	//	testRepo.save(testVO);
		testVO = TestVO.builder()
				        .name("구본길")
				        .address("서울")
				        .age(35)
				        .build();
				list.add(testVO);
		
		testVO = TestVO.builder()
		        .name("박상원")
		        .address("대전")
		        .age(23)
		        .build();
		list.add(testVO);
		
		testVO = TestVO.builder()
				        .name("도경동")
				        .address("문경")
				        .age(24)
				        .build();
				list.add(testVO);
							
		// iterable 리스트외 사용할 상위 인터페이스로 소화시키기 위해 
		//List<TestVO> list = new ArrayList<>();
		List<TestVO> resultList = (List<TestVO>)testRepo.saveAll(list);
		// saveAll 함수 (두가지 구문으로되어 있음) 
		// insert
		// select * from test_tbl -> List<TestVO>
		assertEquals(4, resultList.size());
	
	
	}
	// case-2) 전체 인원 조회
	// case-2-1) 조회수 4명 점검
	// case-2-2) 
	// case-2-3) 첫/마지막 레코드의 특정값으로 지정
	@Test
	void testFindAll()
	
	{	List<TestVO> list = testRepo.findAll();
	
	 	// case-2) 조회 결과 존재 여부(Null 여부) 점검
	   // case-2-2) 조회수 4명 점검
		assertNotNull(list);
		 assertEquals(4, list.size());
		
		// 참고) assertJ Style -> junit + hamcrest 표현
	   //assertThat(4).isEqualTo(list.size());
		
	  // case-2-3) 첫/마지막 레코드의 특정값으로 점검
	  // 첫 레코드 : expected(오상욱)
		 String actual = list.get(0).getName();
		 assertThat("오상욱", equalTo(actual));
		 
	  // case-2-4) 
		 actual = list.get(3).getName();
		 assertThat("도경동", equalTo(actual));
		 
	
	}
}
