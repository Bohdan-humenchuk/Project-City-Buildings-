package com.example.a911.coordsgps2;
import java.io.*;
import java.net.*;


public class Sender extends Thread{
    public static void main(String args[])
    {
        try
        {
            // получаем сокет сервера
            Socket s = new Socket("localhost", 25000);
            InputStream instream;
            OutputStream outstream;
            instream = s.getInputStream();
            outstream = s.getOutputStream();

            // читаем ответ
            byte buf[] = new byte[2048];
            String r = instream.read(buf);
            System.out.println(r)
            outstream.write(buf);

            // выводим ответ в консоль
            System.out.println(data);
        }
        catch(Exception e)
        {System.out.println("init error: "+e);} // вывод исключений
    }
}
