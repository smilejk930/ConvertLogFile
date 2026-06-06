package convertLogFile;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker;

/**
* 
* SwingWorkder 상태에 따라 ProgressManager 메소드 실행
* 
* @author SMILEJK
* @version 1.0 (Release. 2019-10-09)
* @since version 1.0
* 
*/
public abstract class PublishTask extends SwingWorker implements PropertyChangeListener {
 
     
    public PublishTask(){
        this.addPropertyChangeListener(this);
    }
     
	public void start() throws Exception{
        ProgressManager.start(this);
    }
     
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                ProgressManager.stop();
            }
        }else if(evt.getPropertyName().equals("progress")) {
        }
    }    
}