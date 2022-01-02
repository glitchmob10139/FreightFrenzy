package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Pushbot: Auto Drive By Encoder", group="Pushbot")
//@Disabled
public class PushbotAutoDriveByEncoder_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    HardwarePushbot         goat   = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        goat.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        goat.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        goat.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        goat.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        goat.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        goat.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        goat.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        goat.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        goat.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                          goat.leftFront.getCurrentPosition(),
                          goat.rightFront.getCurrentPosition(),
                          goat.leftRear.getCurrentPosition(),
                          goat.rightRear.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  5,  5, 2.0);  // S1: Forward 5 Inches with 2 Sec timeout
        encoderDrive(TURN_SPEED,   12, -12, 2.0);  // S2: Turn Right 12 Inches with 2 Sec timeout
        encoderDrive(DRIVE_SPEED, -4, -4, 2.0);  // S3: Reverse 4 Inches with 2 Sec timeout

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = goat.leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = goat.rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newleftTarget = goat.leftRear.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = goat.rightRear.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            goat.leftFront.setTargetPosition(newLeftTarget);
            goat.rightFront.setTargetPosition(newRightTarget);
            goat.leftRear.setTargetPosition(newLeftTarget);
            goat.rightRear.setTargetPosition(newRightTarget);


            // Turn On RUN_TO_POSITION
            goat.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            goat.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            goat.leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            goat.rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            // reset the timeout time and start motion.
            runtime.reset();
            goat.leftFront.setPower(Math.abs(speed));
            goat.rightFront.setPower(Math.abs(speed));
            goat.leftRear.setPower(Math.abs(speed));
            goat.rightRear.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (goat.leftFront.isBusy() && goat.rightFront.isBusy() && goat.leftRear.isBusy() && goat.rightRear.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                                            goat.leftFront.getCurrentPosition(),
                                            goat.rightFront.getCurrentPosition(),
                                            goat.leftRear.getCurrentPosition(),
                                            goat.rightRear.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            goat.leftFront.setPower(0);
            goat.rightFront.setPower(0);
            goat.leftRear.setPower(0);
            goat.rightRear.setPower(0);

            // Turn off RUN_TO_POSITION
            goat.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            goat.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            goat.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            goat.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
