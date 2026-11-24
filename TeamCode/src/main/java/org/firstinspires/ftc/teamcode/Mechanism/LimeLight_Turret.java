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

//    NextDigitalSensor magnet = new NextDigitalSensor("magnet");

    private boolean hasTarget = false;
    PIDController pid = new PIDController(new PIDCoefficients(0.01, 0.0, 0.001));

    private final HashMap<Integer, String> obeliskMap = new HashMap<>();

    public LimeLight_Turret() { // RED
//        obeliskMap.put(30, "30");
//        obeliskMap.put(31, "31");
//        obeliskMap.put(32, "32");
        obeliskMap.put(33, "33");
//        obeliskMap.put(35, "34");
//        obeliskMap.put(36, "35");
//        obeliskMap.put(37, "36");
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
//            }
//        }

        LLResult resultLL = limelightRed.getLatestResult();

        if (resultLL != null && resultLL.isValid()) {
            double tx = resultLL.getTx();
            double ty = resultLL.getTy();
            double ta = resultLL.getTa();

            Telemetry.log("Target X: ", tx);
            Telemetry.log("Target Y: ", ty);
            Telemetry.log("Target Area: ", ta);
            Telemetry.update();
        } else {
            hasTarget = false;
            Telemetry.log("Limelight", "No Targets");
            Telemetry.update();
        }


        List<FiducialResult> fiducials = resultLL.getFiducialResults();

        if (fiducials == null || fiducials.isEmpty()) {
            hasTarget = false;
            Telemetry.log("no target - fiducials");
            return;
        } else {
            hasTarget = true;
            Telemetry.log("fiducials work");
        }

        hasTarget = true;

        FiducialResult fiducial = fiducials.get(0);

        int id = fiducial.getFiducialId();
        tx = fiducial.getTargetXDegrees();
        ty = fiducial.getTargetYDegrees();

        Telemetry.log("Target ID", id);
        Telemetry.log("Target X", tx);
        Telemetry.log("Target Y", ty);


//        if (obeliskMap.containsKey(id)) {
//            Telemetry.log("Target Color", obeliskMap.get(id));
//            Telemetry.update();
//        }

//        List<FiducialResult> fiducials = result.getFiducialResults();
//        FiducialResult fiducial = fiducials.get(0); // takes the first fiducial
//        //TODO: shows error - line 88. fix
//
//        if (fiducial == null || fiducials.isEmpty()) {
//            hasTarget = false;
//            Telemetry.log("no target f");
//            Telemetry.update();
//            return;
//        } else {
//            hasTarget = true;
//            Telemetry.log("id: ", fiducial.getFiducialId());
//            Telemetry.update();
//            return;
//        }

    }


    public double DegPos() {

        LLResult result = limelightRed.getLatestResult();

        List<FiducialResult> fiducials = result.getFiducialResults();
        FiducialResult fiducial = fiducials.get(0); // takes the first fiducial

        double LLtx = fiducial.getTargetXDegrees();
        Angle LLCurrentPos = turretLL.getEncoderPosition();

        double degPos = LLCurrentPos.into(Degrees);

        return degPos;

//        double pidOutput = pid.calculate(LLtx - degPos); // setpoint - currentPosition
//
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

    public void moveDegrees(double degS) {
        moveToSetDegree(DegPos() + degS);
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
