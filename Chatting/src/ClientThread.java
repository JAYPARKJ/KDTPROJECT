import java.io.*;
import java.net.*;
// import java.util.*;

// 채팅 입장시마다 채터(chatter)당 스레드가 1개씩 생성
class ClientThread extends Thread {

	Socket socket;
	MultiServer server;
	BufferedReader reader;
	BufferedWriter writer;
	String userMsg; // 메시지
	String nickName; // 채터(chatter) 이름(별명)

	public ClientThread(Socket user_socket, MultiServer main_server) throws Exception {

		socket = user_socket;
		server = main_server;

		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
	}

	// Thread run 메소드
	@Override
	public void run() {

		try {
			// 읽어들일 메시지가 있을 경우
			while ((userMsg = reader.readLine()) != null) {

				// ex) 001java|spring|python
				if (userMsg.startsWith("001")) { // Client List

					nickName = userMsg.substring(3);
					server.broadCast("002 new Client[" + nickName + "]님 입장");
					server.broadClientList();

					// ex) 002 [spring]안녕하세요
				} else if (userMsg.startsWith("002")) { // Chatting

					server.broadCast("002 " + userMsg.substring(3));

				} // if

			} // while

		// 소켓 관련 예외처리
		} catch (SocketException se) { // Client Connection Exception
			System.out.println("run:" + se);
		} catch (Exception e) {
			System.out.println("run:" + e);
		} // try

		closeClient(); // 클라이언트 자원 반납
	}

	// 메시지 전송
	public void sendMessage(String serverMsg) {

		try {

			System.out.println("sendMsg:" + serverMsg);
			writer.write(serverMsg + "\n"); // 문자열 출력
			writer.flush(); // 출력 버퍼에 남아있는 문자들을 출력 후 버퍼를 비움(버퍼 초기화)

		} catch (Exception e) {
			System.out.println("sendMessage:" + e);
		}

	}

	// 자원 반납
	public void closeClient() {

		try {

			server.deleteFromServer(this);
			writer.close();
			reader.close();
			socket.close();

		} catch (Exception e) {
			System.out.println("closeClient:" + e);
		}
	} //

}