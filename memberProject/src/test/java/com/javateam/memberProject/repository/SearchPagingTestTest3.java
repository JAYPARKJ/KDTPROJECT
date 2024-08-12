
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
package com.javateam.memberProject.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SearchPagingTestTest3 {
	
	
			// case-3 성별 검색  회원정보 검색
			// case-3-2) Null 여부 점검
			// case-3-3) 페이지당 레코드 길이 => 첫 페이지, 마지막 페이지 길이 특정값 판단 
			// case-3-4) ID 정렬시 => 처음/마지막 레코드 이름 비교 
	@Autowired
	MemberDAO memberDAO;
	List<Map<String, Object>> members;
	String searchKey;
	String searchWord;
	String isLikeOrEquals;
	int page;
	int limit;
	String ordering;
	
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
	 

	private void assertThat(String string, Matcher<Object> equalTo) {
		// TODO Auto-generated method stub
		
	}

}

 
 

	  
	     
 