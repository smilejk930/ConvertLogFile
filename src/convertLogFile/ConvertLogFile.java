package convertLogFile;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import util.FileInfo;
import util.PrintConsole;
import util.SetupInfo;

/**
* DecodeLogFile.exe 파일을 이용해서 [bl 파일]=>[xml 파일]로 변환 및 생성한 후
* [xml 파일]=>[json 파일]로 변환 및 생성하는  프로그램.
* jdk ver : jdk1.8.0_161_x64
* 
* @author SMILEJK
* @version 1.0 (Release. 2019-10-09) 
* @since version 1.0
* 
* @param bl_file
* @return xml_file
* @return json_file
*/

public class ConvertLogFile extends JFrame implements ActionListener{

	private SetupInfo setupInfo; //셋엇파일 객체 
	private FileInfo fileInfo = new FileInfo(null); //파일 정보 객체
	private PrintConsole console = new PrintConsole(false); //ture: console 표시, false: console 미표시
	private JPanel contentPane;
    
	private JFileChooser jfc_dlfe;
	private JFileChooser jfc_open;
    private JFileChooser jfc_save;
    
    private JButton jbt_dlfe;
    private JButton jbt_save;
    private JButton jbt_open;
    private JButton jbt_cnvt;
    
    private JTextField txtF_dlfe;
    private JTextField txtF_save;
    private JTextField txtF_open;
    
    private JLabel jlb_progress;
    
    private FileReader fReader = null;
    private BufferedReader bReader = null;
    private FileWriter fWriter = null;
    private BufferedWriter bWriter = null;

    
    /**
	 * 메인 쓰레드 메소드 
	 * swing 테마 적용(javax.swing.plaf.nimbus.NimbusLookAndFeel)
	 * @see UIManager#setLookAndFeel(javax.swing.LookAndFeel)
	 * */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					ConvertLogFile frame = new ConvertLogFile();
					frame.setVisible(true);
				} catch (UnsupportedLookAndFeelException e) {
				} catch (ClassNotFoundException e) {
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				} catch (Exception e) { e.printStackTrace();}
			}
		});
	}
	
	/**
	 * 프로그램 위치 및 텍스트 설정
	 * */
	public ConvertLogFile() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Json Log - DATA CONVERTER");
		setBounds(100, 100, 496, 282);
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jfc_dlfe = new JFileChooser();
		jfc_save = new JFileChooser();
		jfc_open = new JFileChooser();
	    
	    jbt_dlfe = new JButton("DecodeLogFile.exe 경로");
	    jbt_save = new JButton("저장 경로");
	    jbt_open = new JButton("파일 열기");;
	    jbt_cnvt = new JButton("변 환");
	    
	    txtF_dlfe = new JTextField();
	    txtF_save = new JTextField();
	    txtF_open = new JTextField();
	    
	    jlb_progress = new JLabel("");
	    
	    jbt_dlfe.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		jbt_dlfe.setBounds(12, 22, 167, 30);
		
		jbt_save.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jbt_save.setBounds(12, 62, 86, 30);
        
        jbt_open.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        jbt_open.setBounds(12, 102, 86, 30);
        
        jbt_cnvt.setBackground(Color.ORANGE);
		jbt_cnvt.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		jbt_cnvt.setBounds(12, 158, 456, 30);
		
		txtF_dlfe.setToolTipText("DecodeLogFile.exe 경로 등록");
		txtF_dlfe.setForeground(SystemColor.activeCaptionBorder);
		txtF_dlfe.setEnabled(false);
		txtF_dlfe.setEditable(false);
		txtF_dlfe.setColumns(10);
		txtF_dlfe.setBounds(185, 22, 283, 30);
		
		txtF_save.setToolTipText("변환될 파일이 저장되는 경로 등록");
		txtF_save.setForeground(SystemColor.activeCaptionBorder);
		txtF_save.setEnabled(false);
		txtF_save.setEditable(false);
		txtF_save.setColumns(10);
		txtF_save.setBounds(104, 62, 364, 30);
		
		txtF_open.setToolTipText("변환할 bl 파일 열기");
		txtF_open.setForeground(SystemColor.activeCaptionBorder);
		txtF_open.setEnabled(false);
		txtF_open.setEditable(false);
		txtF_open.setColumns(10);
		txtF_open.setBounds(104, 102, 364, 30);
		
		jlb_progress.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		jlb_progress.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_progress.setBounds(12, 208, 456, 18);
		
		contentPane.add(jbt_dlfe);
		contentPane.add(jbt_save);
		contentPane.add(jbt_open);
		contentPane.add(jbt_cnvt);
		contentPane.add(txtF_dlfe);
		contentPane.add(txtF_save);
		contentPane.add(txtF_open);
		contentPane.add(jlb_progress);
		
		start();
	}
	
	/**
	 * 파일 선택 및 버튼 활성화 비활성화에 대한  progressManager 생성자 생성
	 * @see ProgressManager#ProgressManager(JButton, JButton, JButton)
	 * */
	public void start(){
		setupInfo = new SetupInfo(console); //셋엇파일 객체
		
		jbt_dlfe.addActionListener(this);
		jbt_save.addActionListener(this);
		jbt_open.addActionListener(this);
		jbt_cnvt.addActionListener(this);
		
		txtF_dlfe.setText(setupInfo.getDlfePath());//경로 표시
		jfc_dlfe.setFileFilter(new FileNameExtensionFilter("exe", "exe")); //파일 형식 exe로 설정 
		jfc_dlfe.setMultiSelectionEnabled(false);//다중 선택 불가
        
        txtF_save.setText(setupInfo.getSavePath());//경로 표시
        jfc_save.setFileSelectionMode(jfc_save.DIRECTORIES_ONLY); // 디렉토리만 선택가능
        
        jfc_open.setFileFilter(new FileNameExtensionFilter("bl", "bl")); //파일 형식 bl로 설정 
        jfc_open.setMultiSelectionEnabled(false);//다중 선택 불가
        
        ProgressManager progressManager = new ProgressManager(jbt_dlfe,jbt_save,jbt_open,jbt_cnvt);
        
    }
	
	@Override
    public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == jbt_dlfe){ /* DecodeLogFile.exe 경로 버튼 이벤트 */  console.print("DecodeLogFile.exe 경로 버튼 이벤트");
				jfc_dlfe.setCurrentDirectory(new File(setupInfo.getDlfePath())); //저장된 경로 불러옴.
		        if(jfc_dlfe.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
		        	setupInfo.setDlfePath(jfc_dlfe.getSelectedFile().getAbsolutePath());
		            txtF_dlfe.setText(jfc_dlfe.getSelectedFile().getAbsolutePath());//경로 표시
		            console.print("dlfePath", jfc_dlfe.getSelectedFile().getAbsolutePath());
		        }
		}
		else if(arg0.getSource() == jbt_save){ /* 저장 경로 버튼 이벤트 */  console.print("저장 경로 버튼 이벤트");
	    		jfc_save.setCurrentDirectory(new File(setupInfo.getSavePath())); //저장된 경로 불러옴.
	            if(jfc_save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
	            	setupInfo.setSavePath(jfc_save.getSelectedFile().getAbsolutePath()+"\\");
	                txtF_save.setText(jfc_save.getSelectedFile().getAbsolutePath()+"\\");//경로 표시
	                console.print("savePath", jfc_save.getSelectedFile().getAbsolutePath());
	            }
		}
		else if(arg0.getSource() == jbt_open){ /* 파일 열기 버튼 이벤트 */  console.print("파일 열기 버튼 이벤트");
				jfc_open.setCurrentDirectory(new File(fileInfo.getPath())); //저장된 경로 불러옴.
	            if(jfc_open.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
	            	File openFile = jfc_open.getSelectedFile();
	            	fileInfo = new FileInfo(openFile); //파일 정보 객체
	            	
	            	console.print("fileInfo");
	            	console.print("getName()", fileInfo.getName());
	            	console.print("getExt()", fileInfo.getExt());
	            	console.print("getPath()", fileInfo.getPath());
	            	console.print("getAbsolutePath()", fileInfo.getFile().getAbsolutePath());
	            	
	            	/* bl 확장자 확인 - 시작*/
	            	if(!"bl".equals(fileInfo.getExt())) {
	            		JOptionPane.showMessageDialog(null, "bl 확장자만 변환 가능합니다.", "파일 확장자 오류", JOptionPane.ERROR_MESSAGE);
	            		fileInfo.setFile(null);
	            		return ;
	            	}
	            	/* bl 확장자 확인 - 끝*/
	            	txtF_open.setText(fileInfo.getFile().getAbsolutePath()); //경로 표시
	            	jlb_progress.setText("");
	            }
	    }
		else if(arg0.getSource() == jbt_cnvt){ /* 변환 버튼 이벤트 */  console.print("변환 버튼 이벤트");
				if(fileInfo.getFile() == null) {
					JOptionPane.showMessageDialog(null, "변환할 bl 파일을 선택해 주십시오.", "bl 파일 선택 필요", JOptionPane.WARNING_MESSAGE);
					console.print("Not exists openFile");	    				
					return ;
				}
				
				File dlfeFile = new File(setupInfo.getDlfePath());
				if(!dlfeFile.exists()) {
					JOptionPane.showMessageDialog(null, "DecodeLogFile.exe 파일이 존재하지 않습니다.", "DecodeLogFile.exe 파일 선택 필요", JOptionPane.WARNING_MESSAGE);
					console.print("Not exists DecodeLogFile.exe");	    				
					return ;
				}
				
				File saveDir = new File(setupInfo.getSavePath());
				if(!saveDir.exists()) {
					JOptionPane.showMessageDialog(null, "저장 경로가  존재하지 않습니다.", "저장 경로 선택 필요", JOptionPane.WARNING_MESSAGE);
					console.print("Not exists saveDir");	    				
					return ;
				}
				
				int n = JOptionPane.showConfirmDialog(null, "파일을 변환하시겠습니까?", "파일 변환", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.NO_OPTION) return ;
				
				/**
				 * 셋업파일 저장
				 * */
				setupInfo.saveInfo();
				
				try{
					/**
					 * 파일 변환 로직 구현(SwingWorker)(Thread 생성)
					 * */
					PublishTask task = new PublishTask(){
		            	@Override
		            	protected Void doInBackground() throws Exception {
		            		/* 변환된 xml 파일 찾기 - 시작*/ console.print("변환된 xml 파일 찾기 - 시작");
		            		int tmp = 0;
		            		long tmpSize[] = {0,0,0,0};
		            		
		            		/* DecodeLogFile.exe을 통해 생성된 xml파일을 찾을 수 있도록 while문으로 구현
		            		 * 외부 프로그램이기에 완전히 변환된 xml파일을 얻기 위해 총 4개의 tmp 4개 생성하여 시간별로 쌓이는 xml파일 사이즈를 체크
		            		 * 생성되는 시간을 충분히 주기 위하여 0.05초의 여유시간을 설정
		            		 * */
							while(true) {
								Thread.sleep(50); //0.05 sec
								File xmlFile = new File(fileInfo.getPath()+fileInfo.getName()+".xml"); 
								if( xmlFile.exists() && xmlFile.length()>0 ) {
									switch(tmp) {
										case 0 :
											tmpSize[0] = xmlFile.length();
											tmp = 1;
											break;
										case 1 :
											tmpSize[1] = xmlFile.length();
											tmp = 2;
											break;
										case 2 :
											tmpSize[2] = xmlFile.length();
											tmp = 3;
											break;
										case 3 :
											tmpSize[3] = xmlFile.length();
											tmp = 0;
											break;
									}
									
									if(tmpSize[0] == tmpSize[1]&&tmpSize[0] == tmpSize[2]&&tmpSize[0] == tmpSize[3]&&tmpSize[1] == tmpSize[2]&&tmpSize[1] == tmpSize[3]&&tmpSize[2] == tmpSize[3]) {
										console.print("tmpSize0 size",Long.toString(tmpSize[0]));
										console.print("tmpSize1 size",Long.toString(tmpSize[1]));
										console.print("tmpSize2 size",Long.toString(tmpSize[2]));
										console.print("tmpSize3 size",Long.toString(tmpSize[3]));
										fileInfo.setFile(xmlFile);
										break;
									}
								}
							}
							/* 변환된 xml 파일 찾기 - 끝*/ console.print("변환된 xml 파일 찾기 - 끝");
							
							/* 파일 읽기 - 시작 */  console.print("파일 읽기 - 시작");
	    					fReader = new FileReader(fileInfo.getFile());
	    					bReader = new BufferedReader(fReader);
			                
			                String line = "";
			                StringBuilder sb = new StringBuilder();
		
			                while((line = bReader.readLine()) != null){
			                	sb.append(line);
			                }
			                bReader.close(); //.readLine()은 끝에 개행문자를 읽지 않는다.
			                /* 파일 읽기 - 끝 */  console.print("파일 읽기 - 끝");
			                
			                /* xml to json - 시작*/  console.print("xml to json - 시작");
			                jlb_progress.setText("XML to JSON CONVERTING...");
		                	JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
			                String strJson = xmlJSONObj.toString();
			                console.print("strJson bytes", ""+strJson.getBytes().length);
			                /* xml to json - 끝*/  console.print("xml to json - 끝");
			                
			                /* 파일 생성 - 시작 */  console.print("파일 생성 - 시작");
			                String jsonPath = setupInfo.getSavePath()+fileInfo.getName()+".json";
			                console.print("jsonPath",jsonPath);
			                fWriter = new FileWriter(jsonPath);
		                    bWriter = new BufferedWriter(fWriter);
		                    
		                    bWriter.write(strJson);
		                    bWriter.flush();
		                    /* 파일 생성 - 끝 */  console.print("파일 생성 - 끝");
		                    return null;
		            	}
		            	
		            	@Override
		                public void done() {
		            		jlb_progress.setText("TRANSFORMATION COMPLETE");
		            		console.print("The End!!!");
		                }
		            };
		            
		            jlb_progress.setText("BL to XML CONVERTING...");
		            
		            /* DecodeLogFile.exe 사용 - 시작*/ console.print("DecodeLogFile.exe 사용 - 시작");
	        		List<String> commands = new ArrayList<String>();                
					commands.add("cmd.exe");
					commands.add("/C");
					commands.add(setupInfo.getDlfePath()+" -xml "+ fileInfo.getFile().getAbsolutePath());
					
					ProcessBuilder pb = new ProcessBuilder(commands);
					pb.start();
					/* DecodeLogFile.exe 사용 - 끝*/ console.print("DecodeLogFile.exe 사용 - 끝");
					
					task.start();
	                
	                
	    		}catch (FileNotFoundException e) {
	            	console.print("FileNotFoundException",e.getMessage());
	            	jlb_progress.setText("FileNotFoundException >> "+ e.getMessage());
	            }catch(IOException e){
	            	console.print("IOException",e.getMessage());
	            	jlb_progress.setText("IOException >> "+ e.getMessage());
	            }catch(JSONException e) {
	            	console.print("JSONException",e.getMessage());
	            	jlb_progress.setText("JSONException >> "+ e.getMessage());
	            }catch(Exception e) {
	            	console.print("Exception",e.getMessage());
	            	jlb_progress.setText("Exception >> "+ e.getMessage());
				} finally {
	                try {
	                    if(bWriter != null) bWriter.close();
	                    if(fWriter != null) fWriter.close();
	                } catch(IOException e) {
	                	console.print("IOException_finally",e.getMessage());
	                	jlb_progress.setText("IOException_finally >> "+ e.getMessage());
	                }
	            }
	        
		}
    }//actionPerformed end
}
