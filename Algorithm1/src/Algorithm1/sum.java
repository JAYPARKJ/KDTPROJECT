package Algorithm1;

import java.util.Scanner;

public class sum {

	public static void main(String[] args) {
		 
		/*
		 * 숫자의 합
		시간  
		1 초	 
		문제
		N개의 숫자가 공백 없이 쓰여있다. 이 숫자를 모두 합해서 출력하는 프로그램을 작성하시오.
		입력
		첫째 줄에 숫자의 개수 N (1 ≤ N ≤ 100)이 주어진다. 둘째 줄에 숫자 N개가 공백없이 주어진다.
		출력
		입력으로 주어진 숫자 N개의 합을 출력한다.
		
		예제 입력 1 
		1
		1
		예제 출력 1 
		1
		
		예제 입력 2 
		5
		54321
		예제 출력 2 
		15
		
		예제 입력 3 
		25
		7000000000000000000000000
		예제 출력 3 
		7
		
		예제 입력 4 
		11
		10987654321
		예제 출력 4 
		46*/
		/*
		 1.제일 핵심적인것 N =100이다. 즉 자리수가 100자리까지인데, 이것은 int나 long으로 받을 수 없다
		 2. 숫자 대신 문자열로 생각한다 String
		 3. 문자열이 배열의 각 자리에 들어가며 이것을 각 자리로 나눌 때는 tocharArray를 사용한다.(char[]형 변환)
		 4. 문자와 숫자의 아스키 코드 차이는 48이다 예) '1' - 48 = 1로 떨어짐 -> char 값을 int형변환 후 sum 변수에 더해준다.
		 
		 슈도코드 작성
		 1. N값 입력받기
		 2. 길이 N의 숫자를 입력받나 String형 변수 sNum에 저장
		 3. sNum을 다시 char []형 변수 cNum에 변환하여 저장하기
		 4. int형 변수 sum 선언하기
		 5. for(cNum 길이만큼 반복하기)
		 	{ 배열의 각 자릿값을 정수형으로 변환하여 sum에 더하여 누적
		 	}
         6. sum 출력
         		 	
		  
		  */
		 System.out.print("Hello \nWorld"); 
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		String sNum = sc.next();
		char[] cNum = sNum.toCharArray();
		int sum = 0;
		for( int i =0; i<cNum.length; i++)
		{ 
			sum += cNum[i] -'0';
		}
		System.out.println(sum);
		
	}

}
