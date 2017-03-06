package Hx.HxServer.network.jetty.core;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import Hx.HxServer.network.jetty.core.GmServlet;

public class GmHttpServer {

    private final int port;

    public GmHttpServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        handler.setContextPath("/abc");
        //handler.setResourceBase(".");
        handler.addServlet(new ServletHolder(new GmServlet()), "/*");

        Server server = new Server(port);
        server.setHandler(handler);
        server.start();
        // server.join();
    }
}
