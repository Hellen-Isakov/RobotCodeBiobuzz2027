package org.firstinspires.ftc.teamcode.Mechanism;


import static dev.nextftc.units.Units.Degrees;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.Data.PidController;
import org.firstinspires.ftc.teamcode.Data.RobotMap;
import org.firstinspires.ftc.teamcode.Data.StarRobot;

import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.sensors.NextDigitalSensor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;


public class Turret implements Mechanism {

    double offset = 0;
    NextDigitalSensor magnet = new NextDigitalSensor("magnet");

    NextMotor turretLL = new NextMotor("0C");
    PIDController pid = new PIDController(new dev.nextftc.control.feedback.PIDCoefficients(
            0.01, 0.0, 0.001
    ));

    public void initialize() {
        turretLL.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.FLOAT);
    }

    public boolean isMagnetPressed(){
        return magnet.isTriggered();
    }

//    public double getAngle() {
//        return (turretLL.getPositionConstants() * RobotMap.TURRET_GEAR_RATIO) - offset;
//    }

    @Override
    public void periodic() {
//        double PIDPower = pid.calculateOutput(getAngle(), ActiveOpMode.getRuntime());

        Telemetry.log("Target LL: ", pid.getError());
        Telemetry.update();
    }
//    public double getTarget() {
//        return pid.();
//    }
//
//    public void ResetEncoder() {
//        offset = (turretLL.getEncoderPosition() * RobotMap.TURRET_GEAR_RATIO );
//    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }
//
//    public Command FollowTarget() {
//        double tx = LimeLight.getTx();
//        Telemetry.log("tx", tx);
//        Telemetry.update();
//        return infinite(()-> PidController.setTarget(tx));
//    }
//
//    public void moveToSetDegree(double deg) {
//         PidController.setTarget(deg);
//    }
//
//    public void moveDegrees(double deg) {
//        moveToSetDegree(getAngle() + deg);
//    }

    public void moveError(double tx) {
        turretLL.setPositionSetpoint(Degrees.of(tx));
    }

}
