package frc.robot.subsystems;

//import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;

//import com.kauailabs.navx.frc.AHRS;

//import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
//import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.SPI;
//import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class SwerveSubsystem extends SubsystemBase {
    private final SwerveModule frontLeft = new SwerveModule(
            DriveConstants.kFrontLeftDriveMotorPort,
            DriveConstants.kFrontLeftTurningMotorPort,
            DriveConstants.kFrontLeftDriveEncoderReversed,
            DriveConstants.kFrontLeftTurningEncoderReversed,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderPort,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontLeftDriveAbsoluteEncoderReversed);


    private final SwerveModule frontRight = new SwerveModule(
            DriveConstants.kFrontRightDriveMotorPort,
            DriveConstants.kFrontRightTurningMotorPort,
            DriveConstants.kFrontRightDriveEncoderReversed,
            DriveConstants.kFrontRightTurningEncoderReversed,
            DriveConstants.kFrontRightDriveAbsoluteEncoderPort,
            DriveConstants.kFrontRightDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kFrontRightDriveAbsoluteEncoderReversed);

    private final SwerveModule backLeft = new SwerveModule(
            DriveConstants.kBackLeftDriveMotorPort,
            DriveConstants.kBackLeftTurningMotorPort,
            DriveConstants.kBackLeftDriveEncoderReversed,
            DriveConstants.kBackLeftTurningEncoderReversed,
            DriveConstants.kBackLeftDriveAbsoluteEncoderPort,
            DriveConstants.kBackLeftDriveAbsoluteEncoderOffsetRad,
            DriveConstants.kBackLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule backRight = new SwerveModule(
            DriveConstants.kBackRightDriveMotorPort,
            DriveConstants.kBackRightTurningMotorPort,
            DriveConstants.kBackRightDriveEncoderReversed,
            DriveConstants.kBackRightTurningEncoderReversed,
           DriveConstants.kBackRightDriveAbsoluteEncoderPort,
           DriveConstants.kBackRightDriveAbsoluteEncoderOffsetRad,
           DriveConstants.kBackRightDriveAbsoluteEncoderReversed);

    private final AHRS navX = new AHRS(SPI.Port.kMXP);


public SwerveModulePosition[] getModulePositions(){

    return( new SwerveModulePosition[]{
      frontLeft.getPosition(), 
      frontRight.getPosition(), 
      backLeft.getPosition(),
      backRight.getPosition()});

  }


 // private SwerveDriveOdometry odometer = new SwerveDriveOdometry(DriveConstants.kDriveKinematics,SwerveModulePosition, null);
   //Set robot odometry object to current robot heading and swerve module positions
   //SwerveDriveOdometry odometer = new SwerveDriveOdometry(DriveConstants.kDrivekinematics, getRotation2d(), getModulePositions());
  

/* 
    SwerveDriveOdometry odometer = new SwerveDriveOdometry(kDriveKinematics, getRotation2d(),
    new SwerveModulePosition[] {
        frontLeft.getDrivePosition(),
        frontRight.getDrivePosition(),
        backLeft.getDrivePosition(),
        backRight.getDrivePosition()
    }, new Pose2d(5.0, 13.5, new Rotation2d()));*/
    

 public SwerveSubsystem() {

        
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();
    }
    
    public void zeroHeading() {
        navX.reset();
    }

    public double getHeading() {
       //  Return not working 
        return Math.IEEEremainder(navX.getAngle(), 360);
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(getHeading());
    }

  // public Pose2d getPose() {
       // return odometer.getPoseMeters();
    //}

    public void resetOdometry(Pose2d pose) {
       // odometer.resetPose(pose, getRotation2d());
    }

    @Override
    public void periodic() {
       // odometer.update(getRotation2d(), frontLeft.getState(), frontRight.getState(), backLeft.getState(),backRight.getState());
        SmartDashboard.putNumber("Robot Heading", getHeading());
      
    }

    public void stopModules() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }

    public void setModuleStates(SwerveModuleState[] desiredStates) {
       SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);

        frontLeft.setDesiredState(desiredStates[2]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[3]);
        backRight.setDesiredState(desiredStates[0]);
    }
    public SwerveModule getFrontLeft() {
        return frontLeft;
    }
    public SwerveModule getFrontRight() {
        return frontRight;
    }
    public SwerveModule getBackLeft() {
        return backLeft;
    }
    public SwerveModule getBackRight() {
        return backRight;
    }
   
    public AHRS getNavX() {
        return navX;
    }

}