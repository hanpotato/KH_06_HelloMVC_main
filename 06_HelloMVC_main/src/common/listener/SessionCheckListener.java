package common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionCheckListener implements HttpSessionListener, HttpSessionAttributeListener {

	private static int activeSession=0;
	
	public static int getActiveSession() {
		return activeSession;
	}
	
    /**
     * Default constructor. 
     */
    public SessionCheckListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
//    	activeSession++;//id부여 : was를 들어가자마자 생성이 된다.
//    	System.out.println("현재 접속인원 수 : "+activeSession);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 

    	activeSession--;
    	System.out.println("세션 등록 인원 수 : "+activeSession);
    	System.out.println("로그아웃 되었습니다.");
    }

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
    	activeSession++;//id부여 : was를 들어가자마자 생성이 된다.
    	System.out.println("세션 등록 인원 수 : "+activeSession);
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
