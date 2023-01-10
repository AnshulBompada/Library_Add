package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class drive extends SubsystemBase {
    private WPI_TalonFX TopLeft;
    private WPI_TalonFX TopRight;
    private WPI_TalonFX BottomLeft;
    private WPI_TalonFX BottomRight;
    DifferentialDrive TankDrive;

    public drive() {
        TopLeft = new WPI_TalonFX(1);
        TopRight = new WPI_TalonFX(2);
        BottomRight = new WPI_TalonFX(1);
        BottomLeft = new WPI_TalonFX(1);

        TopLeft.follow(BottomLeft);
        TopRight.follow(BottomRight);

        TankDrive = new DifferentialDrive(TopRight, TopLeft);
    }


    public void arcade_drive(double speed, double rotation) {
        TankDrive.arcadeDrive(speed, rotation, true);   
    }
}