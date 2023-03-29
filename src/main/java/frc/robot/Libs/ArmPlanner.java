package frc.robot.Libs;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ArmPlanner {
    ArmFeedforward m_tripleFeedforward;
    PIDController m_stage1Controller;
    PIDController m_stage2Controller;
    PIDController m_stage3Controller;
    KinematicsSolver m_solver;
    Path m_path;
    double m_speed;
    double m_time;
    Timer m_timer = new Timer();
    CANSparkMax m_stage1;
    CANSparkMax m_stage2;
    CANSparkMax m_stage3;
    DoubleSupplier m_stage1Po;
    DoubleSupplier m_stage2Po;
    DoubleSupplier m_stage3Po;
    Subsystem m_subsystem;


    public ArmPlanner(ArmFeedforward feedforward, PIDController stage1Controller, PIDController stage2Controller, PIDController stage3Controller, KinematicsSolver solver, double L1, double L2, double L3, CANSparkMax stage1, CANSparkMax stage2, CANSparkMax stage3, DoubleSupplier stage1Position, DoubleSupplier stage2Position, DoubleSupplier stage3Position, Subsystem subsystem) {
        m_tripleFeedforward = feedforward;
        m_stage1Controller = stage1Controller;
        m_stage2Controller = stage2Controller;
        m_stage3Controller = stage3Controller;
        setMotors(stage1, stage2, stage3);
        setArmlenghts(L1, L2);
        m_solver = solver;
        m_stage1Po = stage1Position;
        m_stage2Po = stage2Position;
        m_stage3Po = stage3Position;
        m_subsystem = subsystem;
    }

    public void setMotors(CANSparkMax stage1, CANSparkMax stage2, CANSparkMax stage3) {
        m_stage1 = stage1;
        m_stage2 = stage2;
        m_stage3 = stage3;
    }

    public void startTime(double time, Path path) {
        m_path = path;
        double speed = m_path.pathDistance() / time;
        m_speed = speed;
        m_timer.stop();
        m_timer.start();
    }

    public void setArmlenghts(double stage1, double stage2) {
        m_solver = new KinematicsSolver(stage1, stage2);
    }

    public double[] calculate() {
        if(m_timer.get()* m_speed > m_path.pathDistance()) {
            return new double[] {0, 0, 0};
        }
            Pose2d output = m_path.pathValues(m_speed * m_timer.get());
            double[] setpoints = m_solver.posToAngles(output.getTranslation());
            double[] allSetpoints = {setpoints[0], setpoints[1], output.getRotation().getRadians()};
            return allSetpoints;
    }

    public boolean END() {
        if(m_timer.get() * m_speed > m_path.pathDistance()) {
            return true;
        }
        return false;
    }

    public BooleanSupplier isFinished() {
        return () -> END();
    }

    public void Stop() {
        m_timer.stop();
    }

    public void work() {
        double[] setpoints = calculate(); 
        m_stage1.setVoltage(m_stage1Controller.calculate(m_stage1Po.getAsDouble(), setpoints[0]) + m_tripleFeedforward.calculate(setpoints[0], m_stage1.getEncoder().getVelocity())); 
        m_stage2.setVoltage(m_stage2Controller.calculate(m_stage2Po.getAsDouble(), setpoints[1]) + m_tripleFeedforward.calculate(setpoints[1], m_stage1.getEncoder().getVelocity()));
        m_stage3.setVoltage(m_stage3Controller.calculate(m_stage3Po.getAsDouble(), setpoints[2]) + m_tripleFeedforward.calculate(setpoints[2], m_stage1.getEncoder().getVelocity()));
    }

    public FunctionalCommand movetoAngles(double time, Path path) {
        return new FunctionalCommand(() -> {startTime(time, path);}, 
        () ->{work();},
        interupted -> {Stop();},
        isFinished(), 
        m_subsystem);
    }

}
