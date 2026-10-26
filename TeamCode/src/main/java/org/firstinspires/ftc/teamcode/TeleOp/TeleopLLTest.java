package org.firstinspires.ftc.teamcode.TeleOp;

import static com.pedropathing.ivy.commands.Commands.instant;
import static dev.nextftc.units.Units.RotationsPerMinute;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.robot.NextRobot;
import dev.nextftc.robot.opmode.BulkReadHook;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.triggers.CommandGamepad;
import dev.nextftc.robot.triggers.Trigger;

@TeleOp(name = "A TeleopLL")
public class TeleopLLTest extends NextOpMode {
    public TeleopLLTest(Robot robot) { super((NextRobot) robot, BulkReadHook.INSTANCE); }

    public boolean hasMatchStarted = false;

    public Robot robot;

    public TeleopLLTest(Robot robot) {
        super(robot);
        this.robot = robot;

        Scheduler.reset();
    }

    @Override
    public void start() {
        Trigger.Companion.getDefaultEventLoop().clear();

        CommandGamepad gp1 = new CommandGamepad(gamepad1);
        CommandGamepad gp2 = new CommandGamepad(gamepad2);


        robot.init().schedule();

        hazmatRobot.startDrive(gamepad1);

        gp1.leftBumper().onTrue(instant(()->hazmatRobot.getIntake().cycle()));
        gp1.rightBumper().onTrue(hazmatRobot.launch());

        gp1.dpadUp().onTrue(hazmatRobot.getLauncher().setPollen());
        gp1.dpadDown().onTrue(hazmatRobot.getLauncher().setNectar());

/*
        gp2.dpadUp().onTrue(hazmatRobot.getLauncher().incrementPower());
        gp2.dpadDown().onTrue(hazmatRobot.getLauncher().decrementPower());*/

        gp2.circle().onTrue(hazmatRobot.getTransfer().open());
        gp2.square().onTrue(hazmatRobot.getTransfer().close());

    }

    @Override
    public void periodic() {
        telemetry.addData("Intake Speed", hazmatRobot.getIntake().getSpeed());
        telemetry.addData("Lift Position", hazmatRobot.getLift().getPos());
        telemetry.addData("Bucket Position", hazmatRobot.getTransfer().getRampServo().getPosition());
        telemetry.addData("Launcher Motor Velocity", hazmatRobot.getLauncher().getLauncherMotor().getEncoderVelocity().into(RotationsPerMinute));

        telemetry.update();
    }

    @Override
    public void end() {
    }
}