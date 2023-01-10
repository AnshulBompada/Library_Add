package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.defaultdrive;
import frc.robot.Subsystems.claw;
import frc.robot.Subsystems.climb;
import frc.robot.Subsystems.drive;

public class RobotContainer {
  CommandXboxController controller;
  Trigger LT;
  Trigger RT;
  Trigger RB;
  Trigger LB;
  Trigger A;
  Trigger B;
  drive Tank_drive;
  climb Climb;
  claw Claws;

  public RobotContainer() {
    Climb = new climb();
    Tank_drive = new drive();
    Claws = new claw();
    controller = new CommandXboxController(0);

    Tank_drive.setDefaultCommand(new defaultdrive(() -> controller.getLeftY(), ()-> controller.getRightX(), Tank_drive));
    LT = controller.leftTrigger();
    RT = controller.rightTrigger();
    LB = controller.leftBumper();
    RB = controller.rightBumper();
    A = controller.a();
    B = controller.b();

    configureBindings();
  }

  private void configureBindings() {
    LT.whileTrue(new InstantCommand(Climb::arm_in, Climb));
    RT.whileTrue(new InstantCommand(Climb::arm_out, Climb));
    LT.whileFalse(new InstantCommand(Climb::arm_stop, Climb));
    RT.whileFalse(new InstantCommand(Climb::arm_stop, Climb));

    LT.whileTrue(new InstantCommand(Climb::winch_in, Climb));
    RT.whileTrue(new InstantCommand(Climb::winch_out, Climb));
    LT.whileFalse(new InstantCommand(Climb::winch_stop, Climb));
    RT.whileFalse(new InstantCommand(Climb::winch_stop, Climb));

    A.whileTrue(new InstantCommand(Claws::claw_in, Claws));
    A.whileFalse(new InstantCommand(Claws::claw_stop, Claws));
    B.whileTrue(new InstantCommand(Claws::claw_out, Claws));
    B.whileFalse(new InstantCommand(Claws::claw_stop, Claws));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
