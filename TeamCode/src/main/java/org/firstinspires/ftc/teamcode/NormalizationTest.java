
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "W Allen Color Norm test")
public class NormalizationTest extends OpMode {
    enum BallColor {
        pollen, blue, red, none;
    }

    private RevColorSensorV3 sensor;
    private RevColorSensorV3 othasensor;

    DcMotorEx outtake;
    ElapsedTime time;

    @Override
    public void init() {

        sensor = hardwareMap.get(RevColorSensorV3.class, "Color");
        othasensor = hardwareMap.get(RevColorSensorV3.class, "OthaCola");
        outtake = hardwareMap.get(DcMotorEx.class, "outtake");
        outtake.setDirection(DcMotorSimple.Direction.FORWARD);
        time = new ElapsedTime();
    }

    @Override
    public void loop() {

        NormalizedRGBA rgba = sensor.getNormalizedColors();
        double normal = rgba.alpha;
        double normalNormal = rgba.blue;
        double red  = (float) ( ( rgba.red / normal ) / normalNormal);
        double green = (float)( ( rgba.green / normal ) / normalNormal);

        double[] pollenA = new double[] {1.1, 2.1};
        double[] redA = new double[] {1.8, 1.35};
        double[] blueA = new double[] {0.2, 0.5};

        double pollenDist = Math.sqrt( Math.pow(pollenA[0] - red, 2) + Math.pow(pollenA[1] - green, 2));
        double redDist = Math.sqrt( Math.pow(redA[0] - red, 2) + Math.pow(redA[1] - green, 2));
        double blueDist = Math.sqrt( Math.pow(blueA[0] - red, 2) + Math.pow(blueA[1] - green, 2));

        BallColor color;
        double dist = sensor.getDistance(DistanceUnit.INCH);
        if (dist < 1.9) {
            if (pollenDist < redDist && pollenDist < blueDist) {
                color = BallColor.pollen;
            } else if (redDist < pollenDist && red < blueDist) {
                color = BallColor.red;
            } else {
                color = BallColor.blue;
            }
        } else {
            color = BallColor.none;
        }

        if (color == BallColor.none && time.milliseconds() > 150) {
            outtake.setPower(-0.25);
        } else if (color == BallColor.pollen) {
            time.reset();
            outtake.setPower(-0.5);
        }  else if (color == BallColor.blue || color == BallColor.red) {
            time.reset();
            outtake.setPower(0.5);
        }




        NormalizedRGBA rgba1 = othasensor.getNormalizedColors();
        double normal1 = rgba1.alpha;
        double normalNormal1 = rgba1.blue;
        double red1  = (float) ( ( rgba1.red / normal1 ) / normalNormal1);
        double green1 = (float)( ( rgba1.green / normal1 ) / normalNormal1);

        double[] pollenA1 = new double[] {1.1, 2.1};
        double[] redA1 = new double[] {1.8, 1.35};
        double[] blueA1 = new double[] {0.2, 0.5};

        double pollenDist1 = Math.sqrt( Math.pow(pollenA1[0] - red, 2) + Math.pow(pollenA1[1] - green1, 2));
        double redDist1 = Math.sqrt( Math.pow(redA1[0] - red, 2) + Math.pow(redA1[1] - green1, 2));
        double blueDist1 = Math.sqrt( Math.pow(blueA1[0] - red, 2) + Math.pow(blueA1[1] - green1, 2));

        BallColor color1;
        double dist1 = othasensor.getDistance(DistanceUnit.INCH);
        if (dist1 < 1.9) {
            if (pollenDist1 < redDist1 && pollenDist1 < blueDist1) {
                color1 = BallColor.pollen;
            } else if (redDist1 < pollenDist1 && red1 < blueDist1) {
                color1 = BallColor.red;
            } else {
                color1 = BallColor.blue;
            }
        } else {
            color1 = BallColor.none;
        }

        if (color1 == BallColor.none && time.milliseconds() > 150) {
            outtake.setPower(-0.25);
        } else if (color1 == BallColor.pollen) {
            time.reset();
            outtake.setPower(-0.5);
        }  else if (color == BallColor.blue || color1 == BallColor.red) {
            time.reset();
            outtake.setPower(0.5);
        }


        if (color1 != color) {
            outtake.setPower(-1);
            time.reset();
        }



        if (gamepad1.a) {
            color = BallColor.pollen;
        } else if (gamepad1.b) {
            color = BallColor.red;
        } else if (gamepad1.x) {
            color = BallColor.blue;
        }

        telemetry.addData("Color", color);
        telemetry.addData("OthaCola", color1);
    }
}
