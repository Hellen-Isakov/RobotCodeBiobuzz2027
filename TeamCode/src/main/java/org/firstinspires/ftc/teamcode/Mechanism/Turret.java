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

//import android.annotation.SuppressLint;
//import android.content.Context;
//import com.qualcomm.ftccommon.FtcEventLoop;
//import com.qualcomm.hardware.lynx.LynxModule;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
//import dev.frozenmilk.sinister.sdk.apphooks.OnCreateEventLoop;
//import dev.nextftc.hardware.lynx.NextLynxModule;
//import dev.nextftc.hardware.util.LazyHardware;
//import dev.nextftc.units.celsius;
//import dev.nextftc.units.measuretypes.Temperature;
//import dev.nextftc.units.measuretypes.Voltage;
//import dev.nextftc.units.volts;
//import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
//import java.util.Collections;
//import java.util.WeakHashMap;



public class Turret implements Mechanism {

    double offset = 0;
    NextDigitalSensor magnet = new NextDigitalSensor("magnet");

    NextMotor turretLL = new NextMotor("3C");

    PIDController pid = new PIDController(new dev.nextftc.control.feedback.PIDCoefficients(
            0.01, 0.0, 0.001
    ));

    public Command initialize() {
        return infinite(()-> turretLL.setZeroPowerBehavior(NextMotor.ZeroPowerBehavior.BRAKE));
    }

    public boolean isMagnetPressed(){
        return magnet.isTriggered();
    }

//    public double getAngle() {
//        return (turretLL.getPositionConstants() * RobotMap.TURRET_GEAR_RATIO) - offset;
//    }

    public Command tryout(double pow) {
        return instant(()-> turretLL.setThrottle(pow));
    }

    @Override
    public void periodic() {
        Telemetry.log("Target LL: ", pid.getError());
        Telemetry.update();
    }

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

    public Command moveError(double tx) {
        return instant(()-> turretLL.setAnglePerCount(Degrees.of(tx)));
    }

}
