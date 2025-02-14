package com.javateam.memberProject.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.javateam.memberProject.domain.MemberVO;

// import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Repository
// @Slf4j
// @Log4j2
public class MemberDAOImpl implements MemberDAO {
	
	// 로그 선언 방법
	private static final Logger log
		= LoggerFactory.getLogger(MemberDAOImpl.class);


	@Autowired
	SqlSession sqlSession; // MyBatis Session 객체

	
	  @Override public MemberVO selectMemberById(String id) { return
	  sqlSession.selectOne("mapper.MemberMapper.selectMemberById", id); }
	
	  @Override
		public List<MemberVO> selectMembersByPaging(int page, int limit) {

			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("page", page);
			hashMap.put("limit", limit);
			
			log.info("page : "+ hashMap.get("page"));
			log.info("limit : "+ hashMap.get("limit"));
			
			return sqlSession.selectList("mapper.MemberMapper.selectMembersByPaging", hashMap);
		}

	  @Override public List<MemberVO> selectAllMembers() { return
	  sqlSession.selectList("mapper.MemberMapper.selectAllMembers"); }
	 
	  
	  @Override
		public List<Map<String, Object>> selectMembersBySearchingAndPaging(String searchKey, String searchWord, int page,
				int limit, String isLikeOrEquals, String  ordering) {
		 
				Map<String , Object> map = new HashMap<>();
				map.put("searchKey", searchKey);
				map.put("searchWord", searchWord);
				map.put("page", page);
				map.put("limit", limit);
				map.put("isLikeOrEquals", isLikeOrEquals);
				map.put("ordering", ordering);
				
				return sqlSession
					   .selectList("mapper.MemberMapper.selectMembersBySearchingAndPaging"
							      ,map);
		}

	  @Override
		// public void insertMember(MemberVO memberVO) {
		// public int insertMember(MemberVO memberVO) {
		public boolean insertMember(MemberVO memberVO) {


			// return sqlSession.insert("mapper.MemberMapper.insertMember", memberVO);
			// return sqlSession.insert("mapper.MemberMapper.insertMember", memberVO) == 1 ? true : false;
			boolean returnValue = false;
			
			try {
				
				sqlSession.insert("mapper.MemberMapper.insertMember", memberVO);
				returnValue = true;
				
			} catch (Exception e) {
				log.error("insertMember 오류 : " + e);
				e.printStackTrace();
				returnValue = false;
			}
			
			return returnValue;
		}

	  
	  
	  
	  @Override
		public boolean updateMember(MemberVO memberVO) {
			
			boolean returnValue = false;
			
			try {
				// 기존 회원정보 존재 여부 점검
				int result 
					= this.selectMemberById(memberVO.getId()) != null ? 1 : 0;
				
				if (result == 0) { // 수정할 회원정보가 없음(부재시)
					// log.error("회원정보가 존재하지 않습니다.");
					// returnValue = false;
					throw new Exception("회원정보가 존재하지 않습니다.");
				} 
				
				sqlSession.update("mapper.MemberMapper.updateMember", memberVO);
				
				// else {
				//	returnValue = true;
				// }
				
				returnValue = true;
				
			} catch (Exception e) {
				log.error("updateMember 오류 : {}", e);
				e.printStackTrace();
				returnValue = false;
			}

			return returnValue;
		}

		@Override
		public boolean hasMemberByFld(String fld, String val) {

			// 인자가 2개정도
			Map<String, String> map = new HashMap<>();
			map.put("fld", fld);
			map.put("val", val);
 			// 삼항 연산자 추가 , int 형변환 return (int)sqlSession
			return sqlSession
					.selectOne("mapper.MemberMapper.hasMemberByFld", map);
		}

	 
	 
	

		@Override
		public boolean hasMemberForUpdate(String id, String fld, String val) {


			Map<String, String> map = new HashMap<>();
			map.put("id", id);
			map.put("fld", fld);
			map.put("val", val);
			return (int)sqlSession
					.selectOne("mapper.MemberMapper.hasMemberForUpdate", map) 
					== 1 ? true : false;
		}


	 
	 
	@Override
	public Map<String, Object> selectMemberByFld(String fld, Object val) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("fld", fld);
		map.put("val", val);
		
		return sqlSession.selectOne("mapper.MemberMapper.selectMemberByFld",map);
		
	}
	
	// 집가서 메서드 이거 다시 보고 작성하기 
 

 
	@Override
	public boolean insertRole(String id, String role) {
		 boolean returnValue = false;
		 
		 try {
			 Map<String, String>map = new HashMap<>();
			 map.put("id", id);
			 map.put("role", role);
			 
			 sqlSession.insert("mapper.MemberMapper.insertRole", map);
			 returnValue = true;
		 } catch(Exception e){
			 log.error("insertMember 오류 : " + e );
			 e.printStackTrace();
			 returnValue = true;
		 }
		return returnValue;
	}

	
	@Override
	public boolean deleteRoles(String id) {
		
		boolean returnValue = false;
		try {
			sqlSession.delete("mapper.MemberMapper.deleteRoles", id);
			returnValue = true;
		}
		catch (Exception e) {
			log.error("deleteMemberById  오류 :  "+ e);
			e.printStackTrace();
			returnValue = false;
		}
		return returnValue;
	}

	@Override
	public boolean deleteMemberById(String id) {

		boolean returnValue = false;

		try {
			
			sqlSession.delete("mapper.MemberMapper.deleteMemberById", id);
			returnValue = true;
			
		} catch (Exception e) {
			log.error("deleteMemberById 오류 : " + e);
			e.printStackTrace();
			returnValue = false;
		}
		
		return returnValue;
	}

	@Override
	public boolean deleteMember(String id) {
		
		boolean returnValue = false;
		return returnValue;
	}
	
	}
		 
 
