package frc.robot.Libs;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.Timer;

public class AnshulPlanner {
    double a, b, c;
    double max, min;
    Pose2d CurrentPose;
    PIDController angPID, distPID;
    Timer timer = new Timer();

    public AnshulPlanner(double a, double b, double c, Pose2d CurrentPose) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.CurrentPose = CurrentPose;
    }

    public void setPID( PIDController angPID, PIDController distPID) {
        this.angPID = angPID;
        this.distPID = distPID;
    }

    public void start() {
        timer.stop();
        timer.start();
    }

    public Transform2d T2D() {
        double targetx = timer.get() + min + 0.02;
        Pose2d target = new Pose2d(targetx, a * targetx * targetx + b * targetx + c, new Rotation2d(2*a*targetx + b));
        return new Transform2d(CurrentPose, target);
    }

    public Transform2d reverseT2D() {
        double targetx = -timer.get() + max - 0.02;
        Pose2d target = new Pose2d(targetx, a * targetx * targetx + b * targetx + c, new Rotation2d(2*a*targetx + b));
        return new Transform2d(CurrentPose, target);
    }

    private double[] rawCalculate() {
        double[] output = new double[2];
        Transform2d T2D = T2D();
        double angle = T2D.getRotation().getDegrees();
        double distance = T2D.getTranslation().getNorm();
        double angPIDOutput = angPID.calculate(CurrentPose.getRotation().getDegrees(), angle);
        double distPIDOutput = distPID.calculate(distance, 0);
        output[0] = angPIDOutput;
        output[1] = distPIDOutput;
        return output;
    }

    private double[] rawReverseCalculate() {
        double[] output = new double[2];
        Transform2d T2D = reverseT2D();
        double angle = T2D.getRotation().getDegrees();
        double distance = T2D.getTranslation().getNorm();
        double angPIDOutput = angPID.calculate(CurrentPose.getRotation().getDegrees(), angle);
        double distPIDOutput = distPID.calculate(distance, 0);
        output[0] = angPIDOutput;
        output[1] = distPIDOutput;
        return output;
    }

    public double[] calculate() {
        double[] noOutput = {0,0};
        while(CurrentPose.getX() < max) {
            double[] output = rawCalculate();
            return output;
        }
        return noOutput;
    }

    public double[] reverseCalculate() {
        double[] noOutput = {0,0};
        while(CurrentPose.getX() > min) {
            double[] output = rawReverseCalculate();
            return output;
        }
        return noOutput;
    }

    public void setRange(double max, double min) {
        this.max = max;
        this.min = min;
    }

}
