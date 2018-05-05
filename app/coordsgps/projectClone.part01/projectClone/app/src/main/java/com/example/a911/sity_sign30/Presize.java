package com.example.a911.sity_sign30;


public class Presize {


    public double presize(double[] coord)
    {
        double sum = 0;
        for (int i=0 ; i<coord.length; i++)
        {
            sum += coord[i];
        }
        double midCoord = sum/coord.length;

        return midCoord - .0005;   // map difference constant
    }

}
