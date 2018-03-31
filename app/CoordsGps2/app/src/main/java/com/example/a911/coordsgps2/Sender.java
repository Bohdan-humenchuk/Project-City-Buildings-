package com.example.a911.coordsgps2;

import java.io.*;
import java.net.*;


public class Sender extends Thread{
    public static void sendData(Double longtit, Double latit, Double ang)
    {
        try
        {
            Socket s = new Socket("77.120.204.6", 25970);
            InputStream instream;
            OutputStream outstream;
            instream = s.getInputStream();
            outstream = s.getOutputStream();
            byte buf[] = new byte[64*1024];

            String longtitude = Double.toString(longtit)
            String latitude = Double.toString(latit)
            String angle = Double.toString(ang)

            s.getOutputStream().write(longtitude.getBytes());
            String message=s.getInputStream().read(buf);
            s.getOutputStream().write(latitude.getBytes());
            String message=s.getInputStream().read(buf);
            s.getOutputStream().write(angle.getBytes());
            String message=s.getInputStream().read(buf);
   
            byte buf[] = new byte[64*1024];
            String r = instream.read(buf);

        }
        catch(Exception e)
        {System.out.println("init error: "+e);}
        return r;
    }
}
