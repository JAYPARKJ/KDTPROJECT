package com.javateam.springJPA.service;
// 서비스 클래스에서는 DAO에서 정의된 메서드를 호출하고, 
// 결과를 비즈니스 로직에 맞게 변환하거나 추가 처리를 수행합니다.
/*
이 클래스는 DAO 인터페이스 (PagingJpaDAO)에서 정의된 추상 메서드를 구현하고 
실제 비즈니스 로직을 수행합니다. 예를 들어, findById 메서드는 
PagingJpaDAO에서 정의된 findById 메서드를 호출하여 결과를 반환합니다.
 이 부분에서 실제로 데이터베이스와 상호작용하며 비즈니스 로직을 수행하는 것이 서비스 클래스입니다. 
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.springJPA.domain.DemoVO;
import com.javateam.springJPA.repository.PagingJpaDAO;

@Service
@Transactional
public class PagingJpaService {
	
	@Autowired
	// 원래는 구현클래스의 생성자가 들어가서 나와줘야 되는게 맞음
	// PagingJpaDAO dao = new ~ 
	// 그러나 추상함수인데도 Spring JPA가 만들어줌
	//PagingJpaService는 DAO의 findById 메서드를 호출하여 실제 비즈니스 로직을 구현합니다.
	PagingJpaDAO dao;

    public List<DemoVO> findAll(String sort) {
    	
    	Direction direction = sort.equals("오름차순") ?  Direction.ASC : Direction.DESC;
    	
    	return (List<DemoVO>) dao.findAll(Sort.by(new Order(direction, "name")));
    }
    
    public Page<DemoVO> findAllByPaging(int page, int limit) {
    	
    	Pageable pageable = PageRequest.of(page-1, limit);

    	return dao.findAll(pageable);
    }
   
    // 주어진 ID로 DemoVO를 조회하는 메서드
    public DemoVO findById(int id) {
    	
    	// DAO의 메서드를 호출하여 결과 반환
    	return dao.findById(id);
    }
    
}