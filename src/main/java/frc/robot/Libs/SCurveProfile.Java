package frc.robot.Libs;

public class SCurveProfile {
    private double m_maxVelocity;
    private double m_maxAcceleration;

    public SCurveProfile(double maxAcceleration, double maxVelocity) {
        maxAcceleration = m_maxAcceleration;
        maxVelocity = m_maxVelocity;
    }

    public double Scurve() {
        return 0;
    }

    public double calculate(double x) {
        return 0;
    }

    public double positon(double a, double b double n) {
        double size = (b - a)/ n;
        double val = 0;
        for(i = 0; i < size; i += n ) {
            val = calculate(i);
        }
        return val;
    }

}