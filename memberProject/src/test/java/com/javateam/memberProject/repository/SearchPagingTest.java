package com.javateam.memberProject.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j
class SearchPagingTest {
	@Autowired
	MemberDAO memberDAO;
	List<Map<String, Object>> members;
	String searchKey;
	String searchWord;
	String isLikeOrEquals;
	int page;
	int limit;
	String ordering;


	// case-1) 주소 검색 : "신림" 이라는 검색어로 회원정보 검색
	// case-1-1) Null 여부 점검
	// case-1-2) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지
	// case-1-3) ID 정렬시(오름차순) => 처음/마지막 레코드 이름 비교 
	// case-1-4) ID 정렬시(내림차순) => 처음/마지막 레코드 이름 비교
	@Test
	void testSelectMembersBySearchingAndPaging() {
		searchKey = "ADDRESS";
		searchWord = "신림";
		isLikeOrEquals = "like";
		page = 1;
		limit = 10;
		ordering = "ASC";
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
	    // case-1-1) Null 여부 점검
		assertNotNull(members);
		// case-1-2) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지
		assertEquals(10, members.size());
		page = 3; // 마지막 페이지
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		assertEquals(6, members.size());
	}
	
	// case-1-3) ID 정렬시(오름차순) => 처음/마지막 레코드 이름 비교
	@Test
	void testSelectMemberBySearchingAndPagingAsc() {
		// sqldeveloper에 페이지 확인해보기, 데이터는 강사님과 다름
		searchKey = "ADDRESS";
		searchWord = "신림";
		isLikeOrEquals = "like";
		page = 3;
		limit = 10;
		ordering = "ASC";
		
		members = memberDAO.selectMembersBySearchingAndPaging(searchKey, searchWord, page, limit, isLikeOrEquals, ordering);
	
		// case-1-1) Null 여부 점검
		assertNotNull(members);
		// 3페이지(마지막 페이지)의 첫/마지막 레코드 이름 :황순수 , 구운석
		// 마지막 페이지 
		page = 3; 
		members = memberDAO.selectMembersBySearchingAndPaging(searchKey, searchWord, page, limit, isLikeOrEquals, ordering);
				assertEquals(8, members.size());}
	
	

/*
 * sqldevloper에 검색해보기 
SELECT COUNT(*) 
  FROM MEMBER_TBL
 WHERE ROAD_ADDRESS LIKE '%신림%';

SELECT COUNT(*) 
  FROM MEMBER_TBL
 WHERE ROAD_ADDRESS LIKE '%신림%';
-- 페이징 
SELECT  *
  FROM ( SELECT m.*  
                , FLOOR((ROWNUM - 1) / 10 + 1) PAGE  
           FROM ( SELECT DISTINCT m3.* 
                         , ( SELECT LISTAGG(r2.ROLE, ',') 
                                      WITHIN GROUP (ORDER BY m2.ID) 
                               FROM MEMBER_TBL m2, USER_ROLES r2  
                              WHERE r2.USERNAME = m2.ID
                                AND r2.USERNAME = m3.ID ) AS "ROLE"           
                    FROM MEMBER_TBL m3, USER_ROLES r3
                   WHERE m3.ID = r3.USERNAME
			         AND ROAD_ADDRESS LIKE '%신림%'
                   ORDER BY ID ASC ) m
       )  
 WHERE PAGE = 3;
 
 SELECT COUNT(*) 
  FROM MEMBER_TBL
 WHERE GENDER = 'm';
 
 SELECT COUNT(*) 
  FROM MEMBER_TBL
 WHERE GENDER = 'f';
 
 -- 여기 ROLE을 사용하고 있어서 회원테이블과 최소한 일치해야함
 SELECT  *
  FROM ( SELECT m.*  
                , FLOOR((ROWNUM - 1) / 10 + 1) PAGE  
           FROM ( SELECT DISTINCT m3.* 
                         , ( SELECT LISTAGG(r2.ROLE, ',') 
                                      WITHIN GROUP (ORDER BY m2.ID) 
                               FROM MEMBER_TBL m2, USER_ROLES r2  
                              WHERE r2.USERNAME = m2.ID
                                AND r2.USERNAME = m3.ID ) AS "ROLE"           
                    FROM MEMBER_TBL m3, USER_ROLES r3
                   WHERE m3.ID = r3.USERNAME
			         AND GENDER = 'f'
                   ORDER BY ID DESC ) m
       )  
 WHERE PAGE = 5;
 
  SELECT  *
  FROM ( SELECT m.*  
                , FLOOR((ROWNUM - 1) / 10 + 1) PAGE  
           FROM ( SELECT DISTINCT m3.* 
                         , ( SELECT LISTAGG(r2.ROLE, ',') 
                                      WITHIN GROUP (ORDER BY m2.ID) 
                               FROM MEMBER_TBL m2, USER_ROLES r2  
                              WHERE r2.USERNAME = m2.ID
                                AND r2.USERNAME = m3.ID ) AS "ROLE"           
                    FROM MEMBER_TBL m3, USER_ROLES r3
                   WHERE m3.ID = r3.USERNAME
			         AND ROAD_ADDRESS LIKE '신림'
                   ORDER BY ID DESC ) m
       )  
 WHERE PAGE = 1;
 * 
 * */

	
	
	
	@Test
	void testTestSelectMembersBySearchingAndPagingAsc() {

		searchKey = "GENDER";
		searchWord = "m";
		isLikeOrEquals = "equals";
		page = 1;
		limit = 10;
		ordering = "ASC";
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		assertThat("남궁나제", equalTo(members.get(0).get("NAME")));
		assertThat("유나순", equalTo(members.get(10).get("NAME")));
				
				
	}
	
	@Test
	void testTestSelectMembersBySearchingAndPagingDesc() {

		searchKey = "GENDER";
		searchWord = "m";
		isLikeOrEquals = "equals";
		page = 5;
		limit = 10;
		ordering = "DESC";
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		assertThat("도순주", equalTo(members.get(0).get("NAME")));
		assertThat("전정철", equalTo(members.get(10).get("NAME")));
				
				
	}
	@Test
	void TestSelectMembersBySearchingAndPagingAsc() {

		searchKey = "GENDER";
		searchWord = "f";
		isLikeOrEquals = "equals";
		page = 5; // 마지막 페이지  
		limit = 10;
		ordering = "ASC";
		  
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		// 2페이지(마지막 페이지)의 첫/마지막 레코드 이름 : 한갑석/소갑정
		assertThat("서문운훈", equalTo(members.get(0).get("NAME")));
		assertThat("허운철", equalTo(members.get(10).get("NAME")));
				
				
	}
	@Test
	void TestSelectMembersBySearchingAndPagingDesc() {

		searchKey = "GENDER";
		searchWord = "f";
		isLikeOrEquals = "equals";
		page = 1; // 마지막 페이지  
		limit = 10;
		ordering = "DESC";
		members = memberDAO
				.selectMembersBySearchingAndPaging(searchKey, 
						searchWord, page, limit, isLikeOrEquals, ordering);
		
		// 2페이지(마지막 페이지)의 첫/마지막 레코드 이름 : 한갑석/소갑정
					assertThat("한갑석", equalTo(members.get(0).get("NAME")));
					assertThat("소갑정", equalTo(members.get(10).get("NAME")));
				
	}
	 
 
	  
	     
 
	}
	
	/*
	 * @SpringBootTest
	 * 
	 * @Slf4j
	 * 
	 * class SearchPagingTest {
	 * 
	 * @Autowired MemberDAO memberDAO;
	 * 
	 * List<Map<String, Object>> members; // 객체 앞으로 빼기 String searchKey; String
	 * searchWord; String isLikeOrEquals; int page; int limit; String ordering; //
	 * case-1) 주소 검색 : "신림" 이라는 검색어로 회원정보 검색 // case-1-2) Null 여부 점검 // case-1-3)
	 * 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지 길이 특정값 판단 // case-1-4) ID 정렬시 => 처음/마지막 레코드 이름
	 * 비교
	 * 
	 * @Test void testSelectMembersBySearchingAndPaging() {
	 * 
	 * // 이건 mapper에 들어가 있기에 필요 없으나 구색 마쳐주기
	 * 
	 * searchKey = "ADDRESS"; searchWord = "신림"; isLikeOrEquals = "like"; page = 1;
	 * limit = 10; ordering = "ASC";
	 * 
	 * members = memberDAO.selectMembersBySearchingAndPaging(searchKey, searchWord,
	 * page, limit, isLikeOrEquals, ordering);
	 * 
	 * // not null인지 테스트 해보기 // case-1-1) null 여부 점검 assertNotNull(members);
	 * 
	 * // case-1-2) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지 assertEquals(10, members.size());
	 * 
	 * page = 3; // 마지막 페이지 members =
	 * memberDAO.selectMembersBySearchingAndPaging(searchKey, searchWord, page,
	 * limit, isLikeOrEquals, ordering);
	 * 
	 * 
	 * assertEquals(8, members.size()); } // case-1-3) ID 정렬시 (오름차순) => 처음/마지막 레코드
	 * 이름 비교
	 * 
	 * @Test void testSelectMembersBySearchingAndPagingAsc() {
	 * 
	 * searchKey = "ADDRESS"; searchWord = "신림"; isLikeOrEquals = "like"; page = 3;
	 * // 3페이지(마지막 페이지) limit = 10; ordering = "ASC"; members = memberDAO
	 * .selectMembersBySearchingAndPaging(searchKey, searchWord, page, limit,
	 * isLikeOrEquals, ordering);
	 * 
	 * // 3페이지(마지막 페이지)의 첫/마지막 레코드 이름 : 오선훈, 도희순 assertThat("오선훈",
	 * equalTo(members.get(0).get("NAME"))); assertThat("도희순",
	 * equalTo(members.get(7).get("NAME"))); }
	 * 
	 * 
	 * // case1-4) 내림 차순
	 * 
	 * @Test void testselectMembersBySearchingAndPagingDesc() { String searchKey =
	 * "ADDRESS"; String searchWord ="신림"; String isLikeOrEquals= "like"; page = 1;
	 * // 1 페이지(첫 페이지) limit = 10; String Ordering = "DESC";
	 * 
	 * members = memberDAO .selectMembersBySearchingAndPaging(searchKey, searchWord,
	 * page, limit, isLikeOrEquals, Ordering);
	 * 
	 * // 1페이지의 첫 /마지막 레코드 이름 : 도희순/ 구순재 assertThat("도희순",
	 * equalTo(members.get(0).get("NAME")));
	 * 
	 * assertThat("구순재", equalTo(members.get(9).get("NAME")));
	 * 
	 * 
	 * } private void assertThat(String string, Matcher<Object> equalTo) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */
 
	
		// case-1) 이름 검색 : "훈" 이라는 검색어로 회원정보 검색
		// case-1-1) Null 여부 점검
		// case-1-2) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지 길이 특정값 판단 
		// case-1-3) ID 정렬시 => 처음/마지막 레코드 이름 비교 
		/*
		 * @Test void testSelectMembersBySearchingAndPaging1() {
		 * 
		 * // 이건 mapper에 들어가 있기에 필요 없으나 구색 마쳐주기
		 * 
		 * searchKey = "NAME"; searchWord = "훈"; isLikeOrEquals = "like"; page = 1;
		 * limit = 5; ordering = "ASC";
		 * 
		 * members = memberDAO.selectMembersBySearchingAndPaging(searchKey, searchWord,
		 * page, limit, isLikeOrEquals, ordering);
		 * 
		 * // not null인지 테스트 해보기 // case-1-1) null 여부 점검 assertNotNull(members);
		 * 
		 * // case-1-2) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지 assertEquals(4, members.size());
		 * 
		 * page = 2; // 마지막 페이지 members =
		 * memberDAO.selectMembersBySearchingAndPaging(searchKey, searchWord, page,
		 * limit, isLikeOrEquals, ordering);
		 * 
		 * 
		 * assertEquals(4, members.size()); }
		 */
	
	
	
 
