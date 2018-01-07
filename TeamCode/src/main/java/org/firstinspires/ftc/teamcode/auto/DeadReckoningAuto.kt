package org.firstinspires.ftc.teamcode.auto

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.hardware.Rebound

@Autonomous(name = "Park Red Near", group = "Dead Reckoning")
//@Disabled
class DeadReckoningAuto : LinearOpMode() {

    private lateinit var drivetrain : Rebound

    override fun runOpMode() {
        val lf : DcMotor = hardwareMap.dcMotor.get("lf")
        val lb : DcMotor = hardwareMap.dcMotor.get("lb")
        val rf : DcMotor = hardwareMap.dcMotor.get("rf")
        val rb : DcMotor = hardwareMap.dcMotor.get("rb")
        val imu : BNO055IMU = hardwareMap.get(BNO055IMU::class.java, "imu")
        drivetrain = Rebound(this, lf, lb, rf, rb, imu)
        drivetrain.initialize(DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE)
        //val mediaPlayer: MediaPlayer = MediaPlayer.create(hardwareMap.appContext, R.raw.up_and_away)
        //mediaPlayer.start()
        waitForStart()
        drivetrain.arcadeMecanum(0.0, -0.35, 0.0)
        sleep(1200)
        drivetrain.stopMoving()
        sleep(1000)
        drivetrain.arcadeMecanum(-0.7, 0.0, 0.85)
        sleep(1000)
        drivetrain.stopMoving()
        sleep(500)
        drivetrain.arcadeMecanum(0.35, -0.35, 0.0)
        sleep(1000)
        drivetrain.stopMoving()
        //mediaPlayer.stop()

    }


}