package org.firstinspires.ftc.teamcode.TeleOp;

import static com.pedropathing.ivy.commands.Commands.infinite;

import com.pedropathing.ivy.Scheduler;

import org.firstinspires.ftc.teamcode.Data.StarRobot;

import dev.nextftc.robot.opmode.BulkReadHook;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;

@NextTeleop(name = "A TeleopLL")
public class TeleopLLRed extends NextOpMode {

    public boolean hasMatchStarted = false; 

    public StarRobot starRobot;

    public TeleopLLRed(StarRobot starRobot) {
        super(starRobot, BulkReadHook.INSTANCE);
        this.starRobot = starRobot;

        Scheduler.reset();
    }

    @Override
    public void start() {
        hasMatchStarted = true;

        CommandGamepad gp1 = new CommandGamepad(gamepad1);
        CommandGamepad gp2 = new CommandGamepad(gamepad2);

        starRobot.init().schedule();
//        starRobot.startDrive(gamepad1); // TODO: needs to be tuned

//        DrivePowers powers = ManualDrive.fieldCentric(
//                -gamepad1.left_stick_y,
//                gamepad1.left_stick_x,
//                gamepad1.right_stick_x,
//                follower.pose().heading()
//        );
    }

    @Override
    public void periodic() {
        if (hasMatchStarted) {
            infinite(() -> starRobot.getTurret().moveError(
                    starRobot.getLimeLightRed().getTx()));
        }
    }

    @Override
    public void end() {

    }
}