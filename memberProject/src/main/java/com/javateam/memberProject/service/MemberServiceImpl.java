package com.javateam.memberProject.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.javateam.memberProject.domain.MemberVO;
import com.javateam.memberProject.repository.MemberDAO;


import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
	
	/*
    @Service: 이 클래스가 서비스 컴포넌트임을 나타냅니다.
	@Slf4j: Lombok을 사용하여 로그 기능을 제공하는 애너테이션입니다.
	txTemplate: TransactionTemplate 객체를 주입받아 트랜잭션 관리를 간편하게 수행합니다.
	dsTxManager: PlatformTransactionManager 인터페이스를 구현한 트랜잭션 매니저를 주입받습니다. 주석 처리된 부분과 대안 코드에서는 DataSourceTransactionManager를 사용할 수도 있습니다.
	memberDAO: 데이터베이스 접근을 위한 DAO 객체입니다.
	@Transactional(readOnly = true): 읽기 전용 트랜잭션을 설정하여 데이터베이스 조회 시 성능 최적화 및 잠금 문제를 최소화합니다.
	 
	 */
	@Autowired
	private TransactionTemplate txTemplate;
	// howto-1)
	@Autowired
	// DataSourceTransactionManager dsTxManager; 
	PlatformTransactionManager dsTxManager;
	
	// howto-2)
//	private final PlatformTransactionManager dsTxManager;
//	
//	public MemberServiceImpl(PlatformTransactionManager dsTxManager) {
//		this.dsTxManager = dsTxManager;
//	}
	
	// 1번 selectMemberById()
	@Autowired
	MemberDAO memberDAO;

	// selectMemberById: 주어진 id로 회원 정보를 조회하여 반환합니다.
	@Transactional(readOnly = true)
	@Override
	public MemberVO selectMemberById(String id) {
		
		return memberDAO.selectMemberById(id);
	}

	// 2번 selectMembersByPaging() 읽기 전용 트랜잭션을 사용하여 회원 목록을 조회합니다.
	@Transactional(readOnly = true)
	@Override
	public List<MemberVO> selectMembersByPaging(int page, int limit) {
		
		return memberDAO.selectMembersByPaging(page, limit);
	}

	// 3번 selectAllMembers() 읽기 전용 트랜잭션을 사용하여 회원 목록을 조회합니다.
	@Transactional(readOnly = true)
	@Override
	public List<MemberVO> selectAllMembers() {
		
		return memberDAO.selectAllMembers();
	}
	
	// 4번 
	@Override
	public boolean insertMember(MemberVO memberVO) {

		/*
		 txTemplate.execute: 트랜잭션을 관리하는 TransactionTemplate의 execute 메서드를 
		 사용하여 트랜잭션 내에서 코드를 실행합니다.
		 status.setRollbackOnly(): 예외가 발생하면 롤백 상태로 설정하여 트랜잭션을 롤백합니다.
		 회원을 등록한 후, 롤(Role)을 추가합니다. 두 단계 모두 성공해야 최종적으로 true를 반환합니다.
		  */
		return txTemplate.execute(status -> {
				boolean result = false;
				// insertMember => 리턴값이 있음(boolean이냐 아니냐)
				// 회원정보 생성
				try {
					// 기존 회원 존재 여부 24.08.06 추가
					// if (memberDAO.selectMemberById(memberVO.getId()) != null) {
					if (memberDAO.hasMemberByFld("ID", memberVO.getId()) == true) {
						throw new Exception("중복되는 회원정보가 존재합니다.");
					}
					result = memberDAO.insertMember(memberVO);
					// result = memberDAO.insertRole(memberVO.getId(), "ROLE_USER");
				} catch (Exception e) {
					result = false;
					log.error("MemberService.insertMember 에러 : " + e);
					status.setRollbackOnly();
				}
				// 회원 롤(Role) 생성
				try {
					// 기존 회원 존재 여부
					// if (memberDAO.selectMemberById(memberVO.getId()) != null) {
					
					// 만약 중복되는 회원정보 존재 시 ~
					if (memberDAO.hasMemberByFld("ID", memberVO.getId()) == false) {
						throw new Exception("회원정보가 존재하지 않습니다.");
					}
					result = memberDAO.insertRole(memberVO.getId(), "ROLE_USER");
				} catch (Exception e) {
					result = false;
					log.error("MemberService.insertMember(Role) 에러 : " + e);
					status.setRollbackOnly();
				}
				return result;
			}
		);
	}
	/*
	@Override
	public boolean insertMember(MemberVO memberVO) {


		return txTemplate.execute(new TransactionCallback<Boolean>() {


			@Override
			public Boolean doInTransaction(TransactionStatus status) {


				boolean result = false;
				
				try {
					result = memberDAO.insertMember(memberVO);
				} catch (Exception e) {
					result = false;
					log.error("MemberService.insertMember 에러 : " + e);
					status.setRollbackOnly();
				}
				return result;
			}
			
		});
	}
	*/
	/*
	@Override
	public boolean insertMember(MemberVO memberVO) {
		
		boolean result = false;
		TransactionStatus txStatus
			= dsTxManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			result = memberDAO.insertMember(memberVO);		
			result = memberDAO.insertRole(memberVO.getId(), "ROLE_USER");	
			dsTxManager.commit(txStatus);
			// result = true;
			
		} catch (Exception e) {
			
			dsTxManager.rollback(txStatus);
			result = false;
			log.error("MemberService.insertMember 에러 : " + e);
			// throw e;
		}
		
		// dsTxManager.commit(txStatus);
		 * return 이 있어서 execute함, 자바스크립트식으로 Callback함수와 같음, 함수 익명인터페이스형태로 람다로 쉽게 치환 
		 
		return result;
	} 
	*/
		 
	  
/*
  insertMember2 메서드는 insertMember와 유사하지만, 회원 정보를 삽입한 후 다시 조회하여 결과를 반환합니다
   */
  @Override
  public MemberVO insertMember2(MemberVO memberVO)
  {
	   return txTemplate.execute(new TransactionCallback<MemberVO>()
			   {
			@Override
			public MemberVO doInTransaction(TransactionStatus status) {
				//boolean result = false;
			  	MemberVO resultVO = null;

			  try { 
				  if(memberDAO.insertMember(memberVO) == true &&
			  		 memberDAO.insertRole(memberVO.getId(), "ROLE_USER") ==true)
			  		 {// 기존 회원 존재 여부
						// if (memberDAO.selectMemberById(memberVO.getId()) != null) {
		   				// 회원 정보 생성 이후 결과
					  if (memberDAO.hasMemberByFld("ID", memberVO.getId()) == true) {
							throw new Exception("중복되는 회원정보가 존재합니다.");
						}
					  resultVO = memberDAO.selectMemberById(memberVO.getId());
			  		 }
				  }
			  catch(Exception e){
				  resultVO = null;
				  log.error("MemberService.insertMember 에러 :" + e );
				  status.setRollbackOnly();
			  }
			  	return resultVO;
		 
			   }

			   });
  }
  /*
     회원 존재 여부 확인: 수정하려는 회원이 존재하는지 확인합니다.
	 회원 정보 수정: 존재하는 경우, 회원 정보를 업데이트합니다.
	 예외 처리 및 롤백: 예외가 발생하면 트랜잭션을 롤백하고 로그에 오류를 기록합니다. 
   */
// 가끔 final 걸어주는 이유는 memberVO 변경안시키겠다. final MemberVO memberVO
@Override
public boolean updateMember(MemberVO memberVO) 
	{

	// 익명클래스로 씀, without(은 void값으로 들어감), return 있을 경우 callback으로함 
	// 빨간줄이 있는경우 구현해야할 추상메서드가 있기 때문
	return txTemplate.execute(new TransactionCallback<Boolean>()
	{
		@Override
		public Boolean doInTransaction(TransactionStatus status) {
		boolean result = false;
		// 관리자가 업데이트, 롤을 업데이트 하게되면 별도로 호출 
		try 
		{	// 기존 회원 존재 여부 
			//if(memberDAO.selectMemberById(memberVO.getId()) == null)
			//if(memberDAO.hasMemberByFld(memberVO.getId(), null) == false)
			if(memberDAO.hasMemberByFld(memberVO.getId(), null) == true)
			{
			 throw new Exception("수정할 회원정보가 존재하지 않습니다.");
			}
			 result = memberDAO.updateMember(memberVO);
			}
		catch (Exception e)
		{	result = false;
			log.error("MemberService.updateMember 오류 : " + e);
			status.setRollbackOnly();
		}
	    return result;
		}
	});
	}

@Override
public boolean deleteMember(String id) 
	{

	// 익명클래스로 씀, without(은 void값으로 들어감), return 있을 경우 callback으로함 
	// 빨간줄이 있는경우 구현해야할 추상메서드가 있기 때문
	return txTemplate.execute(new TransactionCallback<Boolean>()
	{
		@Override
		public Boolean doInTransaction(TransactionStatus status) {
		boolean result = false;
		// 관리자가 업데이트, 롤을 업데이트 하게되면 별도로 호출 
		try 
		{	// 기존 회원 존재 여부 
			//if(memberDAO.selectMemberById(memberVO.getId()) == null)
			//if(memberDAO.hasMemberByFld(memberVO.getId(), null) == false)
			if(memberDAO.hasMemberByFld("ID", id) == false)
			{
			 throw new Exception("삭제할 회원정보가 존재하지 않습니다.");
			}
			if(memberDAO.deleteRoles(id) == true &&
			   memberDAO.deleteMemberById(id) == true) {
				result = true;}
			}
		catch (Exception e)
		{	result = false;
			log.error("MemberService.deleteMember 오류 : " + e);
			status.setRollbackOnly();
		}
	    return result;
		}
	});
	}
}
