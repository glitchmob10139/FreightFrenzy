/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name=" Good Bot TeleOp", group="Pushbot")
//@Disabled
public class GoodBotTeleOp extends LinearOpMode {

    /* Declare OpMode members. */
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
        double primaryServoPower;
        double secondaryServoPower;

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
            
            //Use gamepad for the robot arm
            primaryServoPower = gamepad2.right_stick_y;
            secondaryServoPower = gamepad2.right_stick_x;
            goat.primaryServo.setPower(primaryServoPower);
            goat.secondaryServo.setPower(secondaryServoPower);



            // Send telemetry message to signify robot running;
            telemetry.addData("leftFront",  "%.2f", leftFrontPower);
            telemetry.addData("leftRear",  "%.2f", leftRearPower);
            telemetry.addData("rightFront", "%.2f", rightFrontPower);
            telemetry.addData("rightRear", "%.2f", rightRearPower);
            telemetry.addData("Duck Spinner", "%.2f", duckspinnerPower);
            telemetry.addData("primaryServo", "%.2f", primaryServoPower);
            telemetry.addData("secondaryServo", "%.2f", secondaryServoPower);
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
