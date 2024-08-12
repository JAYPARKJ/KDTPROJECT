-- 전체 회원 조회
SELECT * FROM MEMBER_TBL;

SELECT *  
FROM (SELECT m.*, rownum rowNo
      FROM (
             SELECT *
			 FROM member_tbl
           ) m  
      )      
where rowNo = 1;  


SELECT *  
FROM (SELECT m.*, rownum rowNo
      FROM (
             SELECT *
			 FROM member_tbl
           ) m  
      )      
where rowNo = 100;  
