package com.example.a911.sity_sign30;


import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;

public class Find {

    // return numeric of the building index in database
    // if building not exist will return -1
    public int coordFinding(double[] dbLt, double[] dbLg, double Latitude,  double Longitude, double Angle, int N )
    {
        // difference between Database and current device (lt, lg)
        double[] d_lt = new double[N]; //y
        double[] d_lg = new double[N]; //x

        // if it's true quarter contain true
        boolean[] trueQuarter = new boolean[N];

        // numeric of the current true quarter
        int[] quarternum = new int[N];


        // finding true quarter,and it's num
        for (int i =0; i<N; i++)
        {
            if ( ( dbLt[i] - Latitude ) >= 0 && ( dbLg[i] - Longitude ) >= 0) //45
            {
                if (Angle>=0 && Angle<=90)
                {
                    d_lt[i] = dbLt[i] - Latitude;
                    d_lg[i] = dbLg[i] - Longitude;
                    trueQuarter[i] = true;
                    quarternum[i] = 1;
                }
                else
                {
                    trueQuarter[i] = false;
                }
            }
            else if ( ( dbLt[i] - Latitude ) > 0 && ( dbLg[i] - Longitude ) < 0) //-45
            {
                if (Angle>=-90 && Angle<=0)
                {
                    d_lt[i] = dbLt[i] - Latitude;
                    d_lg[i] = dbLg[i] - Longitude;
                    trueQuarter[i] = true;
                    quarternum[i] = 2;
                }
                else
                {
                    d_lt[i] = 0;
                    d_lg[i] = 0;
                    trueQuarter[i] = false;
                }
            }
            else if ( ( dbLt[i] - Latitude ) < 0 && ( dbLg[i] - Longitude ) < 0) //-135
            {
                if (Angle>=-180 && Angle<=-90)
                {
                    d_lt[i] = dbLt[i] - Latitude;
                    d_lg[i] = dbLg[i] - Longitude;
                    trueQuarter[i] = true;
                    quarternum[i] = 3;
                }
                else
                {
                    d_lt[i] = 0;
                    d_lg[i] = 0;
                    trueQuarter[i] = false;
                }
            }
            else if ( ( dbLt[i] - Latitude ) < 0 && ( dbLg[i] - Longitude ) > 0) //135
            {
                if (Angle>=90 && Angle<=180)
                {
                    d_lt[i] = dbLt[i] - Latitude;
                    d_lg[i] = dbLg[i] - Longitude;
                    trueQuarter[i] = true;
                    quarternum[i] = 4;
                }
                else
                {
                    d_lt[i] = 0;
                    d_lg[i] = 0;
                    trueQuarter[i] = false;
                }
            }

        }

        //class for arctg
        arcTg arcTg = new arcTg();
        // numeric of the most approximately angle
        int smallestN = -1;
        // quantity of the smallest tg difference
        double smallestLt = 999;
        double smallestLg = 999;
        // if difference between angles is less than 10 decimal degrees is true
        boolean[] trueAngle = new boolean[N];
        // quantity d_(lg,lt) in miles (1 degree * d_lg, d_lt)
        double[]  d_mileLg = new  double[N];
        double[]  d_mileLt = new double[N];
        // angle of database certain home vector
        double[] dbAngle = new double[N];


        // comparing with other angles, finding the most approximately
        for (int i = 0; i<N; i++)
        {
            if (trueQuarter[i])
            {
                d_mileLt[i] = ( 69 * d_lt[i] );                                                    //ok
                d_mileLg[i] = ( d_lg[i] *  cos(d_mileLt[i]) * 45.372 );  //45.372                  //ok
                //ok
                if (quarternum[i] == 1 || quarternum[i] == 2)                                      //ok
                {
                    dbAngle[i] = arcTg.arctg((abs(d_mileLg[i] / d_mileLt[i])) );                   //ok
                }
                else if (quarternum[i] == 3 || quarternum[i] == 4)                                 //ok
                {
                    dbAngle[i] = abs(  180 - arcTg.arctg((abs(d_mileLg[i] / d_mileLt[i])) )  );    //ok

                    // for testing
                    //System.out.println("i: "+i+ " |  d_mileLg[i] / d_mileLt[i]: "+ abs(d_mileLg[i] / d_mileLt[i]) );
                    //System.out.println("i: "+i+ " |  arctg( abs(d_mileLg[i] / d_mileLt[i]): "+ arcTg.arctg( (abs(d_mileLg[i] / d_mileLt[i])) )  );
                }


                if ( abs( abs(dbAngle[i]) - abs(Angle) ) < 15 )
                {
                    trueAngle[i] = true;
                }
                else
                {
                    trueAngle[i] = false;
                }

            }

            if (trueAngle[i])
            {
                if ( abs(d_lg[i]) < smallestLg && abs(d_lt[i]) < smallestLt ) // +2abs
                {
                    smallestLg = abs(d_lg[i]);                  // +
                    smallestLt = abs(d_lt[i]);                  // +

                    smallestN = i;
                }
            }
        }

        return smallestN;
    }


}
