package com.gridweaver.gridweaver.simulator;

public class VirtualDeviceLauncher {

    private static final int DEVICE_COUNT = 10000;

    public static void main(String[] args) {

        System.out.println(
                "Starting " + DEVICE_COUNT + " virtual devices..."
        );

        Thread[] devices = new Thread[DEVICE_COUNT];

        for (int i = 1; i <= DEVICE_COUNT; i++) {

            String deviceId = String.format(
                    "DEVICE-%05d",
                    i
            );

            devices[i - 1] = Thread.startVirtualThread(() -> {

                VirtualDeviceClient device =
                        new VirtualDeviceClient(
                                deviceId,
                                "localhost",
                                9090
                        );

                device.connect();
            });
        }

        System.out.println(
                DEVICE_COUNT + " virtual devices launched."
        );

        try {

            for (Thread device : devices) {
                device.join();
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.err.println(
                    "Launcher interrupted."
            );
        }
    }
}