package org.firstinspires.ftc.teamcode.Pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static PinpointConfig localizerConfig;


    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("2E");
        c.frontRightName.set("0E");
        c.backLeftName.set("3E");
        c.backRightName.set("1E");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.37399650545990654);
                Controller secondaryTranslationalForward = Controller.proportional(0.13818171539675903);
                Controller primaryTranslationalLateral = Controller.proportional(0.6731272667590381);
                Controller secondaryTranslationalLateral = Controller.proportional(0.24870253877563847);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.013555757984907239));
                c.brake.set(Controller.proportionalFeedforward(0.011522394287171153));

                c.headingFeedback.set(Controller.proportional(5.97225101778926));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.050020892198206934, 0.008510485142089234));

                c.linearBrakeCoefficients.set(Matrix.diag(0.105142575968934, 0.06728132037371413));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0012782884509269876, 0.0015753599728806265));

                c.maxAchievableForwardVelocity.set(39.813022057216);
                c.maxAchievableStrafeVelocity.set(33.33395584926368);
                c.naturalForwardDeceleration.set(26.517759686142686);
                c.naturalStrafeDeceleration.set(73.15748781345577);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}