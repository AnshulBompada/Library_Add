package frc.robot.Libs;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Path {
    public double a, b, c;


    public Path(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Pose2d pathValues(double t) {
        return new Pose2d(a, b, new Rotation2d(c));
    }

    public double pathDistance() {
        return 0;
    }
}
