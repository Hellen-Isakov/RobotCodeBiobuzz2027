package org.firstinspires.ftc.teamcode.TeleOp;

import com.pedropathing.ivy.Scheduler;

import org.firstinspires.ftc.teamcode.Data.Alliance;
import org.firstinspires.ftc.teamcode.Data.StarRobot;

import dev.nextftc.robot.Telemetry;
import dev.nextftc.robot.opmode.BulkReadHook;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;

@NextTeleop(name = "A TeleopLL blue")
public class TeleopLLBlue extends NextOpMode {

    public boolean hasMatchStarted = false;
    public Alliance alliance = Alliance.BLUE;

    public StarRobot starRobot;

    public TeleopLLBlue(StarRobot starRobot) {
        super(starRobot, BulkReadHook.INSTANCE);
        this.starRobot = starRobot;

        Scheduler.reset();
    }

    @Override
    public void start() {
        starRobot.getLimeLightTurret().RSTtimer();
        starRobot.getLimeLightTurret().periodic();
        starRobot.getLimeLightTurret().ResetEncoder();

        hasMatchStarted = true;
        starRobot.init().schedule();

//        CommandGamepad gp1 = new CommandGamepad(gamepad1);
//        starRobot.startDrive(gamepad1);

    }

    @Override
    public void periodic() {
        if (hasMatchStarted) {
//            starRobot.getLimeLightTurret().periodic();

            starRobot.getLimeLightTurret().moveDegrees();
            Telemetry.log("matchStarted");
            Telemetry.update();
        }
    }

    @Override
    public void end() {

    }
}