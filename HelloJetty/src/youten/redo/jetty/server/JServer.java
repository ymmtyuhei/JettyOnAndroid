
package youten.redo.jetty.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Jetty Server
 */
public class JServer {
    private int mPort;
    private Server mServer;

    /**
     * コンストラクタ
     * @param port Jettyを起動するport
     */
    public JServer(int port) {
        mPort = port;
    }

    /**
     * Serverを開始する。
     */
    public synchronized void start() {
        if ((mServer != null) && (mServer.isStarted())) {
            return;
        }
        if (mServer == null) {
            mServer = new Server(mPort);
            // Servletコンテキスト・ルートは"/"に設定
            ServletContextHandler handler = new ServletContextHandler(mServer, "/", ServletContextHandler.SESSIONS);
            // HelloServletクラスを "/hello"に割り当て
            handler.addServlet(new ServletHolder(new HelloServlet()), "/hello");
        }

        try {
            mServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 
     * Serverを停止する。
     */
    public synchronized void stop() {
        if ((mServer == null) || (mServer.isStopped())) {
            return;
        }
        try {
            mServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Serverが起動しているかどうか
     * @return 起動していたらtrueを、それ以外はfalseを返す。
     */
    public synchronized boolean isStarted() {
        if (mServer == null) {
           return false; 
        }
        return mServer.isStarted();
    }
}
