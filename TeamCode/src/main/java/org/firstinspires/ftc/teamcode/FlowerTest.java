package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class FlowerTest extends OpMode {

    private DcMotor intake;
    private DcMotor aluminum;
    private DcMotor flower;

    FancyButton shooterIn;
    FancyButton shooterOut;
    FancyButton speedUp;
    FancyButton speedDown;




    double intakePower = 0.25;

    public void init(){

        intake = hardwareMap.get(DcMotor.class, "outtake");
        aluminum = hardwareMap.get(DcMotor.class, "intake");
        flower = hardwareMap.get(DcMotor.class, "flower");

        intake.setDirection(DcMotor.Direction.REVERSE);
        aluminum.setDirection(DcMotor.Direction.REVERSE);
        flower.setDirection(DcMotor.Direction.FORWARD);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        aluminum.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flower.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooterIn = new FancyButton(FancyButton.PressType.Toggle);
        shooterOut = new FancyButton(FancyButton.PressType.Toggle);
        speedUp = new FancyButton(FancyButton.PressType.LongPress);
        speedDown = new FancyButton(FancyButton.PressType.LongPress);
    }

    public void loop(){

        shooterOut.checkStatus(gamepad1.y);
        shooterIn.checkStatus(gamepad1.a);
        speedUp.checkStatus(gamepad1.dpad_up);
        speedDown.checkStatus(gamepad1.dpad_down);

        if(shooterOut.isOn) {
            intake.setDirection(DcMotorSimple.Direction.REVERSE);
            aluminum.setDirection(DcMotorSimple.Direction.REVERSE);
            flower.setDirection(DcMotorSimple.Direction.FORWARD);
            intake.setPower(intakePower);
            aluminum.setPower(intakePower);
            flower.setPower(intakePower);
        } else if (shooterIn.isOn) {
            intake.setDirection(DcMotorSimple.Direction.FORWARD);
            aluminum.setDirection(DcMotorSimple.Direction.FORWARD);
            flower.setDirection(DcMotorSimple.Direction.REVERSE);
            intake.setPower(intakePower);
            aluminum.setPower(intakePower);
            flower.setPower(intakePower);
        } else {
            intake.setPower(0);
            aluminum.setPower(0);
            flower.setPower(0);
        }

        if (speedUp.isOn) {
            intakePower += 0.001;
        } else if (speedDown.isOn) {
            intakePower -= 0.001;
        }

        telemetry.addData("intake speed", intake.getPower() * 100);
        telemetry.addData("diverter speed", aluminum.getPower() * 100);
        telemetry.addData("flower speed", flower.getPower() * 100);

    }

}
