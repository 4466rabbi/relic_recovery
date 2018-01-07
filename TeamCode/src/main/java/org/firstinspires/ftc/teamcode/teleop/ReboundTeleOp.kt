package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.ClawLift
import org.firstinspires.ftc.teamcode.hardware.Rebound
import java.util.*

@TeleOp(name = "Rebound TeleOp")
//@Disabled
class ReboundTeleOp : OpMode() {

    private lateinit var drivetrain : Rebound
    private lateinit var glyphHandler: ClawLift
    private var tankStyleControl : Boolean = false

    override fun init() {
        // setup
        val lf: DcMotor = hardwareMap.dcMotor.get("lf")
        val lb: DcMotor = hardwareMap.dcMotor.get("lb")
        val rf: DcMotor = hardwareMap.dcMotor.get("rf")
        val rb: DcMotor = hardwareMap.dcMotor.get("rb")
        val imu: BNO055IMU = hardwareMap.get(BNO055IMU::class.java, "imu")
        drivetrain = Rebound(this, lf, lb, rf, rb, imu)
        drivetrain.initialize(DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT)
        telemetry.addLine("rebound. v1.0")
        telemetry.addLine("Drivetrain initialized!")
        // check if encoders are working
        if (Collections.frequency(drivetrain.getMotorModes(), DcMotor.RunMode.RUN_USING_ENCODER) == 4) {
            telemetry.addLine("Encoders enabled!")
        }
        telemetry.addData("Initial Orientation: ", drivetrain.getOrientation())
        //mediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.up_and_away)
        // set up the claw
        val lift_left : DcMotor = hardwareMap.dcMotor.get("lift_left")
        val lift_right : DcMotor = hardwareMap.dcMotor.get("lift_right")
        val claw_left : Servo = hardwareMap.servo.get("claw_left")
        val claw_right : Servo = hardwareMap.servo.get("claw_right")
        glyphHandler = ClawLift(this, lift_left, lift_right, claw_left, claw_right)
        glyphHandler.initialize()
    }

    override fun loop() {
        val orientation = drivetrain.getOrientation()
        telemetry.addData("Orientation: ", orientation)
        telemetry.addData("Jordan Drive ", if(tankStyleControl) "Enabled." else "Disabled.")
        when {
            gamepad2.dpad_up -> glyphHandler.runLift(0.25)
            gamepad2.dpad_down -> glyphHandler.runLift(-0.25)
            gamepad2.a -> glyphHandler.openClaw()
            gamepad2.b -> glyphHandler.closeClaw()
            !gamepad2.dpad_up || gamepad2.dpad_down -> glyphHandler.runLift()
        }
        glyphHandler.runLift(0.33*gamepad2.left_stick_y)
        if (tankStyleControl) {drivetrain.tankMecanum(gamepad1)} else {drivetrain.arcadeMecanum(gamepad1)}
        telemetry.addData("Lift Powers: ", glyphHandler.getMotorPowers())
    }
}