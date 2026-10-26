package org.firstinspires.ftc.teamcode.Mechanism;


import com.pedropathing.ivy.Command;

import org.firstinspires.ftc.teamcode.Robot.RobotMap;

import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;


public class Turret implements Mechanism {

    double offset = 0;
    NextMotor turretLL = new NextMotor("0C");
    PIDController pid = new PIDController(new dev.nextftc.control.feedback.PIDCoefficients(
            0.01, 0.0, 0.001
    ));

    public void initialize() {
        turretLL.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.FLOAT);
    }


    public double getAngle() {
        return (turretLL.getPositionConstants() * RobotMap.TURRET_GEAR_RATIO) - offset;
    }

    @Override
    public void periodic() {
//        double PIDPower = pid.calculateOutput(getAngle(), ActiveOpMode.getRuntime());
        double PIDPower = pid.calculate(getAngle());

//        telemetryManager.debug(getAngle());
//        telemetryManager.debug(PID.getTarget());
//        telemetryManager.addData("Target Angle LL: ", pid.getTarget());
//        telemetryManager.addData("Current Angle LL: ", getAngle());
//        telemetryManager.update();
//        if(!ActiveOpMode.opModeInInit()){
//            turretLL.setPower(PIDPower);
//        }

        Telemetry.log("Target LL: ", pid.getError());
        Telemetry.update();
    }
    public double getTarget() {
        return PID.getTarget();
    }

    public void ResetEncoder() {
        offset = (turretLL.getEncoderPosition() * RobotMap.TURRET_GEAR_RATIO );
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public Command FollowTarget() {
        double tx = LimeLight.getTx();
        Telemetry.log("tx", tx);
        Telemetry.update();
        return infinite(()-> pid.setTarget(tx));
    }

    public void moveToSetDegree(double deg) {
         pid.setTarget(deg);
    }

    public void moveDegrees(double deg) {
        moveToSetDegree(getAngle() + deg);
    }

}
