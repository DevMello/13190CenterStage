package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Commands.*;
import org.firstinspires.ftc.teamcode.Subsystems.*;

public class BaseOpMode extends CommandOpMode {

    protected DriveSubsystem driveSubsystem;
    protected IntakeSubsystem intakeSubsystem;
    protected ClawSubsystem clawSubsystem;
    protected LiftSubsystem liftSubsystem;
    protected ArmSubsystem armSubsystem;
    private MotorEx fl, fr, bl, br, intakeMotor, liftLeft, liftRight;
    private ColorSensor colorSensor;

    private SimpleServo arm,pitch,claw;
    private MotorEx[] motors = {fl, fr, bl, br};
    protected GamepadEx gamepadEx1;
    protected GamepadEx gamepadEx2;
    protected DriveRobotOptimalCommand driveRobotOptimalCommand;

    protected StartIntakeCommand startIntakeCommand;
    protected ClawGrabCommand clawGrabCommand;
    protected ClawReleaseCommand clawReleaseCommand;
    protected ManualLiftCommand manualLiftCommand;
    protected AxleMoveCommand axleMoveCommand;
    protected ArmMoveCommand armMoveCommand;

    @Override
    public void initialize() {
        arm = new SimpleServo(hardwareMap, "arm", 0, 255);
        pitch = new SimpleServo(hardwareMap, "pitch", 0, 255);
        //Motors
        fl = new MotorEx(hardwareMap, "frontLeft");
        fr = new MotorEx(hardwareMap, "frontRight");
        bl = new MotorEx(hardwareMap, "backLeft");
        br = new MotorEx(hardwareMap, "backRight");

        intakeMotor = new MotorEx(hardwareMap, "intakeMotor");

        liftLeft = new MotorEx(hardwareMap, "liftLeft");
        liftRight = new MotorEx(hardwareMap, "liftRight");


        //Prevent Drift
        fl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftLeft.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Reverse Motors
        fl.setInverted(true);
        fr.setInverted(true);
        br.setInverted(true);

       // pitch.setPosition(0.5);

        //Servos
        //clawServo = new SimpleServo(hardwareMap, "claw", 0, 180);
        //axleServo = new CRServo(hardwareMap, "axle");
        //armServo = new CRServo(hardwareMap, "arm");

        //Gamepads
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        //Subsystems
        driveSubsystem = new DriveSubsystem(fl, fr, bl, br);
        intakeSubsystem = new IntakeSubsystem(intakeMotor);
        liftSubsystem = new LiftSubsystem(liftRight, liftLeft,telemetry);
        //clawSubsystem = new ClawSubsystem(clawServo, axleServo);
        armSubsystem = new ArmSubsystem(arm,pitch);

        //Commands
        clawGrabCommand = new ClawGrabCommand(clawSubsystem);
        clawReleaseCommand = new ClawReleaseCommand(clawSubsystem);
        driveRobotOptimalCommand = new DriveRobotOptimalCommand(driveSubsystem, gamepadEx1);
        manualLiftCommand = new ManualLiftCommand(liftSubsystem, gamepadEx2::getLeftY);
        startIntakeCommand = new StartIntakeCommand(intakeSubsystem);
//        axleMoveCommand = new AxleMoveCommand(clawSubsystem,gamepadEx2, axleServo);
        armMoveCommand = new ArmMoveCommand(armSubsystem, gamepadEx2);

        driveSubsystem.speedMultiplier = -1;

        //Setup up April Tag Detector
        AprilTagDetector.initAprilTag(hardwareMap);

    }

    @Override
    public void run() {
        super.run();
    }

    protected GamepadButton gb1(GamepadKeys.Button button){
        return gamepadEx1.getGamepadButton(button);
    }

    protected GamepadButton gb2(GamepadKeys.Button button){
        return gamepadEx2.getGamepadButton(button);
    }

}