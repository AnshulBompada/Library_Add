package frc.robot.Commands;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.drive;

public class defaultdrive extends CommandBase {
  DoubleSupplier m_speed;
  DoubleSupplier m_rotation;
  drive m_TankDrive;

  public defaultdrive(DoubleSupplier speed, DoubleSupplier rotation, drive TankDrive) {
    m_speed = speed;
    m_rotation = rotation;
    m_TankDrive = TankDrive;
    addRequirements(TankDrive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    m_TankDrive.arcade_drive(m_speed.getAsDouble(), m_rotation.getAsDouble());
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
