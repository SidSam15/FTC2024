package org.firstinspires.ftc.teamcode.commandBased;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commandBased.commands.MoveElevator;
import org.firstinspires.ftc.teamcode.commandBased.commands.drive.FieldCentric;
import org.firstinspires.ftc.teamcode.commandBased.subsystems.DrivetrainSubsystem;
import org.firstinspires.ftc.teamcode.commandBased.subsystems.ElevatorSubsystem;


import ftc.rogue.blacksmith.BlackOp;
import ftc.rogue.blacksmith.Scheduler;
import ftc.rogue.blacksmith.listeners.ReforgedGamepad;

@Config
@TeleOp(name="Command Based", group="Linear Opmode")
public class Robot extends BlackOp {

    public static DrivetrainSubsystem drivetrainSS;
    public static ElevatorSubsystem elevatorSubsystem;

    @Override
    public void go() {

        drivetrainSS = new DrivetrainSubsystem(hardwareMap);
        elevatorSubsystem = new ElevatorSubsystem(hardwareMap);

        ReforgedGamepad driver = new ReforgedGamepad(gamepad1);

        MoveElevator eleHigh = new MoveElevator(elevatorSubsystem, 1000);
        MoveElevator eleLow = new MoveElevator(elevatorSubsystem, 0);

        FieldCentric fieldCentric = new FieldCentric(
                drivetrainSS,
                driver.left_stick_x::get,
                () -> -driver.left_stick_y.get(),
                driver.right_stick_x::get
        );

        drivetrainSS.setSpeedMultipliers(1, 1, 1);

        drivetrainSS.setDefaultCommand(fieldCentric);

        waitForStart();

        Scheduler.launchOnStart(this, () -> {

            driver.dpad_up.onRise(eleHigh::schedule);
            driver.dpad_down.onRise(eleLow::schedule);

            driver.left_bumper.onRise(() -> drivetrainSS.setSpeedMultipliers(0.5, 0.5, 0.5))
                              .onFall(() -> drivetrainSS.setSpeedMultipliers(1, 1, 1));

            mTelemetry().addData("LX", driver.left_stick_x.get());
            mTelemetry().addData("LY", driver.left_stick_y.get());
            mTelemetry().addData("RX", driver.right_stick_x.get());
            mTelemetry().update();

            CommandScheduler.getInstance().run();
        });
    }
}