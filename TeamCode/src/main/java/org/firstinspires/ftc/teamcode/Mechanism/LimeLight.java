package org.firstinspires.ftc.teamcode.Mechanism;

import static dev.nextftc.units.Units.Meters;

import com.pedropathing.ivy.Command;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;

import org.firstinspires.ftc.teamcode.Data.PidController;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.control.geometry.Pose2d;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.units.measuretypes.Distance;

public class LimeLight implements Mechanism {

    NextLimelight limelight = new NextLimelight("limelight");
    public double tx = 0;
    public double ty = 0;

    private boolean hasTarget = false;

    private final HashMap<Integer, String> obeliskMap = new HashMap<>();

    public final PidController pidController = new PidController();


    public LimeLight() {
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
    public Command initialize() {
//        limelight = ActiveOpMode.hardwareMap().get(
//                Limelight3A.class,
//                "limelight"
//        );

        limelight.startReading(0, 100);
        limelight.start();
        return null;
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

    // TODO: it's from ri30h, check if needed
    public Pose getFinalPose(double targetX, double targetY, Pose currentPose) {

        if (currentPose == null) {
            return null;
        }

        double cameraPitch = 10;
        double ballHeight = 3;
        double cameraHeight = 9.5;

        double cameraOffsetY = 7.5;
        double cameraOffsetX = 0;

        double cameraToBallAngle = Math.toRadians(cameraPitch + targetY);

        double heightDifference = cameraHeight - ballHeight;

        // Checks for impossible math
        if (Math.abs(Math.tan(cameraToBallAngle)) < 0.0001) {
            return null;
        }

        double forwardDistance = heightDifference / Math.tan(cameraToBallAngle);
        double lateralDistance = forwardDistance * Math.tan(Math.toRadians(targetX));

        forwardDistance += cameraOffsetY;
        lateralDistance += cameraOffsetX;

        Vector robotToBallVector = new Vector(forwardDistance, lateralDistance);

        robotToBallVector.toVector2D().rotate(currentPose.heading());

        double fieldX = currentPose.x() + robotToBallVector.toVector2D().x();

        double fieldY = currentPose.y() + robotToBallVector.toVector2D().y();

        return new Pose(fieldX, fieldY, currentPose.heading());
    }

}
