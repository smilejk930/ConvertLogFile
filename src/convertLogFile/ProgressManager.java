package convertLogFile;

import javax.swing.JButton;
import javax.swing.SwingWorker;

/**
* 파일 변환 중 => 버튼 비활성화
* 파일 미변환 => 버튼 활성화
* SwingWorkder 객체 execute
* 
* @author SMILEJK
* @version 1.0 (Release. 2019-10-09)
* @since version 1.0
* 
*/
public class ProgressManager {
    
	private static JButton jbt_dlfe;
    private static JButton jbt_save;
    private static JButton jbt_open;
    private static JButton jbt_cnvt;
    private static boolean status = true;
    
    public ProgressManager(JButton jbt_dlfe, JButton jbt_save, JButton jbt_open, JButton jbt_cnvt) {
    	this.jbt_dlfe = jbt_dlfe;
    	this.jbt_save = jbt_save;
    	this.jbt_open = jbt_open;
    	this.jbt_cnvt = jbt_cnvt;
	}
    
    public static void start(SwingWorker task) throws Exception{
    	status = false;
    	jbt_dlfe.setEnabled(status);
    	jbt_save.setEnabled(status);
    	jbt_open.setEnabled(status);
    	jbt_cnvt.setEnabled(status);
        task.execute();
    }
     
    public static void stop() {
        if(status == false) {
        	status = true;
        	jbt_dlfe.setEnabled(status);
        	jbt_save.setEnabled(status);
        	jbt_open.setEnabled(status);
        	jbt_cnvt.setEnabled(status);
        }
    }
}