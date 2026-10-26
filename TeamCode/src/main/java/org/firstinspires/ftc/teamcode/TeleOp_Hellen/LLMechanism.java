package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import static dev.nextftc.units.Units.Centimeters;
import static dev.nextftc.units.Units.Inches;
import static dev.nextftc.units.Units.Meters;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dev.nextftc.control.geometry.Pose2d;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.units.measuretypes.Distance;

public class LLMechanism implements Mechanism {

//    public static final LLMechanism INSTANCE = new LLMechanism();

    // Using your configuration name
    NextLimelight limelight = new NextLimelight("limelight");
    private double tx = 0;
    private double ty = 0;

    private boolean hasTarget = false;

    private final HashMap<Integer, String> obeliskMap = new HashMap<>();

    public LLMechanism() {
        obeliskMap.put(30, "RED SCORING");
        obeliskMap.put(31, "RED SCORING");
        obeliskMap.put(32, "RED SCORING");
        obeliskMap.put(33, "RED SCORING");
        obeliskMap.put(35, "RED SCORING");
        obeliskMap.put(36, "RED SCORING");
        obeliskMap.put(37, "RED SCORING");

        obeliskMap.put(38, "BLUE SCORING");
        obeliskMap.put(39, "BLUE SCORING");
        obeliskMap.put(40, "BLUE SCORING");
        obeliskMap.put(41, "BLUE SCORING");
        obeliskMap.put(42, "BLUE SCORING");
        obeliskMap.put(43, "BLUE SCORING");
        obeliskMap.put(44, "BLUE SCORING");
        obeliskMap.put(45, "BLUE SCORING");
    }

//    @Override
    public void initialize() {
//        limelight = ActiveOpMode.hardwareMap().get(
//                Limelight3A.class,
//                "limelight"
//        );

        limelight.startReading(0, 100);
        limelight.start();
    }

    @Override
    public void periodic() {
        tx = 0;
        ty = 0;

        Distance distance = limelight.getDistance(Meters, 33); //Defaults to Inches
        if (distance != null) {
            hasTarget = true;
        }

        Pose2d pose = limelight.getPose();
        if (pose != null) {
            // ...
        }

        Pose2d pedroPose = limelight.getPedroPose();
        if (pedroPose != null) {
            // ...
        }

        LLResult result = limelight.getLatestResult();
        if (result == null || !result.isValid()) {
            hasTarget = false;
            return;
        }

        List<FiducialResult> fiducials = result.getFiducialResults();
        if (fiducials == null || fiducials.isEmpty()) {
            hasTarget = false;
            return;
        }

        hasTarget = true;

        // לוקח את ה-Fiducial הראשון
        FiducialResult fiducial = fiducials.get(0);

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

    public double getTx() { return tx; }

    public double getTy() {
        return ty;
    }

    public boolean hasTarget() {
        return hasTarget;
    }

}
