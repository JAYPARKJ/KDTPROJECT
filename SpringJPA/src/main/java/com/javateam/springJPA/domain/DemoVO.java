package com.javateam.springJPA.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Data;
 
// Entity manager가 Entitiy 참고하여 자동완성 수행  
@Entity 
@Table(name="demo_tbl") 
// hibernate 6.2.x 이상에서는 "HTE_"로 시작하는 이름의 임시(temp) 테이블 같이 생성됨 
// https://hibernate.atlassian.net/browse/HHH-17628
// ex) HTE_DEMO_TBL
@SequenceGenerator( 
	     name = "USER_SEQ_GENERATOR",  
	     sequenceName = "USER_SEQ",  // DB sequence(시퀀스) 이름
	     initialValue = 1, 			 // nextval 초기값
	     allocationSize = 1)		 // allocationSize : nextval 증가폭
@Data
public class DemoVO {
 
	// 자동생성이 조금 언급한대로 필드 매겨주는건 좋지만 제대로 잘 안된경우도 있어서 권하진 않음
    @Id		// nullable = not null  
    @Column(nullable=false, precision=4, scale=0) // number(4,0)
    // @GeneratedValue // hibernate_sequence.nextval
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
    			    generator = "USER_SEQ_GENERATOR")
    private int id;
   
    @Column(nullable=false, length=40)
    private String name;
 
}
 
  