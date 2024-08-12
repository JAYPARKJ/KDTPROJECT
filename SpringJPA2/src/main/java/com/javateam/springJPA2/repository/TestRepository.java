package com.javateam.springJPA2.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
import com.javateam.springJPA2.domain.TestVO;

//@Repository // 꼭 써야되는건 아니다. 추상메서드가 아무것도 없는데 쓸 수 는 있다
							
// 코드 구현면에서 복잡하진 않다. 페이징 기법같은것 자동완성
// 깊이는 얕다
// 자동완성 생성 후 비활성화 시키면 잘 쓸 수 있을듯
//@Repository
// 여기서 CrudRepository <- 스프링 데이터 jpa , 하이버네이트등으로 잘 처리하기에 
public interface TestRepository extends CrudRepository<TestVO, Long> {
	// table 에 해당되는 domain : TestVO
	
	// 전체를 다 가져올 때 findAll : 새로운 함수  -> 이름가지고 sql만들기에 문제가 생길 수 도 있음
	
	// 전체 검색
	List<TestVO> findAll();
	
	// Query Method 작성법
	// http://arahansa.github.io/docs_spring/jpa.html#jpa.query
	// 이름(name) 으로 동등검색
	List<TestVO> findByName(String name);
	
	// 이름(name) 으로 유사검색
	List<TestVO> findByNameLike(String name);
	
	
	
	
}
