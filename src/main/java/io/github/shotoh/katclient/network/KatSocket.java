package io.github.shotoh.katclient.network;

import io.github.shotoh.katclient.features.general.InquisitorWaypoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class KatSocket {
    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public KatSocket(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(() -> {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    receive(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void send(String msg) {
        out.println(msg);
    }

    private void receive(String msg) {
        InquisitorWaypoints.foundInquisitor(msg);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
