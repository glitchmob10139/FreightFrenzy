package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;


@TeleOp(name=" Good Bot TeleOp", group="Pushbot")
public class GoodBotTeleOp extends LinearOpMode {
    
    GoodBotHardwareMap goat           = new GoodBotHardwareMap();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() {
        double leftFrontPower;
        double leftRearPower;
        double rightFrontPower;
        double rightRearPower;
        double drive;
        double turn;
        double strafe;
        double max;
        double duckspinnerPower;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        goat.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;
            strafe = gamepad1.left_stick_x;

            // Combine drive and turn for blended motion.
             leftFrontPower = drive + turn + strafe;
             leftRearPower  = drive + turn - strafe;
             rightFrontPower  = drive - turn - strafe;
             rightRearPower = drive - turn + strafe;


//            // Normalize the values so neither exceed +/- 1.0
//            max = Math.max(Math.abs(left), Math.abs(right));
//            if (max > 1.0)
//            {
//                left /= max;
//                right /= max;
//            }

            // Output the safe vales to the motor drives.
            goat.leftFront.setPower(leftFrontPower);
            goat.leftRear.setPower(leftRearPower);
            goat.rightFront.setPower(rightFrontPower);
            goat.rightRear.setPower(rightRearPower);


            // Use gamepad for Duck Spinner
            duckspinnerPower = -gamepad2.left_stick_y;
            goat.duckSpinner.setPower(duckspinnerPower);

            // Use gamepad for the claw
            if (gamepad2.left_bumper){
                goat.clawGrip.setPosition(0);
                telemetry.addData("Claw Posistion:","Open");
            }
            if (gamepad2.right_bumper){
                goat.clawGrip.setPosition(1);
                telemetry.addData("Claw Postion:","Closed");
            }

            // Send telemetry message to signify robot running;

            telemetry.addData("leftFront",  "%.2f", leftFrontPower);
            telemetry.addData("leftRear",  "%.2f", leftRearPower);
            telemetry.addData("rightFront", "%.2f", rightFrontPower);
            telemetry.addData("rightRear", "%.2f", rightRearPower);
            telemetry.addData("Duck Spinner", "%.2f", duckspinnerPower);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
