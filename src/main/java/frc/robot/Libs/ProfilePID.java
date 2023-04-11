package frc.robot.Libs;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;


public class ProfilePID {
    private PIDController mPID;
    private SCurveProfile mprofile;
    private Timer mTimer;


    public ProfilePID(PIDController PID, SCurveProfile profile) {
        mPID = PID;
        mprofile = profile;
        mTimer = new Timer();
    }

    public PIDController getPID() {
        return mPID;
    }

    public SCurveProfile getProfile() {
        return mprofile;
    }

    public void reset(double position, double goal) {
        mPID.reset();
        mprofile.reset(position);
        mprofile.setGoal(goal);
        mTimer.reset();
        mTimer.start();
    }

    public double calculate(double position, double goal) {
        double time = mTimer.get();
        if(time > mprofile.getEndTime()) {
            return 0;
        }
        double profile = mprofile.calculate(time);
        double output = mPID.calculate(position, profile);
        return output;
    }
}