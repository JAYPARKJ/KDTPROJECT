package Algorithm1;

import java.util.Scanner;

public class CountOccurences {

	public static void main(String[] args) {

		/*
		 * int[] numbers = {1, 2, 3, 4, 5, 3, 4, 2, 9, 5, 7, 2, 3, 6, 7, 8}; // 정수 배열과
		 * 찾고자 하는 숫자 정의 int target = 3;
		 * 
		 * // 특정 숫자 개수를 세기 위한 변수 int count = 3;
		 * 
		 * // 배열의 모든 요소 순회 for(int number:numbers) { // 현재 요소가 target과 같으면 count를 증가시킨다.
		 * if(number == target) { count ++;}
		 * 
		 * } System.out.println("숫자" + target + "는 배열에서 " + count + "번 나타난다" );
		 */
		Scanner sc = new Scanner(System.in);
		 
		// 사용자로 부터 배열 크기를 입력받기
		System.out.print("배열 크기 입력");
		int size = sc.nextInt();
		// 배열 초기화
		int[] numbers = new int[size];
		
		// 사용자로 부터 배열 요소 입력받기
		System.out.println("배열 요소를 입력하세요");
		for(int i =0; i<size; i++)
		{
			System.out.println("숫자" +(i+1)+ ":");
			numbers[i] = sc.nextInt();
		}
		// 사용자로 부터 찾고자 하는 숫자 입력받기
		 System.out.println("찾고자 하는 숫자를 입력하세요");
		 int target = sc.nextInt();
		 
		 // 숫자 개수 세기 위한 변수
		 int count =0;
		
		 // 배열의 모든 요소 수노히
		 for(int number:numbers) {
			 // 현재 요소가 target과 같으면 count를 증가한다.
			 if(number==target)
			 {count++;}
		 }
		 System.out.println("숫자 :" + target + "는 배열에서 : "+count +"번 나타난다");
		 
		 //
		 sc.close();
	
	}
	 
}
