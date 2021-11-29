package org.wheelerschool.robotics.comp.auto;

import static com.sun.tools.doclint.Entity.and;
import static com.sun.tools.doclint.Entity.ge;
import static com.sun.tools.doclint.Entity.pi;
import static com.sun.tools.doclint.Entity.tau;
import static java.lang.Math.floor;
import static java.lang.Math.round;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.sun.tools.javac.comp.Todo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.wheelerschool.robotics.comp.chassis.Meccanum;

@Autonomous
public class AutoNav5 extends LinearOpMode {

    Meccanum meccanum = new Meccanum();

    @Override
    public void runOpMode() throws InterruptedException {
        meccanum.init(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            executeAutomaticSequence2();


        }
    }
    private void executeAutomaticSequence2(){

        // auto for near carousel

        meccanum.motorDriveBackEncoded(meccanum.NORMAL_SPEED, 0);

        meccanum.spinnySpinEncoded(meccanum.OPTIMAL_SPINNER_POWER, 0);

        meccanum.motorDriveRightEncoded(meccanum.NORMAL_SPEED, 0);

        meccanum.motorDriveBackEncoded(meccanum.NORMAL_SPEED, 0);

    }


}
