package com.example.a911.sity_sign30;

import java.io.*;
import java.net.*;
import java.lang.*;

public class Sender extends Thread{
    public static String sendCoord(String args[]){
        try
        {
            Socket s = new Socket(args[0], 25970);
            s.getOutputStream().write(args[1].getBytes());

            byte buf[] = new byte[64*1024];
            String data = new String(buf, 0, s.getInputStream().read(buf));

            s.getOutputStream().write(args[2].getBytes());
            String data1 = new String(buf, 0, s.getInputStream().read(buf));

            return data1;
        }
        catch(Exception e)
        {System.out.println("init error: "+e);}
    }
    public static
}
