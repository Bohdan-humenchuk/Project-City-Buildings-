package com.example.a911.sity_sign30;


public class arcTg {

    public double arctg(double xDegrees) //radians
    {

        double arctg = 0;

        if(xDegrees>=-1 && xDegrees<=1)
        {
            for (int n=0; n<100; n++)
            {
                arctg += Math.pow(-1,n) * Math.pow(xDegrees,2*n+1) / (2*n+1) ;
            }

            return Math.toDegrees(arctg);
        }
        else
            return Math.toDegrees(Math.atan(xDegrees));
    }
}
