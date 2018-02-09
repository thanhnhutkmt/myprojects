package util;

import android.app.IntentService;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import software.nhut.personalutilitiesforlife.R;
import software.nhut.personalutilitiesforlife.constant.AppConstant;

/**
 * Created by Nhut on 10/19/2016.
 */

public class MySocket {
    public static List<InetAddress> scanIP(String subnet) {
        return scanIP(subnet, 1, 255, 10);
    }

    public static List<InetAddress> scanIP(String subnet, int startIP, int endIP, int timeout) {
        List<InetAddress> list = new ArrayList<>();
        for (int i = startIP; i < endIP; i++) {
            String host = subnet + "." + i;
            try {
                InetAddress ip = InetAddress.getByName(host);
                if (ip.isReachable(timeout)) list.add(ip);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void sendFile(int port, String ip, String fileNameWithPath, Context context) {
        long MAXSIZE = 1024 * 1073741824; // max file size = 1 TB
        File file = new File(fileNameWithPath);
        if (!file.exists() || file.length() > MAXSIZE) {
            Toast.makeText(context, R.string.SendfileSocket_error_message, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            byte[] bytes = new byte[1024*1024];
            byte[] initBytes = new byte[] {0,1,2,3,4};
            DataOutputStream os = new DataOutputStream(new Socket(InetAddress.getByName(ip), port).getOutputStream());
            os.write(initBytes, 0, initBytes.length);
            os.writeChars(file.getName() + "\n");
            os.writeLong(file.length());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int count = 0;
            while((count = bis.read(bytes)) > 0) os.write(bytes, 0, count);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
