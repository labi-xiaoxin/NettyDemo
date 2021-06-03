package edu.mju.pojo_server;

import edu.mju.pojo_server.client.TimeClient;

public class TimeClientApplication {
    public static void main(String[] args) throws InterruptedException {
        int port;
        String host = "localhost";

        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }else {
            port = 8082;
        }

        new TimeClient(port,host).start();
    }
}
