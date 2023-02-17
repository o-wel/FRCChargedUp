// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import frc.robot.input.XboxController;
import frc.robot.input.XboxController.XboxAxis;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.Arm;
import frc.robot.testingdashboard.TestingDashboard;

public class ArmOperatorControl extends CommandBase {
  Arm m_arm;
  XboxController m_xbox;

  /** Creates a new ArmOperatorControl. */
  public ArmOperatorControl() {
    m_arm = Arm.getInstance();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Arm.getInstance());
  }

  public static void registerWithTestingDashboard() {
    Arm arm = Arm.getInstance();
    ArmOperatorControl cmd = new ArmOperatorControl();
    TestingDashboard.getInstance().registerCommand(arm, "Manual", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_arm.setTurretMotorPower(0);
    m_arm.setShoulderMotorPower(0);
    m_arm.setElbowMotorPower(0);
    m_xbox = OI.getInstance().getOperatorXboxController();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Note that the max power here is calculated using multiplication
    // because the max power for each arm segment is a value between
    // 0 and 1. Note that 1 / 0.5 == 2, but 1 * 0.5 == 0.5
    double t_power = m_xbox.getAxis(XboxAxis.kXLeft)*Constants.A_TURRET_MAX_POWER;
    double s_power = m_xbox.getAxis(XboxAxis.kYLeft)*Constants.A_SHOULDER_MAX_POWER;
    double e_power = m_xbox.getAxis(XboxAxis.kYRight)*Constants.A_ELBOW_MAX_POWER;

    m_arm.setTurretMotorPower(t_power);
    m_arm.setShoulderMotorPower(s_power);
    m_arm.setElbowMotorPower(e_power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_arm.setTurretMotorPower(0);
    m_arm.setShoulderMotorPower(0);
    m_arm.setElbowMotorPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
