package org.firstinspires.ftc.teamcode.TeleOp;

import static com.pedropathing.ivy.commands.Commands.infinite;

import com.pedropathing.ivy.Scheduler;

import org.firstinspires.ftc.teamcode.Data.StarRobot;
import org.firstinspires.ftc.teamcode.Mechanism.Turret;

import dev.nextftc.robot.opmode.BulkReadHook;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;
import dev.nextftc.robot.triggers.Trigger;

@NextTeleop(name = "A TeleopLL")
public class TeleopLLTest extends NextOpMode {

    public boolean hasMatchStarted = false;

    public StarRobot starRobot;

    public TeleopLLTest(StarRobot starRobot) {
        super(starRobot, BulkReadHook.INSTANCE);
        this.starRobot = starRobot;

        Scheduler.reset();
    }

    @Override
    public void start() {
        Trigger.Companion.getDefaultEventLoop().clear();

        CommandGamepad gp1 = new CommandGamepad(gamepad1);
        CommandGamepad gp2 = new CommandGamepad(gamepad2);


        starRobot.init().schedule();

        starRobot.startDrive(gamepad1);

    }

    @Override
    public void periodic() {
        if (hasMatchStarted) {
            infinite(() -> starRobot.getTurret().moveError(starRobot.getLimeLight().getTx()));
        }
    }

    @Override
    public void end() {

    }
}