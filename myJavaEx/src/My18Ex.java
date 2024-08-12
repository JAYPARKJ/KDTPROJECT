import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class My18Ex {
	// 예외 처리방법 1 throws
	//public static void main(String[] args) throws FileNotFoundException {
	//public static void main(String[] args) throws IOException { fis.read() 예외 처리 
	
		// 수도 틀으면 물이 좔좔 
	
	public static void main(String[] args)   {
			
		// 상황연출1 : 없는 파일을 불러들인다. ("ab.txt") 
		File file = new File("a.txt"); // 2번 경로작성 
		// 1번 System.out.println("파일 존재 여부 : " + file.exists());
		//FileInputStream fis = new FileInputStream(file);
		
		
		try {FileInputStream fis = new FileInputStream(file);
//			FileInputStream fis = new FileInputStream(file);
			
			// fis.read가 만약에 -1이 아니라면 뺑뺑이 돌 수 있다. 
			// -1이라면 종료한다. 
		
			// 위 try를 아래로 집어넣었을 경우 자원 반납되어 밑에 fis.close()안해도됨
		
			try { 
				
				int ch; // 바이트일경우 문자로 치환 char(ch)
				// 3번 반복문을 통해 읽어 들어오기 
				while((ch =fis.read()) != -1) {
					
					System.out.println(ch);
				}
				// 자원 반납 -> 고속도로 감시카메라 
				 fis.close();
				
			} catch (IOException e) {
				System.out.println("파일 오류 : " + e);
				e.printStackTrace();
			}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		// 에러 날경우 2가지 예외처리방법 사용 try catch나 throw 사용 
		// 경로상에서 ./a.txt"   .하나는 상위폴더         .\\.a.txt  
		
		
		
	}

}
