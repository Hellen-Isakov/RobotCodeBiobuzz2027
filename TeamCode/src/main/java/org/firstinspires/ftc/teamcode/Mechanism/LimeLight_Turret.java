package org.firstinspires.ftc.teamcode.Mechanism;

import static dev.nextftc.units.Units.Degrees;

import com.pedropathing.ivy.Command;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.HashMap;
import java.util.List;

import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.units.measuretypes.Angle;

public class LimeLight_Turret implements Mechanism {

    NextMotor turretLL = new NextMotor("3C");
    NextLimelight limelightRed = new NextLimelight("limelight");
    public double tx = 0;
    public double ty = 0;
    public boolean isReset = false;

    public static final double TICKS_PER_ROTATION = 537.6 ; //312rpm motor

//    NextDigitalSensor magnet = new NextDigitalSensor("magnet");

    PIDController pid = new PIDController(new PIDCoefficients(0.01, 0.0, 0.001));

    private boolean hasTarget = false;

    private final HashMap<Integer, String> obeliskMap = new HashMap<>();

    public LimeLight_Turret() { // 30,31,32,33,34,35,36,37
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
//        limelightRed = hardwareMap.get(Limelight3A.class, "limelight");
        limelightRed.start();
        return infinite(()-> limelightRed.startReading(0, 100));
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

    @Override
    public void periodic() {

        LLResult result = limelightRed.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                Pose3D botpose = result.getBotpose();
                Telemetry.log("tx: ", result.getTx());
                Telemetry.log("ty: ", result.getTy());
//                Telemetry.log("BotPose: ", botpose.toString());

            }
        }

        if (!result.isValid()) {
            hasTarget = false;
            Telemetry.log("no target");
            return;
        }


        List<FiducialResult> fiducials = result.getFiducialResults();
        FiducialResult fiducial = fiducials.get(0); // takes the first fiducial
        if (fiducials == null || fiducials.isEmpty()) {
            hasTarget = false;
            Telemetry.log("no target");
            return;
        } else if (fiducials != null) {
            Telemetry.log("");
            Telemetry.log("id: ", fiducial.getFiducialId());
        }

        hasTarget = true;

        Telemetry.update();

        double LLtx = fiducial.getTargetXDegrees();
        Angle LLCurrentPos = turretLL.getEncoderPosition();
        double degPos = LLCurrentPos.into(Degrees);


        double pidOutput = pid.calculate(LLtx - degPos); // setpoint - currentPosition

        if(){
            turretLL.setThrottle(pidOutput);
        }

    }


    public Command llNturret() {

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
}
