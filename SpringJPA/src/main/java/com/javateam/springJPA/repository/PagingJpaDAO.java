package com.javateam.springJPA.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.javateam.springJPA.domain.DemoVO;

public interface PagingJpaDAO extends PagingAndSortingRepository<DemoVO, Integer> {
 
	// 퍼시스턴스 계층 : 모든 DB 관련 로직 처리, 이 과정에서 DB에 접근하는 DAO객체를 사용
	// DAO는 DB계층과 상호작용하기 위한 객체 
	// 인터페이스 명세 
	/*
	 PagingJpaDAO 인터페이스에 추상 메서드를 선언하는 코드입니다. 
	 이 메서드는 id를 사용하여 DemoVO 객체를 조회하는 기능을 제공한다
	 */
	// Iterable 왜 쓰냐? 여러가지 치환가능 -> 다형성에 입각, LIST로 사용해도 상관없음
	// 이전에 사용한 PAGING 
    Iterable<DemoVO> findAll(Sort sort);
   
    Page<DemoVO> findAll(Pageable pageable);
   
    DemoVO findById(int id);
   
} 