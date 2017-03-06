package Hx.HxServer.network.jetty;

import Hx.HxServer.network.jetty.core.GmHttpServer;


public class FsGmServer {
	    private static int gmPort;

	    public static void main(String[] args) throws Exception {
	        gmPort = 8989;
	        new GmHttpServer(gmPort).start();
	    }
	}

