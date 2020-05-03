package com.amir.ethoimage.interfaces;

import java.util.UUID;

public interface Constants {

    String ISLOGIN = "isLogin";
    String USER = "user";

    int PERMISSION_REQUEST_CODE_CG = 100;
    int PERMISSION_REQUEST_CODE_LOCATION = 101;
    int REQUEST_ENABLE_BLUETOOTH = 1;

    String FILE_PATH= "filePath";

    String appName = "EthoImage";
    UUID MY_UUID = UUID.fromString("0000110A-0000-1000-8000-00805F9B34FB");



    int STATE_lISTENING = 1;
    int STATE_CONNECTING = 2;
    int STATE_CONNECTED = 3;
    int STATE_CONNECTION_FAILED = 4;
    int MESSAGE_RECIEVED = 5;
}
