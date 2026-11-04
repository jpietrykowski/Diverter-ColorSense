package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.JavaUtil;


@TeleOp(name = "holy w accurate color sensor reading opmode", group = "OpMode")
public class colorSensorReader extends OpMode {

    DcMotorEx IDKman;
    NormalizedColorSensor color1;
    NormalizedColorSensor color2;

    float hue1;
    double hue2;

    double power;


    public void init() {

        IDKman = hardwareMap.get(DcMotorEx.class, "motor");
        color1 = hardwareMap.get(NormalizedColorSensor.class, "Color1");
        color2 = hardwareMap.get(NormalizedColorSensor.class, "Color2");
        IDKman.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void loop() {

        NormalizedRGBA colorsOf1 = color1.getNormalizedColors();
        hue1 = JavaUtil.colorToHue(colorsOf1.toColor());

        NormalizedRGBA colorsOf2 = color2.getNormalizedColors();
        hue2 = JavaUtil.colorToHue(colorsOf2.toColor());


        if(gamepad1.a) {
            power = 1;
        }
        else if(gamepad1.b) {
            power = 0.75;
        }
        else if (gamepad1.y) {
            power = 0.5;
        }
        else if(gamepad1.x) {
            power = 0.25;
        }
        else {
            power = 0;
        }


        if (hue1 < 1 || hue1 > 330) {
            //IDKman.setDirection(DcMotorSimple.Direction.FORWARD);
            power = -0.5;
            // RED
        }
        else if (hue1 >= 30 && hue1 < 199) {
            IDKman.setDirection(DcMotorSimple.Direction.FORWARD);
            power = 0.5;
            // YELLOW
        }
        else if (hue1 >= 200 && hue1 < 270) {
            //IDKman.setDirection(DcMotorSimple.Direction.FORWARD);
            power = -0.5;
            // BLUE

        }




//        if (hue2 < 30 || hue2 > 330) {
//            IDKman.setDirection(DcMotorSimple.Direction.REVERSE);
//            power = 1;
//            // RED
//        }
//        else if (hue2 >= 30 && hue2 < 90) {
//            // YELLOW
//        }
//        else if (hue2 >= 210 && hue2 < 270) {
//            IDKman.setDirection(DcMotorSimple.Direction.REVERSE);
//            power = 1;
//            // BLUE
//        }
//        else {
//            IDKman.setPower(0);
//        }

        if (gamepad1.left_bumper || gamepad1.right_bumper || gamepad1.left_trigger_pressed || gamepad1.right_trigger_pressed) {
            power = 0;
        }

        if (gamepad1.dpad_down) {
            IDKman.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        else if (gamepad1.dpad_up) {
            IDKman.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        IDKman.setPower(power);




        telemetry.addData("Motor Power", IDKman.getPower());
        telemetry.addData("Color #1", hue1);
        telemetry.addData("Color #2", hue2);
        telemetry.addData("Motor Direction", IDKman.getDirection());


    }
}
