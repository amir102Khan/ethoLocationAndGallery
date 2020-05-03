package com.amir.ethoimage.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.amir.ethoimage.interfaces.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class BluetoothService implements Constants {

    private Handler handler;
    private SendRecieve sendRecieve;
    private BluetoothAdapter bluetoothAdapter;
    private String imagePath;

    public BluetoothService(Handler handler) {
        this.handler = handler;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void getImagePath(String path) {
        this.imagePath = path;
    }

    public synchronized void connect(BluetoothDevice bluetoothDevice) {
        ClientClass clientClass = new ClientClass(bluetoothDevice);
        clientClass.start();
    }

    public synchronized void listen() {
        ServerClass serverClass = new ServerClass();
        serverClass.start();

    }

    private class ServerClass extends Thread {
        private BluetoothServerSocket serverSocket;

        public ServerClass() {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(appName, MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket socket = null;
            while (socket == null) {
                try {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket = serverSocket.accept();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);

                }

                if (socket != null) {
                    Message message = Message.obtain();
                    message.what = STATE_CONNECTED;
                    handler.sendMessage(message);


                    break;
                }
            }
        }
    }

    private class ClientClass extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ClientClass(BluetoothDevice device1) {
            device = device1;

            try {
                socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                socket.connect();

                Message message = Message.obtain();
                message.what = STATE_CONNECTED;
                handler.sendMessage(message);
                sendRecieve = new SendRecieve(socket);
                sendRecieve.start();
                sendImage(imagePath);

            } catch (IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }
    }

    private class SendRecieve extends Thread {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendRecieve(BluetoothSocket socket) {
            bluetoothSocket = socket;
            InputStream tempIn = null;
            OutputStream tempOut = null;

            try {
                tempIn = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream = tempIn;
            outputStream = tempOut;
        }

        public void run() {
            byte[] buffer = null;
            int bytes = 0;
            int index = 0;
            boolean flag = true;


            while (true) {


                if (flag) {
                    try {
                        byte[] temp = new byte[inputStream.available()];
                        if (inputStream.read(temp) > 0) {
                            bytes = Integer.parseInt(new String(temp, "UTF-8"));
                            buffer = new byte[bytes];
                            flag = false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        byte[] data = new byte[inputStream.available()];
                        int numbers = inputStream.read(data);

                        System.arraycopy(data, 0, buffer, index, numbers);
                        index = index + numbers;

                        if (index == bytes) {
                            handler.obtainMessage(MESSAGE_RECIEVED, bytes, -1, buffer).sendToTarget();
                            flag = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendImage(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] imageBytes = stream.toByteArray();
        int subArray = 400;
        sendRecieve.write(String.valueOf(imageBytes.length).getBytes());

        for (int i = 0; i < imageBytes.length; i += subArray) {
            byte[] tempArray;
            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArray));
            sendRecieve.write(tempArray);
        }
    }

}
