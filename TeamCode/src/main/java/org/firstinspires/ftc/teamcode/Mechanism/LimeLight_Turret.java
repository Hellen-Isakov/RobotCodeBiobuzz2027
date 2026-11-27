package org.firstinspires.ftc.teamcode.Mechanism;

import static dev.nextftc.units.Units.Degrees;

import com.pedropathing.ivy.Command;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Data.Alliance;
import org.firstinspires.ftc.teamcode.Data.RingoPIDController;
import org.firstinspires.ftc.teamcode.Data.RobotMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.nextftc.hardware.actuators.NextMotor;
import dev.nextftc.hardware.webcams.NextLimelight;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.Telemetry;
import dev.nextftc.units.measuretypes.Angle;

public class LimeLight_Turret implements Mechanism {

    private final ElapsedTime timer = new ElapsedTime();
    double runtime = timer.seconds();


    NextMotor turretLL = new NextMotor("3C");
    public LLResult llResult;

    NextLimelight limelight = new NextLimelight("limelight");

    double turretCurrentPos;
    public double tx = 0;
    public double ta = 0;
    public double ty = 0;
    double offset = 0;
//    NextDigitalSensor magnet = new NextDigitalSensor("magnet");

    private boolean hasTarget = false;
    private Alliance alliance;
    private final RingoPIDController pid = new RingoPIDController(0.01, 0.0, 0.001);


    public LimeLight_Turret(Alliance alliance) {
        this.alliance = alliance;
    }

    public Command initialize() {
        limelight.start();
        return infinite(()-> limelight.startReading(alliance.getPipeline(), 100));
    }

    public void RSTtimer(){
        timer.reset();
    }
    public List<Integer> getDetectedTagIds(Limelight3A limelight) {
        List<Integer> tagIds = new ArrayList<>();
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            for (LLResultTypes.FiducialResult tag : result.getFiducialResults()) {
                tagIds.add(tag.getFiducialId());
            }
        }

        return tagIds;
    }

    @Override
    public void periodic() {
        runtime = timer.seconds();
        Telemetry.log("Target X: ", tx);
        Telemetry.log("Target Y: ", ty);
        Telemetry.log("encoder: ", getAngle());
        Telemetry.log("runtime ", runtime);
        Telemetry.log("Target: ", pid.getTarget());
        Telemetry.log("Current id detected: ", getDetectedTagIds(limelight.getCamera()));
        Telemetry.log(" limelight status: ", limelight.getLatestResult() != null && limelight.getLatestResult().isValid());



        double PIDPower = pid.calculateOutput(getAngle(), runtime);

        //turretLL.setThrottle(PIDPower);

        LLResult resultLL = limelight.getLatestResult();

        if (resultLL != null && resultLL.isValid()) {
            tx = resultLL.getTx();
            ty = resultLL.getTy();
            ta = resultLL.getTa();
            Telemetry.log("PID power: ", PIDPower);


            Telemetry.update();
        } else {
            hasTarget = false;
            Telemetry.log("Limelight", "No Targets");
            Telemetry.update();
        }

        Angle LLCurrentPos = turretLL.getEncoderPosition();
        turretCurrentPos = LLCurrentPos.into(Degrees);


        /*List<FiducialResult> fiducials = resultLL.getFiducialResults();

        hasTarget = true;

        FiducialResult fiducial = fiducials.get(0);

//        int id = fiducial.getFiducialId();
//        tx = fiducial.getTargetXDegrees();
//        ty = fiducial.getTargetYDegrees();

//        Telemetry.log("Target ID", id);
        Telemetry.log("Target X", tx);
        Telemetry.log("Target Y", ty);
*/

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

//    public double getTx() {
//        return tx;
//    }

    public void ResetEncoder() {
        offset = (turretCurrentPos * RobotMap.TURRET_GEAR_RATIO );
    }

    public double getAngle() {
        return (turretCurrentPos * RobotMap.TURRET_GEAR_RATIO) - offset;
    }

    public void moveToSetDegree(double deg) {
        pid.setTarget(deg);
    }

    public void moveDegrees() {
        moveToSetDegree(getAngle() + tx);
    }



//    public void DegPos() {
//
////        LLResult result = limelightRed.getLatestResult();
//
////        List<FiducialResult> fiducials = result.getFiducialResults();
////        FiducialResult fiducial = fiducials.get(0); // takes the first fiducial
//
////        double LLtx = fiducial.getTargetXDegrees();
//
//
//
//
//
////        return degPos;
//        double pidOutput = pid.calculate(tx); // setpoint - currentPosition
//
//        turretLL.setThrottle(pidOutput);
//
////
////        if(pidOutput > 5){
////            turretLL.setThrottle(pidOutput);
////        } else if (pidOutput < -5) {
////            turretLL.setThrottle(pidOutput);
////        } else {
////            turretLL.setThrottle(0);
////        }
//
//    }
}
