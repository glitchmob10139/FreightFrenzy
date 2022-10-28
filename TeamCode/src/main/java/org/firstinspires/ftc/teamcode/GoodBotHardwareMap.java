package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class GoodBotHardwareMap
{
    /* Public OpMode members. */
    public DcMotor  leftFront   = null;
    public DcMotor  leftRear  = null;
    public DcMotor  rightFront  = null;
    public DcMotor  rightRear    = null;
//    public DcMotor  duckSpinner   = null;
//    public Servo clawGrip  = null;
//    public Servo clawGrip2 = null;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public GoodBotHardwareMap(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFront  = hwMap.get(DcMotor.class, "left_front");
        leftRear = hwMap.get(DcMotor.class, "left_rear");
        rightFront    = hwMap.get(DcMotor.class, "right_front");
        rightRear   = hwMap.get(DcMotor.class, "right_rear");
//        duckSpinner = hwMap.get(DcMotor.class, "duck_spinner");


        //setting directions.
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
//        duckSpinner.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        duckSpinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Define and initialize ALL installed servos.
//        clawGrip = hwMap.get(Servo.class, "claw_grip");
//        clawGrip2 = hwMap.get(Servo.class,"claw_grip2");

        // Set all servo to the 0 position
//        clawGrip.setPosition(0);
    }
 }
