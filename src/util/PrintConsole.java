package util;

/**
* 콘솔 표시 관련 클래스
* 
* @author SMILEJK
* @version 1.0 (Release. 2019-10-09)
* @since version 1.0
* 
*/
public class PrintConsole {
	private static boolean display = true;
	
	public PrintConsole(boolean display) {
		this.display = display;
	}
	
	public void print(String str) {
		if(display) System.out.println("### " + str);
	}
	
	public void print(String str1, String str2) {
		if(display) System.out.println("### " + str1 + " : " + str2);
	}
}
