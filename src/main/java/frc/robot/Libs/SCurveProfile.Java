package frc.robot.Libs;


/* 
 * In order to use this class, you must first use the s curve profile generator to generate a profile for your robot.
 * Take the v and a constants and put them into the constructor of this class. Then use a profiled PID setup where
 * you run a timer and use the calculate method to get the position at that time. Have the PID controller follow
 * this position every single iteration of the timed loop. Before doing this you have to set the goal of the profile,
 * and run the loop while it is less than the total time of the profile. You have to set goal before you start the loop.
 * Warning: This class is not tested and may not work.
 * Profile generator: https://www.desmos.com/calculator/4qjxqjzq2j
*/

public class SCurveProfile {
    private double m_maxVelocity;
    private double m_maxAcceleration;
    private double m_end_time;
    private double m_val;
    private double m_currentPos;

    public SCurveProfile(double maxAcceleration, double maxVelocity) {
        m_maxAcceleration = maxAcceleration;
        m_maxVelocity = maxVelocity;
    }

    public void reset(double position) {
        m_currentPos = position;
    }

    public double Vcalculate(double x) {
        if(x < m_end_time/2) {
            return 2 * m_maxVelocity * (1/(1+Math.exp(-(m_maxAcceleration * (x))) - 0.5));
        }
        else if(x > m_end_time/2) {
            return 2 * m_maxVelocity * (1/(1+Math.exp((m_maxAcceleration * (x - m_end_time)))) - 0.5);
        }
            return 0;
    }


    private double position(double a, double b, double n) {
        double size = (b - a)/ n;
        double i;
        for(i = 0; i < size; i += n ) {
            m_val = Vcalculate(i);
        }
        return m_val + m_currentPos;
    }

    public double calculate(double x) {
        return position(0, x, 0.02);
    }

    public void setGoal(double goal) {
        m_end_time = totaltime(goal);
    }

    public double getEndTime() {
        return m_end_time;
    }

    public double totaltime(double goal) {
        double high = 2;
        double mid = high/2;
        double low = 0;
        while(Math.abs(goal - position(0, mid, 0.02)) == 0.125) {
            if(position(0, mid, 0.02) < goal) {
                low = mid;
                mid = (low + high)/2;
            }
            else if(position(0, mid, 0.02) > goal) {
                high = mid;
                mid /= 2;
            }
        }
        m_end_time = mid;
        return mid;
    }
}