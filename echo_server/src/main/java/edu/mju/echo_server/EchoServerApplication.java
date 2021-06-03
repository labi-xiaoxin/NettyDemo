package edu.mju.echo_server;

import edu.mju.echo_server.server.EchoServer;

public class EchoServerApplication {
    public static void main(String[] args) throws InterruptedException {
        int port;
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }else {
            port = 8081;
        }

        new EchoServer(port).start();
    }
}
