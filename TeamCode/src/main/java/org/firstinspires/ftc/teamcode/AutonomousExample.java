package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.Locale;

@Autonomous(name="Autonomous Example", group="Linear OpMode")
public class AutonomousExample extends LinearOpMode {
    GoBildaPinpointDriver odo;

    @Override
    public void runOpMode() {
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(-84.0, -168.0, DistanceUnit.MM);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odo.resetPosAndIMU();

        waitForStart();
        moveForward(5);
    }

    public void moveForward(int distance){
        double startDistance = odo.getXOffset(DistanceUnit.INCH);
        double movedDistance = 0;

        //Set motors to start moving foward
        while(opModeIsActive() && movedDistance < distance){
            odo.update();
            movedDistance = odo.getPosition().getX(DistanceUnit.INCH)- startDistance;
            telemetry.addData("MovedDistance", movedDistance);
            updateTelemetry();
        }

        //Set motors to stop moving
    }

    public void updateTelemetry(){
        Pose2D pos = odo.getPosition();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Position", data);
        telemetry.addData("Status", "Initialized");
        telemetry.addData("X offset", odo.getXOffset(DistanceUnit.INCH));
        telemetry.addData("Y offset", odo.getYOffset(DistanceUnit.INCH));
        telemetry.addData("Device Version Number:", odo.getDeviceVersion());
        telemetry.addData("Heading Scalar", odo.getYawScalar());
        telemetry.update();
    }
}
