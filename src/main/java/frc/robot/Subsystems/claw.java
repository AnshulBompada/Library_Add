package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class claw extends SubsystemBase {
  private WPI_TalonFX claw_arm;

  public claw() {
    claw_arm = new WPI_TalonFX(9);
  }

  public void claw_in() {
    claw_arm.set(1);
  }

  public void claw_out() {
    claw_arm.set(-1);
  }

  public void claw_stop() {
    claw_arm.set(0);
  }

  @Override
  public void periodic() {

  }
}
