package org.firstinspires.ftc.teamcode.Mechanism;

import static dev.nextftc.units.Units.Inches;

import com.pedropathing.ivy.Command;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;

import org.firstinspires.ftc.teamcode.Data.PidController;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.units.measuretypes.Distance;

public class LimeLightRed implements Mechanism {

    NextLimelight limelightRed = new NextLimelight("limelight");
    public double tx = 0;
    public double ty = 0;

    private boolean hasTarget = false;

    private final HashMap<Integer, String> obeliskMap = new HashMap<>();

    public final PidController pidController = new PidController();


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
//        limelight = ActiveOpMode.hardwareMap().get(
//                Limelight3A.class,
//                "limelight"
//        );

        limelightRed.start();
        return infinite(()-> limelightRed.startReading(0, 100));
    }

    @Override
    public void periodic() {
        tx = 0;
        ty = 0;

        Distance distance = limelightRed.getDistance(Inches, 30);
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

}
