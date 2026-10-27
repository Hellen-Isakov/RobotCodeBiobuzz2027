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
    public NextMotor backLeft = new NextMotor("3E");
    public NextMotor frontLeft = new NextMotor("2E");
    public NextMotor backRight =  new NextMotor("1E");
    public NextMotor frontRight = new NextMotor("0E");

}