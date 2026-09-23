package com.gridweaver.gridweaver.simulator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketServer {

    private static final int PORT = 9090;

    private static final AtomicInteger connectedDevices =
            new AtomicInteger(0);

    public static void main(String[] args) {

        System.out.println("GridWeaver Socket Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println(
                    "Socket server listening on port " + PORT
            );

            while (true) {

                Socket clientSocket = serverSocket.accept();

                Thread.startVirtualThread(
                        () -> handleDevice(clientSocket)
                );
            }

        } catch (IOException e) {

            System.err.println(
                    "Socket server error: " + e.getMessage()
            );
        }
    }

    private static void handleDevice(Socket socket) {

        int count = connectedDevices.incrementAndGet();

        if (count <= 10 || count % 100 == 0) {
            System.out.println(
                    "Connected devices: " + count
            );
        }

        try (socket) {

            while (!socket.isClosed()) {
                Thread.sleep(5000);
            }

        } catch (Exception e) {

            System.err.println(
                    "Device connection error: "
                            + e.getMessage()
            );

        } finally {

            int remaining =
                    connectedDevices.decrementAndGet();

            if (remaining % 100 == 0) {
                System.out.println(
                        "Connected devices remaining: "
                                + remaining
                );
            }
        }
    }
}