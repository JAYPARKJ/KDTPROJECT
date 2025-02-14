package Algorithm1;

import java.util.Scanner;

public class avg {

	public static void main(String[] args) {

		/*
		   
		    슈도 코드 작성
		    
		    변수 N에 과목의 수 입력받기
		    1. 길이가 N인 1차원 배열 A[]선언
		    2. for(A[] 길이 만큼 반복하기)
		    	{A[i]에 각 점수 저장하기}
	   		3.for(A[] 길이만큼 반복하기){
	   			최고점은 변수 max,총점은 변수 sum에 저장하기
	   			}
	   		4.sum*100/max /N 출력
	   		
	     
	   */
		Scanner sc = new Scanner(System.in);
       
		//  1. 과목 수를 입력받는다.
		int N = sc.nextInt();		//  2.점수를 저장할 배열
		int A[] = new int[N];
		 
		/*
		 * // 데이터를 받을 배열 ->  밑에서 그냥 temp로 변환 
		 * for(int i = 0; i < N; i++)
		 *  { A[i] = sc.nextInt(); }
		 */
		// 3. 최고 점수와 총 점수를 계산한다.
			long sum = 0;
			long max = 0;
		// 3. 길이만큼 반복	
	   for(int i =0; i < N; i++)
		{	/* 1번째 방법
			   if(A[i]>max) max = A[i]; sum += sum + A[i];
			 */
	   // 2번째 방법
		   int temp = sc.nextInt();
		   if(temp>max) max = temp;
		   sum = sum + temp;
	   }
	   // 4. 출력
	   System.out.println(sum * 100.0/max/N);
	   sc.close();
	}

}
