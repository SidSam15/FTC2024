package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.gampad;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.packet;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.time;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.voltage;
import static org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive.imuMultiply;
import static java.lang.Math.min;
import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robots.BradBot;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class RL23 {
    boolean logi=false, isRight;
    LinearOpMode op;
    BradBot robot;
    int bark = 0, delaySec =0;
    TrajectorySequence[] spikey = new TrajectorySequence[3];
    TrajectorySequence[] intake = new TrajectorySequence[3];
    TrajectorySequence[] backToStack = new TrajectorySequence[3];
    TrajectorySequence[] droppy = new TrajectorySequence[3];
    TrajectorySequence[] drop = new TrajectorySequence[3];
    TrajectorySequence[] park= new TrajectorySequence[3], parkLeft= new TrajectorySequence[3];





    public RL23(LinearOpMode op, boolean isLogi){
        logi = isLogi;
        this.op=op;
        robot = new BradBot(op, false,isLogi);
        Pose2d startPose = new Pose2d(-32,-61.5,toRadians(-90));
        robot.roadrun.setPoseEstimate(startPose);
        imuMultiply = 1.039 + .002*(robot.getVoltage()-12.5);
        spikey[0] = robot.roadrun
                .trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-45, -34.25, toRadians(-90)), toRadians(90))
                .build();

        spikey[1] = robot.roadrun
                .trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-39,-34,toRadians(-90)))
                .addTemporalMarker(robot::done)
                .build();

        spikey[2] = robot.roadrun
                .trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-33,-38, toRadians(-180)), toRadians(0))
                .build();
        intake[0] = robot.roadrun
                .trajectorySequenceBuilder(spikey[0].end())
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-46, -45.25, toRadians(220)), toRadians(-160))
                .lineToLinearHeading(new Pose2d(-55.8, -35.25, toRadians(180)))
                .build();
        intake[1] = robot.roadrun
                .trajectorySequenceBuilder(spikey[1].end())
                .lineToLinearHeading(new Pose2d(-55.8,-35.25, toRadians(-180)))
                .build();
        intake[2] = robot.roadrun
                .trajectorySequenceBuilder(spikey[2].end())
                .lineToLinearHeading(new Pose2d(-55.8,-35.25, toRadians(-180)))
                .addTemporalMarker(robot::done)
                .build();


        if (!isLogi) {
            droppy[0] =
                    robot
                            .roadrun
                            .trajectorySequenceBuilder(intake[0].end())
                            .setReversed(true)
                            .splineToConstantHeading(new Vector2d(-35,-58.5),toRadians(0))
                            .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(25+(voltage-12.5)*10,5,15))
                            .splineToConstantHeading(new Vector2d(-30,-58.5),toRadians(0))
                            .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80,5,15))
                            .splineToConstantHeading(new Vector2d(5,-59.5),toRadians(0))
                            .splineToConstantHeading(new Vector2d(46.5,-30.25),toRadians(0))
                            .build();


            droppy[1] =
                    robot
                            .roadrun
                            .trajectorySequenceBuilder(intake[1].end())
                            .setReversed(true)
                            .splineToConstantHeading(new Vector2d(-35,-58.5),toRadians(0))
                            .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(25+(voltage-12.5)*10,5,15))
                            .splineToConstantHeading(new Vector2d(-30,-58.5),toRadians(0))
                            .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80,5,15))
                            .splineToConstantHeading(new Vector2d(5,-59.5),toRadians(0))
                            .splineToConstantHeading(new Vector2d(46.5,-35.25),toRadians(0))
                            .build();

            droppy[2] =
                    robot
                            .roadrun
                            .trajectorySequenceBuilder(intake[2].end())
                            .setReversed(true)
                            .splineToConstantHeading(new Vector2d(-35,-58.5),toRadians(0))
                            .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(25+(voltage-12.5)*10,5,15))
                            .splineToConstantHeading(new Vector2d(-30,-58.5),toRadians(0))
                            .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80,5,15))
                            .splineToConstantHeading(new Vector2d(5,-59.5),toRadians(0))
                            .splineToConstantHeading(new Vector2d(47,-36.75),toRadians(0))
                            .build();

        } else{


        }

        backToStack[0] = robot.roadrun
                .trajectorySequenceBuilder(droppy[0].end())
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(25,-58),toRadians(180))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(35,5,15))
                .splineToConstantHeading(new Vector2d(20,-57),toRadians(180))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80,5,15))
                .splineToConstantHeading(new Vector2d(-25,-57),toRadians(180))
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(35))
                .splineToConstantHeading(new Vector2d(-28,-57),toRadians(180))
                .splineToConstantHeading(new Vector2d(-56,-33.25),toRadians(180))
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(50))
                .build();
        backToStack[1] = robot.roadrun
                .trajectorySequenceBuilder(droppy[0].end())
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(25,-58),toRadians(180))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(35,5,15))
                .splineToConstantHeading(new Vector2d(20,-58),toRadians(180))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80,5,15))
                .splineToConstantHeading(new Vector2d(-25,-58),toRadians(180))
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(35))
                .splineToConstantHeading(new Vector2d(-28,-58),toRadians(180))
                .splineToConstantHeading(new Vector2d(-56,-33.25),toRadians(180))
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(50))
                .build();
        backToStack[2] = robot.roadrun
                .trajectorySequenceBuilder(droppy[2].end())
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(20, -57.5), toRadians(180))
//                .splineTo(new Vector2d(10, -57.5), toRadians(186))
//                .splineToConstantHeading(new Vector2d(0, -58), toRadians(180))

                .splineToConstantHeading(new Vector2d(-26, -58.5), toRadians(180))
                .splineToConstantHeading(new Vector2d(-51, -32.25), toRadians(180))
                .addTemporalMarker(robot::done)
                .build();
        drop[0] = robot.roadrun.trajectorySequenceBuilder(backToStack[0].end())
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35,-58.5),toRadians(0))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(30+(voltage-12.5)*10,5,15))
                .splineToConstantHeading(new Vector2d(-30,-58.5),toRadians(0))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80,5,12))
                .splineToConstantHeading(new Vector2d(15,-59.5),toRadians(0))
                .splineToConstantHeading(new Vector2d(47,-37.5),toRadians(0))
                .build();
        drop[1] = robot.roadrun.trajectorySequenceBuilder(backToStack[0].end())
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35,-58),toRadians(0))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(35,5,15))
                .splineToConstantHeading(new Vector2d(-27,-58),toRadians(0))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80,5,12))
                .splineToConstantHeading(new Vector2d(5,-58),toRadians(0))
                .splineToConstantHeading(new Vector2d(47,-38),toRadians(0))
                .build();
        drop[2] = robot.roadrun.trajectorySequenceBuilder(backToStack[2].end())
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-40, -53.5), toRadians(-15))
                .splineToConstantHeading(new Vector2d(-20, -55.5), toRadians(0))
                .splineToConstantHeading(new Vector2d(6, -56.5), toRadians(0))
                .splineToConstantHeading(new Vector2d(47, -37), toRadians(0))
                .addTemporalMarker(robot::done)
                .build();
    park[1] = robot.roadrun.trajectorySequenceBuilder(drop[1].end())
            .lineToLinearHeading(new Pose2d(43.8,-38, toRadians(-180)))
            .lineToLinearHeading(new Pose2d(45, -60,toRadians(-180)))
            .build();
        park[0] = robot.roadrun.trajectorySequenceBuilder(drop[0].end())
                .lineToLinearHeading(new Pose2d(43.8,-38.25, toRadians(-180)))
                .build();
        parkLeft[1] = robot.roadrun.trajectorySequenceBuilder(drop[1].end())
                .lineToLinearHeading(new Pose2d(43.8,-38, toRadians(-180)))
                .lineToLinearHeading(new Pose2d(45, -11,toRadians(-180)))
                .build();
//
//
//
//        parky[0] = robot.roadrun
//                .trajectorySequenceBuilder(droppy[0].end())
//                .lineToLinearHeading(new Pose2d(43.3,-29,toRadians(-180)))
//                .lineToLinearHeading(new Pose2d(50,-60,toRadians(-180)))
//                .lineToLinearHeading(new Pose2d(55,-60,toRadians(-180)))
//                .build();
//
//        parky[1] = robot.roadrun
//                .trajectorySequenceBuilder(droppy[1].end())
//                .lineToLinearHeading(new Pose2d(43.3,-35.5,toRadians(-180)))
//                .lineToLinearHeading(new Pose2d(50,-60,toRadians(-180)))
//                .lineToLinearHeading(new Pose2d(55,-60,toRadians(-180)))
//                .build();
//
//        parky[2] = robot.roadrun
//                .trajectorySequenceBuilder(droppy[2].end())
//                .lineToLinearHeading(new Pose2d(43.3,-41.5,toRadians(-180)))
//                .lineToLinearHeading(new Pose2d(50,-60,toRadians(-180)))
//                .lineToLinearHeading(new Pose2d(55,-60,toRadians(-180)))
//                .build();/

        robot.dropServo(1);
        robot.dropServo(0);
        robot.setRight(false);
        robot.setBlue(false);
        robot.observeSpike();
        robot.hoverArm();
    }
    public void waitForStart(){
        while (!op.isStarted() || op.isStopRequested()) {
            bark = robot.getSpikePos();
            op.telemetry.addData("pixel", bark);
            packet.put("spike", bark);
            op.telemetry.addData("delaySec", delaySec);
            op.telemetry.addData("isRight", isRight);
            if (gampad.readGamepad(op.gamepad1.dpad_up, "gamepad1_dpad_up", "addSecs")) {
                delaySec++;
            }
            if (gampad.readGamepad(op.gamepad1.dpad_down, "gamepad1_dpad_down", "minusSecs")) {
                delaySec = min(0, delaySec - 1);
            }
            if (gampad.readGamepad(op.gamepad1.dpad_right, "gamepad1_dpad_right", "parkRight")) {
                isRight = true;
            }
            if (gampad.readGamepad(op.gamepad1.dpad_left, "gamepad1_dpad_left", "parkLeft")) {
                isRight = false;
            }
            robot.update();
        }
        op.resetRuntime();
        bark=2;
        time=0;
    }
    public void purp()
    {
        bark=2;
        robot.queuer.queue(false, true);
        robot.followTrajSeq(spikey[bark]);
        robot.queuer.addDelay(0.0);
    }

    public void intake(int height){
        robot.followTrajSeq(intake[bark]);
        robot.resetAuto();
        if (bark == 0) {
            robot.queuer.addDelay(1.5);
        }
        robot.intakeAuto(height);
    }
    public void cycleIntake(int height){

        robot.followTrajSeq(backToStack[0]);
        robot.intakeAuto(height);
        robot.queuer.addDelay(0.6);
        robot.resetAuto();
    }
    public void cycleIntake2(int height){

        robot.followTrajSeq(backToStack[1]);
        robot.intakeAuto(height);
        robot.queuer.addDelay(0.6);
        robot.resetAuto();
    }
    public void cycleDrop(int i){
        robot.queuer.waitForFinish();
        robot.followTrajSeq(drop[i]);
        robot.queuer.addDelay(0.2);
        robot.grabAuto();
        robot.lowAuto(false);
        robot.drop(46);
    }
    public void pre(){
        robot.queuer.waitForFinish();
        robot.followTrajSeq(droppy[bark]);
        robot.queuer.addDelay(0.2);
        robot.grabAuto();
    if (bark == 2) {
        robot.lowAuto(true);
        robot.yellowAuto(true);
        robot.drop(46.5);

    }else{
        robot.lowAuto(false);
        robot.yellowAuto(false);
        robot.drop(45.5);
    }
    }

    public void park(){
        robot.followTrajSeq(park[0]);
        robot.queuer.addDelay(.3);
        robot.resetAuto();
        robot.queuer.waitForFinish();
        robot.queuer.queue(false, true);
    }

    public void update(){
        robot.update();
        robot.queuer.setFirstLoop(false);
    }

    public boolean isAutDone(){
        return !robot.queuer.isFullfilled()&&time<29.8;
    }


}