package frc.robot;


import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggedNetworkTables;
import org.littletonrobotics.junction.inputs.LoggedSystemStats;
import org.littletonrobotics.junction.io.ByteLogReceiver;
import org.littletonrobotics.junction.io.ByteLogReplay;
import org.littletonrobotics.junction.io.LogSocketServer;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import frc.robot.AprilTags.AprilTagCode;

public class Robot extends LoggedRobot {
  private AprilTagCode testAprilTag = new AprilTagCode();

  @Override
  public void robotInit() {
    setUseTiming(isReal()); 
    LoggedNetworkTables.getInstance().addTable("/SmartDashboard"); 
    Logger.getInstance().recordMetadata("FRC-APRILTAGS-CRUMBS2022", "2022-Onseason"); 

    if (isReal()) {
      Logger.getInstance().addDataReceiver(new ByteLogReceiver("/media/sda1/")); 
      Logger.getInstance().addDataReceiver(new LogSocketServer(5800));
    } else {
      String path = ByteLogReplay.promptForPath();
      Logger.getInstance().setReplaySource(new ByteLogReplay(path)); 
      Logger.getInstance().addDataReceiver(new ByteLogReceiver(ByteLogReceiver.addPathSuffix(path, "_sim")));
    }

    LoggedSystemStats.getInstance().setPowerDistributionConfig(1, ModuleType.kRev);
    Logger.getInstance().start(); 
  }

  @Override
  public void robotPeriodic() {
    testAprilTag.calculate();
    //System.out.println("Field to Robot: " + testAprilTag.getPos());
    Pose3d pos=testAprilTag.getPos();
    if (pos != null){
      Logger.getInstance().recordOutput("AprilTagOdometry", new double[] {pos.getX(), pos.getY(), pos.getZ(), pos.getRotation().getAngle()});
    }
    // Logger.getInstance().recordOutput("AprilTagOdometry", new double[] {pos.getX(), pos.getY(), pos.getZ(), pos.getRotation().getAngle()});

  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
