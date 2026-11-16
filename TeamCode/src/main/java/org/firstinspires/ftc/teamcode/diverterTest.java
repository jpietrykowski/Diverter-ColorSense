
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Diverter Test")
public class diverterTest extends OpMode {

    public enum BallColor {
        pollen, blue, red;
    }

    private RevColorSensorV3 leftSensor;
    private RevColorSensorV3 rightSensor;


    DcMotorEx diverter;
    DcMotorEx intake;
    ElapsedTime time;


    public BallColor col(NormalizedRGBA rgba){
        double normal = rgba.alpha;
        double nn = Math.sqrt(Math.pow(rgba.red, 2) + Math.pow(rgba.blue, 2) + Math.pow(rgba.green, 2));
//      double normalNormal = rgba.green;
        telemetry.addData("nn", nn);

        double red  = (float) ( ( rgba.red / nn ) );
        double green  = (float) ( ( rgba.green / nn ) );
        double blue = (float)( ( rgba.blue / nn ));
        telemetry.addData("nn red", red);
        telemetry.addData("nn green", green);
        telemetry.addData("nn blue", blue);

        BallColor color;
        if (green > red && green > blue) {
            color = BallColor.pollen;
        } else if (red > green && red > blue) {
            color = BallColor.red;
        } else {
            color = BallColor.blue;
        }
        return color;
    }

    @Override
    public void init() {

        leftSensor = hardwareMap.get(RevColorSensorV3.class, "Color");
        rightSensor = hardwareMap.get(RevColorSensorV3.class, "OthaCola");
        diverter = hardwareMap.get(DcMotorEx.class, "outtake");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        diverter.setDirection(DcMotorSimple.Direction.REVERSE);
        time = new ElapsedTime();
    }

    @Override
    public void loop() {

        intake.setPower(0.75);


        NormalizedRGBA rgba1 = leftSensor.getNormalizedColors();
        BallColor col1 = col(rgba1);
        NormalizedRGBA rgba2 = rightSensor.getNormalizedColors();
        BallColor col2 = col(rgba2);


        double dist1 = leftSensor.getDistance(DistanceUnit.INCH);
        double dist2 = rightSensor.getDistance(DistanceUnit.INCH);
        BallColor color = col1;
        if (dist2 < dist1) {
            color = col2;
        }
        boolean isNoBallInside = dist1 + dist2 > 4.0;
        telemetry.addData("is_there_no_ball_inside", isNoBallInside);

        if (time.milliseconds() > 300){
            if (isNoBallInside) {
                diverter.setPower(-0.5);
            } else if (color == BallColor.pollen) {
                time.reset();
                diverter.setPower(-0.75);
            }  else if (color == BallColor.blue || color == BallColor.red) {
                time.reset();
                diverter.setPower(0.75);
            }
        }



        if (gamepad1.a) {
            color = BallColor.pollen;
        } else if (gamepad1.b) {
            color = BallColor.red;
        } else if (gamepad1.x) {
            color = BallColor.blue;
        }


        telemetry.addData("Left Color", color);
        telemetry.addData("Right Color", col1);
        telemetry.addLine();
        telemetry.addData("Red", rgba1.red);
        telemetry.addLine();
        telemetry.addData("Blue", rgba1.blue);
        telemetry.addData("Closer: ", dist2 < dist1 ? "sensor 1" : "sensor classis");
        telemetry.addLine();
        telemetry.addData("Red1", rgba2.red);
        telemetry.addLine();
        telemetry.addData("Blue1", rgba2.blue);
        telemetry.addLine();
        telemetry.addData("Green", rgba1.green);
        telemetry.addData("Green1", rgba2.green);
        telemetry.addLine();
        telemetry.addData("alpha", rgba1.alpha);
        telemetry.addData("alpha1", rgba2.alpha);
        telemetry.addLine();
        telemetry.addData("distance", dist1);
        telemetry.addData("distance1", dist2);


    }


}
