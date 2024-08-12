/*
 * 다중 채팅 프로그램
*/

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

public class ChattingClient extends Frame implements ActionListener {

	private static final long serialVersionUID = 1L;

	Thread listenThread;
	Socket socket;
	BufferedReader reader;
	BufferedWriter writer;

	String serverMsg; // 서버 메시지
	String clientMsg; // 클라이언트 메시지
	String name; // 채터(chatter)이름

	TextArea ta; // 메시지 출력란(좌측)
	TextField tf; // 메시지 입력란(하단)
	List list; // 채팅 참여 채터(chatter) 목록
	Button b; // Exit 버튼
	Panel p1; // 상단(메시지 출력 및 채터 목록) 패널
	Panel p2; // 하단(메시지 입력 및 Exit 버튼) 패널

	// 생성자 : 채터(chatter) 사용자 명, IP, 포트(port:7777)
	public ChattingClient(String userName, String HostIP, String HostPort) throws Exception {

		// 채팅창 구성
		super("[" + userName + "] Cilent Window");
		this.name = userName;

		p1 = new Panel();
		p2 = new Panel();

		ta = new TextArea();
		tf = new TextField(40);

		tf.addActionListener(this);

		list = new List();
		b = new Button("Exit");

		p1.setLayout(new GridLayout(1, 2)); // 1 * 2구조 (상단 패널)

		p1.add(ta);
		p1.add(list);
		p2.add(tf);
		p2.add(b);

		add(p1, "Center"); // 상단 패널 채팅창에 추가
		add(p2, "South"); // 하단 패널 채팅창에 추가

		name = userName;

		////////////////////////////////////////////////////////////////
		// so ip 주소  192.168.0.163
		// 네트워크 구성

		InetAddress realHost = InetAddress.getByName(HostIP); // IP 처리
		int realPort = Integer.parseInt(HostPort); // port 처리

		// 소켓(socket) 객체 생성
		socket = new Socket(realHost, realPort);  

		// 소켓 관련 입/출력 스트림 객체 생성 
		// reader -> 2바이트  ms949 한글 2바이트, 5자로 치환 
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		// 채터(chatter) 입장에 따른 메시지 송신
		sendMessage("001" + userName + "\n");

		// 창닫기(채팅창 우측 최상단 "X") 이벤트 핸들러
		// 여기서 this ChattingClient
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				e.getWindow().setVisible(false);
				e.getWindow().dispose();
				System.exit(0);
			}

		});

		// Exit 버튼의 창닫기 이벤트 핸들러
		b.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

		});
		// 끝
	}

	// 메시지를 감지하는 메소드
	public void listenMessage() {

		listenThread = new Thread() { // 스레드(익명 구현 객체)

			@Override
			public void run() {

				try {

					// 서버로부터 온 메시지가 있다면...
					while ((serverMsg = reader.readLine()) != null) {

						System.out.println(serverMsg);

						// 신규 채터(chatter) 입장 ex) 001java|spring
						if (serverMsg.startsWith("001")) {

							list.removeAll();

							StringTokenizer st = new StringTokenizer(serverMsg.substring(3), "|");

							while (st.hasMoreTokens()) {
								
								String chatter = st.nextToken();
								
								System.out.println("next token : " + chatter);								
								
								list.add(chatter);
								
							} // while
							
							// ex) 002 new Client[spring]님 입장
							// ex) 002 [spring]안녕하세요
						} else if (serverMsg.startsWith("002")) {

							ta.append(serverMsg.substring(3) + "\n");

						} // if

					} // while(out)

				} catch (Exception e) {
					System.out.println(e);
				} finally {
					closeAll(); // 자원 반납
				} // try

			} // run()

		};

		listenThread.setDaemon(true); // 데몬 스레드 활성화 설정 
		
		// 데몬 스레드 시작 
		// : 메인 스레드(채팅 입장시 그 채터에 해당되는 스레드가 생성되고 
		// 그것이 main 스레드가 됨) 시작과 종결시 같이 시작하고 끝나는 도우미 스레드임
		// 적용 예시) 워드 프로세서의 자동 저장 기능, 
		// 미디어 플레이어의 비디오/오디오 재성, 가비지 컬렉터 

		listenThread.start();
	}

	// Stream/socket 자원 반납(close) 메소드
	public void closeAll() {

		try {
			
			reader.close(); // 입력 스트림 반납
			writer.close(); // 출력 스트림 반납
			socket.close(); // 소켓 반납
			System.exit(0); // 시스템 정상 종료

		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		}
		
	}

	// 메시지 전송 메소드
	public void sendMessage(String msg) {

		try {
			writer.write(msg + " \n");
			writer.flush();

		} catch (IOException ie) {
			System.out.println(ie.getMessage());
		}
		
	}

	// 이벤트 핸들러 : 액션이 발생하면 불려지는 핸들러
	@Override
	public void actionPerformed(ActionEvent ae) {

		sendMessage("002[" + name + "]" + tf.getText());
		tf.setText(""); // 텍스트 필드 초기화
	}

	// Main 메소드 : 실행
	public static void main(String args[]) {

		ChattingClient cl = null;

		try {
			
			cl = new ChattingClient(args[0], args[1], args[2]);

			cl.setBounds(200, 200, 400, 400); // 사각형 영역의 경계를 크기만큼 지정함
			cl.setVisible(true); // 화면에 보이도록 활성화

			cl.listenMessage(); // 데몬 스레드를 생성시키기 위한 메소드 호출

		} catch (Exception e) {
			cl.closeAll();
		}

	} // main

}