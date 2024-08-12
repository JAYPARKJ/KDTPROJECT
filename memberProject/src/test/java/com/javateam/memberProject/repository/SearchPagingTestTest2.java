package com.javateam.memberProject.repository;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
class SearchPagingTest2 {
	
	@Autowired
	MemberDAO memberDAO;
	
	List<Map<String, Object>> members;
	
	String searchKey;
	String searchWord;
	String isLikeOrEquals;
	int page;
	int limit;
	String ordering;


	// case-1) 이름 검색 : "훈" 이라는 검색어로 회원정보 검색
	// case-1-1) Null 여부 점검
	// case-1-2) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지
	// case-1-3) ID 정렬시(오름차순) => 처음/마지막 레코드 이름 비교 
	// case-1-4) ID 정렬시(내림차순) => 처음/마지막 레코드 이름 비교
	@Test
	void testSelectMembersBySearchingAndPaging() {
		
		searchKey = "NAME";
		searchWord = "훈";
		isLikeOrEquals = "like";
		page = 1;
		limit = 5;
		ordering = "ASC";
		
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
	    // case-1-1) Null 여부 점검
		assertNotNull(members);
		
		// case-1-2) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지
		assertEquals(5, members.size());
		
		page = 2; // 마지막 페이지
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		
		assertEquals(4, members.size());
	}
	
	// case-1-3) ID 정렬시(오름차순) => 처음/마지막 레코드 이름 비교
	@Test
	void testSelectMembersBySearchingAndPagingAsc() {
		
		searchKey = "NAME";
		searchWord = "훈";
		isLikeOrEquals = "like"; 
		page = 2; // 마지막 페이지
		limit = 5;
		ordering = "ASC";
		
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		
		// 2페이지(마지막 페이지)의 첫/마지막 레코드 이름 : 오선훈, 곽나훈
		assertThat("오선훈", equalTo(members.get(0).get("NAME")));
		assertThat("곽나훈", equalTo(members.get(3).get("NAME")));
	}
	
	// case-1-4) ID 정렬시(내림차순) => 처음/마지막 레코드 이름 비교
	@Test
	void testSelectMembersBySearchingAndPagingDesc() {
		
		searchKey = "NAME";
		searchWord = "훈";
		isLikeOrEquals = "like";
		page = 1; // 첫 페이지
		limit = 5;
		ordering = "DESC";
		
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		
		// 1페이지(첫 페이지)의 첫/마지막 레코드 이름 : 곽나훈, 장남훈
		assertThat("곽나훈", equalTo(members.get(0).get("NAME")));
		assertThat("장남훈", equalTo(members.get(4).get("NAME")));
	}
	
}