package com.bhutta.mmmgwifift.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import com.bhutta.mmmgwifift.webserver.AndroidWebServer;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

public class Server extends Service {

    boolean isStarted;
    AndroidWebServer androidWebServer;
    String TAG="SERVER";

    private BroadcastReceiver broadcastReceiverNetworkState;


    public Server() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
          return  null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initBroadcastReceiverNetworkStateChanged();
        boolean started= startAndroidWebServer();
       if(started){
           Log.d(TAG, "onStartCommand: Service is started");
       }else{
           Log.d(TAG, "onStartCommand: Service is  Not started");
       }

       return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        stopAndroidWebServer();
        if (broadcastReceiverNetworkState != null) {
            unregisterReceiver(broadcastReceiverNetworkState);
        }
        super.onDestroy();
    }


    private boolean startAndroidWebServer() {
        if (!isStarted) {
            int port = (getFreePorts(8080, 9000, 1)[0]);
            try {
                if (port == 0) {
                    throw new Exception();
                }

                androidWebServer = new AndroidWebServer(port,getApplicationContext());
                androidWebServer.start();
                androidWebServer.setUrl("http://"+getIpAddress()+":"+port);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void initBroadcastReceiverNetworkStateChanged() {
        final IntentFilter filters = new IntentFilter();
        filters.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filters.addAction("android.net.wifi.STATE_CHANGE");
        broadcastReceiverNetworkState = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
             //   getIpAddress();
                   if(isStarted) {
                       Log.d(TAG, "onReceive: "+isStarted);
                       stopAndroidWebServer();
                       startAndroidWebServer();
                   }
           }
        };
        super.registerReceiver(broadcastReceiverNetworkState, filters);
    }

    private String getIpAddress() {
//        String ip = "";
//        try {
//            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
//                    .getNetworkInterfaces();
//            while (enumNetworkInterfaces.hasMoreElements()) {
//                NetworkInterface networkInterface = enumNetworkInterfaces
//                        .nextElement();
//                Enumeration<InetAddress> enumInetAddress = networkInterface
//                        .getInetAddresses();
//                int i=0;
//                while (enumInetAddress.hasMoreElements()) {
//                    InetAddress inetAddress = enumInetAddress.nextElement();
//
//                    if (inetAddress.isSiteLocalAddress()) {
//                        if (isConnectedInWifi()) {
//                            ip = inetAddress.getHostAddress();
//                            Log.d(TAG, "getIpAddress:wifi "+ip);
//                            return  "http://"+ip+":";
//
//                        }else{
//                            ip=inetAddress.getHostAddress();
//                            return  "http://"+ip+":";
//                        }
//                    }
//
//                }
//            }
//        } catch (SocketException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            ip += "Something Wrong! " + e.toString() + "\n";
//        }
//        Log.d(TAG, "getIpAddress Service: "+ip);

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                if (intf.getName().contains("wlan")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                            .hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()
                                && (inetAddress.getAddress().length == 4)) {
                            Log.d(TAG, inetAddress.getHostAddress());
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;

    }


    public boolean isConnectedInWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()
                && wifiManager.isWifiEnabled() && networkInfo.getTypeName().equals("WIFI")) {
            return true;
        }
        return false;
    }

    private boolean stopAndroidWebServer() {
        if (isStarted && androidWebServer != null) {
            androidWebServer.stop();
            return true;
        }
        return false;
    }


    private static int[] getFreePorts (int rangeMin, int rangeMax, int count) {
        int currPortCount = 0;
        int port[] = new int [count];
        for (int currPort = rangeMin; currPortCount < count && currPort <= rangeMax; ++currPort) {
            if (isPortFree(currPort))
                port[currPortCount++] = currPort;
        }
        if (currPortCount < count)
            throw new IllegalStateException ("Could not find " + count + " free ports to allocate within range " +
                    rangeMin + "-" + rangeMax + ".");
        return port;
    }


    private static boolean isPortFree (int port){
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}