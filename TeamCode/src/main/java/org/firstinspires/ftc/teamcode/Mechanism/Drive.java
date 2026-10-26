package org.firstinspires.ftc.teamcode.Mechanism;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot.RobotMap;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class Drive implements Mechanism {
    public Drive(){
        backLeft.setDirection(NextMotor.Direction.REVERSE);
        frontLeft.setDirection(NextMotor.Direction.FORWARD);
        backRight.setDirection(NextMotor.Direction.FORWARD);
        frontRight.setDirection(NextMotor.Direction.REVERSE);
    }
    public final NextMotor backLeft = new NextMotor(RobotController.expansionHub(), 3);
    public final NextMotor frontLeft = new NextMotor(RobotController.expansionHub(), 2);
    public final NextMotor backRight =  new NextMotor(RobotController.expansionHub(), 1);
    public final NextMotor frontRight = new NextMotor(RobotController.expansionHub(), 0);

}