package frc.robot.Subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class climb extends SubsystemBase {
  WPI_TalonFX r_arm;
  WPI_TalonFX l_arm;
  WPI_TalonFX r_winch;
  WPI_TalonFX l_winch;

  public climb() {
    r_arm = new WPI_TalonFX(5);
    l_arm = new WPI_TalonFX(6);
    r_winch = new WPI_TalonFX(7);
    l_winch = new WPI_TalonFX(8);

    r_arm.follow(l_arm);
    r_winch.follow(l_winch);

  }

  public void winch_in() {
    l_winch.set(1);
  }

  public void winch_stop() {
    l_winch.set(0);
  }

  public void winch_out() {
    l_winch.set(-1);
  }

  public void arm_in() {
    l_arm.set(1);
  }

  public void arm_stop() {
    l_arm.set(0);
  }

  public void arm_out() {
    l_arm.set(-1);
  }

  @Override
  public void periodic() {
  }
}
