package edu.mju.time_server;

import edu.mju.time_server.server.TimeServer;

public class TimeServerApplication {
    public static void main(String[] args) throws InterruptedException {
        int port;
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }else {
            port = 8082;
        }

        new TimeServer(port).start();
    }
}
