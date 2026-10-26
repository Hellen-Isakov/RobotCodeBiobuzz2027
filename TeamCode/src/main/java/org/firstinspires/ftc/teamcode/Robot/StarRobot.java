package org.firstinspires.ftc.teamcode.Robot;

import androidx.annotation.NonNull;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.Mechanism.LimeLight;
import org.firstinspires.ftc.teamcode.Mechanism.Turret;
import org.firstinspires.ftc.teamcode.Pedro.Constants;

import java.util.Set;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class StarRobot implements NextRobot {
    private Follower follower;
    private Alliance alliance;
    private final LimeLight limeLight = new LimeLight();
    private final Turret turret = new Turret();

    public LimeLight getLimeLight() {
            return limeLight;
        }

    public Turret getTurret(){
            return turret;
        }


    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public Follower getFollower() {
         if (follower == null) {
            follower = Constants.create(RobotController.hardwareMap());
         }
         return follower;
    }

    @NonNull
    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(limeLight, turret);
    }

}

