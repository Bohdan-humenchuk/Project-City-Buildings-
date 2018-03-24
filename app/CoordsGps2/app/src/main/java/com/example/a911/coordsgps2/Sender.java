package com.example.a911.coordsgps2;

import java.io.*;
import java.net.*;


public class Sender extends Thread{
    public static void sendData(Double longtit, Double latit, Double ang)
    {
        try
        {
            Socket s = new Socket("127.0.0.1", 25970);
            InputStream instream;
            OutputStream outstream;
            instream = s.getInputStream();
            outstream = s.getOutputStream();

            String longtitude = Double.toString(longtit)
            String latitude = Double.toString(latit)
            String angle = Double.toString(ang)
            s.getOutputStream().write(longtitude.getBytes());
            s.getOutputStream().write(latitude.getBytes());
            s.getOutputStream().write(angle.getBytes());

   
            byte buf[] = new byte[2048];
            String r = instream.read(buf);
            System.out.println(r);
        }
        catch(Exception e)
        {System.out.println("init error: "+e);}
    }
}
