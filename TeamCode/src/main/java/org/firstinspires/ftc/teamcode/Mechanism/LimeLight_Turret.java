package org.firstinspires.ftc.teamcode.Mechanism;

import static dev.nextftc.units.Units.Degrees;

import com.pedropathing.ivy.Command;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;

import org.firstinspires.ftc.teamcode.Data.RingoPIDController;

import java.util.HashMap;
import java.util.List;

import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.units.measuretypes.Angle;

public class LimeLight_Turret implements Mechanism {

    NextMotor turretLL = new NextMotor("3C");
    public LLResult llResult;
    public RingoPIDController ringoPIDController;

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


    @Override
    public void periodic() {

//        LLResult result = limelightRed.getLatestResult();
//        result.getPipelineIndex();
//        if (result != null) {
//            if (result.isValid()) {
//                Pose3D botpose = result.getBotpose();
//                Telemetry.log("tx: ", result.getTx());
//                Telemetry.log("ty: ", result.getTy());
//                Telemetry.log("BotPose: ", botpose.toString());
//
//            }
//        }

        LLResult result = limelightRed.getLatestResult();
        result.getPipelineIndex();

        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            Telemetry.log("Target X", tx);
            Telemetry.log("Target Y", ty);
            Telemetry.log("Target Area", ta);
        } else {
            hasTarget = false;
            Telemetry.log("Limelight", "No Targets");
        }


        List<FiducialResult> fiducials = result.getFiducialResults();
        FiducialResult fiducial = fiducials.get(0); // takes the first fiducial

        if (fiducials == null || fiducials.isEmpty()) {
            hasTarget = false;
            Telemetry.log("no target f");
            return;
        } else {
            Telemetry.log("id: ", fiducial.getFiducialId());
        }


        hasTarget = true;
        Telemetry.update();

    }


    public double degPos() { //llConnectedToTurret

        LLResult result = limelightRed.getLatestResult();

        List<FiducialResult> fiducials = result.getFiducialResults();
        FiducialResult fiducial = fiducials.get(0); // takes the first fiducial

        double LLtx = fiducial.getTargetXDegrees();
        Angle LLCurrentPos = turretLL.getEncoderPosition();

        double degPos = LLCurrentPos.into(Degrees);

        return degPos;

//        double pidOutput = pid.calculate(LLtx - degPos); // setpoint - currentPosition

//        if(pidOutput > 5){
//            turretLL.setThrottle(pidOutput);
//        } else if (pidOutput < -5) {
//            turretLL.setThrottle(pidOutput);
//        } else {
//            turretLL.setThrottle(0);
//        }

    }

    public void moveToSetDegree(double deg) {
        ringoPIDController.setTarget(deg);
    }

    public void moveDegrees(double deg) {
        moveToSetDegree(degPos() + deg);
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
