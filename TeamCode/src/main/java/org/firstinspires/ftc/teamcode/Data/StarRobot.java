package org.firstinspires.ftc.teamcode.Data;

import static com.pedropathing.ivy.groups.Groups.parallel;

import androidx.annotation.NonNull;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mechanism.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanism.LimeLight;
import org.firstinspires.ftc.teamcode.Mechanism.Turret;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;
import dev.nextftc.robot.drive.DriveCommands;

public class StarRobot implements NextRobot {
    public Alliance alliance;
    public final LimeLight limeLight = new LimeLight();
    public final Turret turret = new Turret();
    public final Drivetrain drivetrain = new Drivetrain();

    public LimeLight getLimeLight() {
            return limeLight;
        }
    public Turret getTurret() {
        return turret;
    }
    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }
    public Alliance getAlliance() {
        return alliance;
    }

    public void startDrive(Gamepad gamepad1) {
        DriveCommands.mecanumDrive(
                drivetrain.frontLeft,
                drivetrain.frontRight,
                drivetrain.backLeft,
                drivetrain.backRight,
                gamepad1
        ).schedule();
    }

    public Command init(){
        return parallel(
                limeLight.initialize()
        );
    }

    @NonNull
    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(limeLight, turret, drivetrain);
    }

}

