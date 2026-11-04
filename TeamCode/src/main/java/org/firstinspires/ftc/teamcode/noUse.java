package org.firstinspires.ftc.teamcode;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;




import org.firstinspires.ftc.robotcore.external.JavaUtil;

@Disabled
@TeleOp(name = "nonScat")
public class noUse extends OpMode {

    DcMotor IDKman;
    NormalizedColorSensor coulor1;


    double hue1;
    double power;
    double x = 0.75;


    public void init() {

        IDKman = hardwareMap.get(DcMotor.class, "motor");
        coulor1 = hardwareMap.get(NormalizedColorSensor.class, "Color1");
        IDKman.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IDKman.setPower(0.0);

    }

    public void init_loop() {

        NormalizedRGBA rgba = coulor1.getNormalizedColors();
        hue1 = JavaUtil.colorToHue(rgba.toColor());

        telemetry.addData("Hue", hue1);

        IDKman.setPower(0);

    }

    public void loop() {

        NormalizedRGBA rgba = coulor1.getNormalizedColors();
        hue1 = JavaUtil.colorToHue(rgba.toColor());


        if (hue1 >= 85 && hue1 <= 180) {
            IDKman.setDirection(DcMotorSimple.Direction.REVERSE);
            IDKman.setPower(x);
            sleep(500);
            //yellow
        }

        else if (hue1 >= 10 && hue1 <= 150) {
            IDKman.setDirection(DcMotorSimple.Direction.FORWARD);
            IDKman.setPower(x);
            sleep(500);
            //RED
        }

        else if (hue1 >= 200 && hue1 <= 240) {
            IDKman.setDirection(DcMotorSimple.Direction.FORWARD);
            IDKman.setPower(x);
            sleep(500);
            //BLUE
        }

        else if (hue1 == 0) {
            power = 0;
        }

        else {
            power = 0;
        }


        IDKman.setPower(power);



        telemetry.addData("Motor Speed", IDKman.getPower());
        telemetry.addData("Motor Direction", IDKman.getDirection());
        telemetry.addData("Hue", hue1);


    }


//    public static BallColor toColor(double[] list, double distance) {
//        NormalizedRGBA rgba = new NormalizedRGBA();
//
//        rgba.red   = (float) (list[0] / list[2]);
//        rgba.green = (float) (list[1] / list[2]);
//        rgba.blue  = 1f;
//        rgba.alpha = (float) list[3];
//
//        return toColor(rgba, distance);
//    }


}



