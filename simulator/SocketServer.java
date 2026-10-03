package com.gridweaver.gridweaver.simulator;

import com.gridweaver.gridweaver.engine.StateEngine;
import com.gridweaver.gridweaver.model.Device;
import com.gridweaver.gridweaver.model.DeviceType;
import com.gridweaver.gridweaver.model.GridState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketServer {

    private static final int PORT = 9090;

    private static final AtomicInteger connectedDevices =
            new AtomicInteger(0);

    private static final StateEngine stateEngine =
            new StateEngine();

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

        try (
                socket;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
        ) {

            String event;

            while ((event = reader.readLine()) != null) {

                System.out.println(
                        "Received: " + event
                );

                processEvent(event);
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

    private static void processEvent(String event) {

        try {

            String[] parts = event.split(",");

            if (parts.length != 3) {
                System.err.println(
                        "Invalid event: " + event
                );
                return;
            }

            String deviceId = parts[0];
            String eventType = parts[1];
            double value = Double.parseDouble(parts[2]);

            Device device;

            if (eventType.equals("GENERATION")) {

                device = new Device();
                device.setId(deviceId);
                device.setType(DeviceType.SOLAR);
                device.setPower(value);

            } else if (eventType.equals("LOAD")) {

                device = new Device();
                device.setId(deviceId);
                device.setType(DeviceType.LOAD);
                device.setPower(value);

            } else if (eventType.equals("BATTERY")) {

                device = new Device();
                device.setId(deviceId);
                device.setType(DeviceType.BATTERY);
                device.setBatteryPercentage(value);

            } else {

                System.err.println(
                        "Unknown event type: " + eventType
                );
                return;
            }

            stateEngine.updateDevice(device);

            GridState gridState =
                    stateEngine.calculateGridState();

            System.out.println(
                    "Grid State -> Generation: "
                            + gridState.getTotalGeneration()
                            + ", Load: "
                            + gridState.getTotalLoad()
                            + ", Battery: "
                            + gridState.getBatteryCharge()
                            + ", Import: "
                            + gridState.getGridImport()
                            + ", Export: "
                            + gridState.getGridExport()
            );

        } catch (Exception e) {

            System.err.println(
                    "Failed to process event: "
                            + event
                            + " | "
                            + e.getMessage()
            );
        }
    }
}