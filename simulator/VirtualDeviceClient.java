package com.gridweaver.gridweaver.simulator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class VirtualDeviceClient {

    private final String deviceId;
    private final String host;
    private final int port;

    public VirtualDeviceClient(String deviceId, String host, int port) {
        this.deviceId = deviceId;
        this.host = host;
        this.port = port;
    }

    public void connect() {

        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println(
                    deviceId + " connected using "
                            + Thread.currentThread()
            );

            while (true) {

                String event = deviceId + ",GENERATION,100";

                writer.println(event);

                Thread.sleep(5000);
            }

        } catch (IOException e) {

            System.err.println(
                    deviceId + " connection error: "
                            + e.getMessage()
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.err.println(
                    deviceId + " interrupted"
            );
        }
    }
}