package org.firstinspires.ftc.teamcode.TeleOp_Hellen;

import com.bylazar.configurables.annotations.Configurable;
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

@TeleOp(name = "A TeleopLL")
public class TeleopLLTest extends NextOpMode {
    public TeleopLLTest(Robot robot) { super((NextRobot) robot, BulkReadHook.INSTANCE); }

    public boolean hasMatchStarted = false;

    @Override
    public void onInit() {
    }

    @Override
    public void onUpdate() {
        if (hasMatchStarted) {
            //TurretSubsystemLL.INSTANCE.FollowPointLL();
//            telemetry.addData("motor pow:", TurretMechanism.INSTANCE.turretLL.getPower());
//            telemetry.addData("Motor pos: ", TurretMechanism.INSTANCE.getAngle());
//            telemetry.addData("Motor Target: ", TurretMechanism.INSTANCE.getTarget());
//            telemetry.update();
//            new InstantCommand(()-> TurretMechanism.INSTANCE.moveDegrees(LLMechanism.INSTANCE.getTx())).schedule();

        }
    }

    @Override
    public void onStartButtonPressed() {
        hasMatchStarted = true;
    }


}
