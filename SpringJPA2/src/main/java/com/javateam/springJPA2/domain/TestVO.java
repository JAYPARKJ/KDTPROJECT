package com.javateam.springJPA2.domain;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/*
  : VO 간의 멤버 필드들을 한꺼번에 비교하기 위해서는 hashCode, equals 의 
  오버라이딩 메소드를 사용할 수 있는데 lombok에서는 @Data, @EqualsAndHashCode 
  애너테이션 등으로 생성할 수 있습니다. 그러나 전체 멤버 필드들이 아닌, 
  일부 멤버 필드들끼리만 비교하기 위해서는 수동으로 오버라이딩할 것을 권장드립니다.
   */
// 에러 1: SQL DEVELOPER에서 데이터가 안들어간 이유는 : MEBER-> MEMBER로 바꿔줌 
 
//hibernate 6.2.x 이상에서는 "HTE_"로 시작하는 이름의 임시(temp) 테이블 같이 생성됨
//https://hibernate.atlassian.net/browse/HHH-17628 
//ex) HTE_DEMO_TBL -> 삭제하슈

/* ctrl + space 
 * 롬복 사용법
 @NoArgsConstructor // 롬복식 기본 생성자
 @AllArgsConstructor // 모든 필드의 인자를 대입한 오버로딩 생성자 , 인자 인식이 안되네 -> ctrl + space하면됨
 @RequiredArgsConstructor 필수 인자를 대입하는 오버로딩 생성자
 @NonNull 오버로딩 생성자 사용하려면 이거를 작성하면됨 
 @Builder AllArgsConstructor 기반으로함 , 4개짜리 인자임
* 기본 생성자를 쓰려면 명시적으로 생성자 정의 해줘야함
* public class TestVO {
	
// 테이블 자동생성 개념은 현업적이지 않다.
 // 여기서 this는 Source-> Generate Constructor using Fields로 생성
/*
 * public TestVO( String name, String address, int age) {  
 *    this.name = name; this.address = address; this.age = age; }
 */
 
@Entity
@Table(name = "TEST_TBL")
@SequenceGenerator(	name = "TEST_TBL_Generator",
					sequenceName = "TEST_TBL_SEQ",
					initialValue = 1,
					allocationSize = 1 )
@Data 
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TestVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "TEST_TBL_Generator")
	@Column(name="MEMBER_ID") // number오라클은 0부터 시작 
	//@Column(name="MEMBER_ID"), precision = 10, scale =0 
	// 하고 나서 properites에 create-drop해놓음 -> 자동화 권하지 않으니 왠만하면 none 해놔라~~!
	private long id;
	
	// @Nullable()  nullable false값이면 값 나옴 
	@NonNull
	@Column(name="MEMBER_NAME", nullable = false, length = 20)
	private String name;
	
	// @Nullable()
	@NonNull
	@Column(name="MEMBER_ADDRESS" , nullable = true, length = 20)
	private  String address;
	
	// 24.08.01
	// field 추가
	@NonNull
	@Column(name="MEMBER_AGE", nullable = true  )
	//private Integer age;
	private Integer age;
	 
	
	
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestVO other = (TestVO) obj;
		return Objects.equals(address, other.address) && Objects.equals(age, other.age)
				&& Objects.equals(name, other.name);
	}

    @Override
	public int hashCode() {
		return Objects.hash(address, age, name);
	}
 
}
