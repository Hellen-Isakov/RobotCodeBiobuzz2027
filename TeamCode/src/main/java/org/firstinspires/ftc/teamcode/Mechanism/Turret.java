package org.firstinspires.ftc.teamcode.Mechanism;


import static dev.nextftc.units.Units.Degrees;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.Data.PidController;
import org.firstinspires.ftc.teamcode.Data.RobotMap;
import org.firstinspires.ftc.teamcode.Data.StarRobot;

import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.sensors.NextDigitalSensor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.units.Unit;
import dev.nextftc.units.Units;
import dev.nextftc.units.measuretypes.Angle;



public class Turret implements Mechanism {

    NextMotor turret = new NextMotor("3C");

    PIDController pid = new PIDController(new dev.nextftc.control.feedback.PIDCoefficients(
            0.01, 0.0, 0.001
    ));
//    double output = pid.calculate(setpoint - currentPosition);

    public Command initialize() {
        return infinite(() -> turret.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.BRAKE));
    }

    public Command tryout(double pow) {
        return instant(() -> turret.setThrottle(pow));
    }

    @Override
    public void periodic() {

    }

}