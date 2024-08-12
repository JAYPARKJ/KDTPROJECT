package com.javateam.memberProject.repository;
/*
데이터 접근 객체(DAO : Data Access Object)
DAO에서는 SQL문을 구현한 XML파일에서의 해당 ID값을 호출하여 데이터 조회하거나 조작한다
  
*/
// hamcrest assertj 
import java.util.List;
import java.util.Map;

import com.javateam.memberProject.domain.MemberVO;

/*DAO : DB계층과 상호작용하기위한 객체 
 *리포지터리가 퍼시스턴스 계층 역할*/
public interface MemberDAO {
	/**
	 * 개별 회원정보 조회(검색)
	 * 
	 * @param id 회원 아이디
	 * @return 회원정보
	 */
	MemberVO selectMemberById(String id);
	
	/**
	 * 페이징에 의한(페이지 별로) 회원정보 조회(검색)
	 * 
	 * @param page 페이지
	 * @param limit 페이지당 회원정보 갯수
	 * @return 회원정보(들)
	 */
	List<MemberVO> selectMembersByPaging(int page, int limit);
	
	
	/**
	 * 전체 회원정보 조회
	 * 
	 * @return 전체 회원정보
	 */
	List<MemberVO> selectAllMembers();
	
	
	// 여기서 VO는 DTO역할, 원래는 database relation 결과값을 갖고 있는 불변객체 (setter가 있을 수없으나)
	/*
	 * 회원정보 삽입(생성)
	 * @param memberVO 회원정보
	 * return 값이 나오면 판정하기 좋다.
	 * @return 삽입된(생성된) 레코드 수 ex) 1(레코드 생성), 0(레코드 미생성)  
	 * */
	// 	void insertMember(MemberVO memberVO);
	// 판정시 int로 
	//int insertMember(MemberVO memberVO);
	boolean insertMember(MemberVO memberVO);
	//int insertMember(MemberVO memberVO);
	
	
	/*
	 * 회원 정보 수정(갱신)
	 *
	 *@param memberVO 회원정보
	 *@return 회원 정보 수정(갱신) 성공 여부
	 */
	boolean updateMember(MemberVO memberVO);
	// String으로 대체해도 됨
 		
	 /**회원 정보 중복 점검 (회원가입시)
	 * ex) 중복 점검 대상 : 아이디, 이메일, 휴대폰번호 
	 * @param fld : 중복점검 대상 필드 
	 * @param val : 중복점검 대상 필드 값
	 * @return 중복 여부 : ex)true 중복 X false 
	 * 
	 */
	// 순서 mapper-> DAO ->  구현 클래스
	  

	boolean hasMemberByFld(String fld, String val);
	  
	
	boolean hasMemberForUpdate(String id, String fld, String val);
		/* 회원 정보 검색(페이징) : 주소, 이름, 성별, 휴대폰, 일반전화, 이메일
		 * @param serachKey 검색 구분(종목) ex) ADDRESS(주소)
		 * @param searchWord 검색어 ex) 신림
		 * @param page 현재 페이지 ex)1
		 * @param limit 페이지당 레코드 수 ex)10
		 * @param isLikeOrEquals 동등/유사검색 여부 ex) like(유사), equals(동등)
		 * @param ordering 정렬 ex) ASC(오름차순), DESC(내림차순)
		 * @return (검색된) 회원정보
		 * 
		 */
 
	
		/*
		 * List<Map<String, Object>> selectMembersBySearchingAndPaging (String
		 * searchKey, String searchWord, int page, int limit, String isLikeOrEquals,
		 * String ordering);
		 */
	List<Map<String, Object>> selectMembersBySearchingAndPaging(
		    String searchKey,
			String searchWord, int page, int limit, 
			String isLikeOrEquals, String ordering);
		
	/***
	 * 개별 회원정보 조회(검색)-2 : 롤(role) 정보 포함
	 * 
	 * @param fld 필드 ex) 회원 아이디/이메일/휴대폰
	 * @param val 핃드값 ex) 'abcd1111'/abcd@abcd.com/010-1234-5678
	 * @return 회원 정보 	
	 */
	 Map<String, Object> selectMemberByFld(String fld, Object val);
	 /** 24.08.05
	  * 개별 회원 롤(Role) 생성
	  * @param id 회원 아이디
	  * @param role 롤 ex) ROLE_USER, ROLE_ADMIN
	  * @return 생성 성공여부
	  */
	 boolean insertRole(String id, String role);
	 /**
	  * 개별 회원 롤(Role) 삭제
	  * @param id 회원 아이디
	  * @return 삭제 성공 여부
	  */
	 boolean deleteRoles(String id);
	 /**
		 * 개별 회원정보 삭제
		 * 
		 * @param id 회원 아이디
		 * @return 삭제 성공 여부
		 */
	boolean deleteMemberById(String id);

	/**
	 * 개별 회원 정보 삭제
	 * : 회원 롤(role)/회원정보 동시 삭제
	 * @param id 회원 아이디
	 * @return 회원정보 삭제 성공 여부 
	 */
	boolean deleteMember(String id);
	
	
}
