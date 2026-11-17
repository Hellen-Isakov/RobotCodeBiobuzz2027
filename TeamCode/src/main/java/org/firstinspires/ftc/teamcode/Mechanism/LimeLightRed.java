package org.firstinspires.ftc.teamcode.Mechanism;

import static dev.nextftc.units.Units.Degrees;
import static dev.nextftc.units.Units.Inches;

import com.pedropathing.ivy.Command;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.Data.PidController;
import org.firstinspires.ftc.teamcode.Data.RobotMap;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.units.measuretypes.Angle;
import dev.nextftc.units.measuretypes.Distance;

public class LimeLightRed implements Mechanism {

    NextLimelight limelightRed = new NextLimelight("limelight");
    public double tx = 0;
    public double ty = 0;
    public static final double TICKS_PER_ROTATION = 537.6 ; //312rpm motor


    double offset = 0;
//    NextDigitalSensor magnet = new NextDigitalSensor("magnet");

    NextMotor turretLL = new NextMotor("3C");

    PIDController pid = new PIDController(new dev.nextftc.control.feedback.PIDCoefficients(
            0.01, 0.0, 0.001
    ));

    private boolean hasTarget = false;

    private final HashMap<Integer, String> obeliskMap = new HashMap<>();

    public LimeLightRed() { // 30,31,32,33,34,35,36,37
//        obeliskMap.put(30, "RED SCORING");
//        obeliskMap.put(31, "RED SCORING");
//        obeliskMap.put(32, "RED SCORING");
        obeliskMap.put(33, "RED SCORING");
//        obeliskMap.put(35, "RED SCORING");
//        obeliskMap.put(36, "RED SCORING");
//        obeliskMap.put(37, "RED SCORING");
    }

//    @Override
    public Command initialize() {
        limelightRed.start();
        return infinite(()-> limelightRed.startReading(0, 100));
    }


    Angle anglePerCount = Degrees.of(360.0 / TICKS_PER_ROTATION);
    public void setPos(double goalTicks) {
        Angle setpointAngle = Degrees.of(360.0 * goalTicks / TICKS_PER_ROTATION);
        turretLL.setPositionSetpoint(setpointAngle);
    }

    public String setPoseS() {
        return "try ri30h thingy: " + turretLL.getEncoderPosition().getMagnitude();
    }



//    double output = pid.calculate(setpoint - currentPosition);
//
//    public double getAngle() {
//        turretLL.getAbsoluteEncoderPosition();
//        return turretLL.getAbsoluteEncoderPosition();
//    }
//
//    public void moveToSetDegree(double deg) {
//        turretLL.setTarget(deg);
//    }

//    public void moveDegrees(double deg) {
//        moveToSetDegree(getAngle() + deg);
//    }

//    public Command moveError(double tx) {
//        return infinite(()-> ;
//    }

    @Override
    public void periodic() {
        Telemetry.log("pid error: ", pid.getCoefficients());
        Telemetry.log("pid error: ", pid.getLastError());
        Telemetry.log("turret encoder pos (radians): ", turretLL.getAbsoluteEncoderPosition());
        Telemetry.log("turret pow: ", turretLL.getThrottle());

        Telemetry.update();


        Distance distance = limelightRed.getDistance(Inches, 33);
        if (distance != null) {
            hasTarget = true;
        }

//        Pose2d pose = limelight.getPose();
//        if (pose != null) {
//            // ...
//        }
//
//        Pose2d pedroPose = limelight.getPedroPose();
//        if (pedroPose != null) {
//            // ...
//        }

        LLResult result = limelightRed.getLatestResult();
        if (!result.isValid()) {
            hasTarget = false;
            return;
        }

        List<FiducialResult> fiducials = result.getFiducialResults();
        if (fiducials == null || fiducials.isEmpty()) {
            hasTarget = false;
            return;
        }

        hasTarget = true;


        FiducialResult fiducial = fiducials.get(0); // takes the first fiducial

        int id = fiducial.getFiducialId();
        tx = fiducial.getTargetXDegrees();
        ty = fiducial.getTargetYDegrees();

        Telemetry.log("Target ID: ", id);
        Telemetry.log("Target X: ", tx);
        Telemetry.log("Target Y: ", ty);

        if (obeliskMap.containsKey(id)) {
            Telemetry.log("Target Color", Objects.requireNonNull(obeliskMap.get(id)));
        } else {
            Telemetry.log("no target");
        }

        Telemetry.update();

    }

    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public boolean hasTarget() {
        return hasTarget;
    }


    //------------------------------ from turret ------------------------------
//    public boolean isMagnetPressed(){
//        return magnet.isTriggered();
//    }

    public Command tryout(double pow) {
        return instant(()-> turretLL.setThrottle(pow));
    }

//    public double getOffset() {
//        return offset;
//    }
//
//    public void setOffset(double offset) {
//        this.offset = offset;
//    }

}
