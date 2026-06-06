package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
* 프로그램 셋업파일 생성 및 설정
* 
* @author SMILEJK
* @version 1.0 (Release. 2019-10-09)
* @since version 1.0
* 
*/
public class SetupInfo {
	
	public static final String setupFile = "ConvertLogFile.ini";
	Properties p;
	PrintConsole console;
	
	private String dlfePath = "C:\\DecodeLogFile.exe";
	private String savePath = "C:\\";
	
	
	/**
	 * @return C:\\path\\DecodeLogFile.exe
	 * */
	public String getDlfePath() {
		return dlfePath;
	}
	
	public String setDlfePath(String dlfePath) {
		return this.dlfePath = dlfePath;
	}
	
	/**
	 * @return C:\\path\\
	 * */
	public String getSavePath() {
		return savePath;
	}
	
	public String setSavePath(String savePath) {
		return this.savePath = savePath;
	}
	
	/**
	 * 셋업파일 읽기/쓰기
	 * */
	public SetupInfo(PrintConsole console) {
		this.console = console;
		File loadFile = new File(setupFile);
		p = new Properties();
		
		try {
			if(loadFile.exists()) {
				console.print("loadFile exists");
				/* ini 파일 읽기 */
				p.load(new FileInputStream(setupFile));
				dlfePath = p.getProperty("dlfePath");
				savePath = p.getProperty("savePath");
				console.print("p.getProperty(\"dlfePath\")",p.getProperty("dlfePath"));
				console.print("p.getProperty(\"savePath\")",p.getProperty("savePath"));
			}else {
				/* ini 파일 쓰기 */
				p.setProperty("dlfePath", dlfePath);
				p.setProperty("savePath", savePath);
				p.store( new FileOutputStream(setupFile), null);
			}
		} catch (FileNotFoundException e) {
			console.print(e.getMessage());
		} catch (IOException e) {
			console.print(e.getMessage());
		}
	}
	
	/**
	 * 셋업파일 쓰기(생성)
	 * */
	public void saveInfo() {
		try{
			console.print("setupFile 쓰기 - 시작");
			/* Key 값 저장 */
			p.setProperty("dlfePath", dlfePath);
			p.setProperty("savePath", savePath);

			/* ini 파일 쓰기 */
			p.store( new FileOutputStream(setupFile), null);
			
			console.print("setupFile 쓰기 - 끝");
		}catch (Exception e) {
			console.print(e.getMessage());
		}
	}
	
}
