package org.firstinspires.ftc.teamcode.Mechanism;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;

public class Drivetrain implements Mechanism {
    public Drivetrain(){
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