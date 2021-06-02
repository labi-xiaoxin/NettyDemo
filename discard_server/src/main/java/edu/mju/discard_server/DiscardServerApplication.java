package edu.mju.discard_server;

import edu.mju.discard_server.server.DiscardServer;

/**
 * @Author: wyp
 * @Date: 2021/6/2 23:38
 */
public class DiscardServerApplication {
    public static void main(String[] args) {
        int port;
        if(args.length>0){
            port = Integer.parseInt(args[0]);
        }else {
            port = 8080;
        }

        new DiscardServer(port).start();
    }
}
