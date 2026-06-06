package util;

import java.io.File;

/**
* 파일 정보 리턴 클래스
* 
* @author SMILEJK
* @version 1.0 (Release. 2019-10-09)
* @since version 1.0
* 
*/

public class FileInfo {
	private File openFile = null;
	
	public FileInfo(File openFile) {
		this.openFile = openFile;
	}
	
	public void setFile(File openFile) {
		this.openFile = openFile;
	}
	
	public File getFile() {
		return this.openFile;
	}
	
	/** 
	 * @param fileName.xml
	 * @return fileName
	 * */
	public String getName() {
		String rs = "";
		if(openFile!=null) {
			int pos = openFile.getName().lastIndexOf(".");
			rs = openFile.getName().substring(0, pos);
		}
		return rs;
	}
	
	/** 
	 * @param fileName.xml
	 * @return xml
	 * */
	public String getExt() {
		String rs = "";
		if(openFile!=null) {
			int pos = openFile.getName().lastIndexOf(".");
			rs = openFile.getName().substring( pos + 1 ).toLowerCase(); 
		}
		return rs; 
	}
	
	/** 
	 * @param C:\path\fileName.xml
	 * @return C:\path\
	 * */
	public String getPath() {
		String rs = "";
		if(openFile!=null) {
			int pos = openFile.getAbsolutePath().lastIndexOf("\\");
			rs = openFile.getAbsolutePath().substring( 0, pos + 1 );
		}
		return rs;
	}
	
}
