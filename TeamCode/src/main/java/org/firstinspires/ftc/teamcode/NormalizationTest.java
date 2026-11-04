
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

    DcMotorEx outtake;
    ElapsedTime time;

    @Override
    public void init() {

        sensor = hardwareMap.get(RevColorSensorV3.class, "Color");
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


        telemetry.addData("color", color);
    }
}
