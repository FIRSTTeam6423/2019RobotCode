package robot;

import robot.subsystems.*;

public class Operator {
    public static final double ARM_TOP_ROCKET_BALL = 226;
    public static final double ARM_MID_ROCKET_BALL = -343;
    public static final double ARM_BOT_ROCKET_BALL = -683;
    //--------------------------------------------------------------
    public static final double WRIST_TOP_ROCKET_BALL = -763;
    public static final double WRIST_MID_ROCKET_BALL = -31;
    public static final double WRIST_BOT_ROCKET_BALL = 155;
    //--------------------------------------------------------------
    public static final double ARM_MID_ROCKET_HATCH = -374;
    public static final double ARM_BOT_ROCKET_HATCH = -793;
    //--------------------------------------------------------------
    public static final double WRIST_MID_ROCKET_HATCH = 335;
    public static final double WRIST_BOT_ROCKET_HATCH = 720;
    //--------------------------------------------------------------
    public static final double ARM_CARGO_BALL = 0;
    public static final double ARM_CARGO_HATCH = 0;
    //--------------------------------------------------------------
    public static final double WRIST_CARGO_BALL = 0;
    public static final double WRIST_CARGO_HATCH = 0;
    //--------------------------------------------------------------
    public static final double ARM_GND_HATCH = -450;
    public static final double WRIST_GND_HATCH = -860;
    //--------------------------------------------------------------
    public static final double ARM_STARTUP = -763;
    public static final double WRIST_STARTUP = 785;
    //--------------------------------------------------------------
    public static final double WHEELS_SPEED_IN = 1;
    public static final double WHEELS_SPEED_OUT = -1;

    Controller op;
    Arm arm;
    HatchMechanism hatch;
    Wheels wheels;
    Wrist wrist;

    public Operator(int port) {
        op = new Controller(port);
        arm = new Arm();
        hatch = new HatchMechanism();
        wheels = new Wheels();
        wrist = new Wrist();
    }

    public void runOpControls() {
        wheelControl();
        armWristOverride();
        armWristControl();
        hatchControl();
    }

    public void resetPID() {
        arm.reset();
        wrist.reset();
    }

    public double getWristAngle() {
        return wrist.getPot().get();
    }

    public double getArmAngle() {
        return arm.getPot().get();
    } 

    private void wheelControl() {
        if (op.getRightBumper()) {
            wheels.runWheels(WHEELS_SPEED_IN);
        }
        else if (op.getLeftBumper()) {
            wheels.runWheels(WHEELS_SPEED_OUT);
        }
    }

    private void armWristOverride() {
        if(op.getRightStickButton()) {
            wrist.override(op.getRightYAxis());
        }
        if(op.getLeftStickButton()) {
            arm.override(op.getLeftYAxis());
        }
    }

    private void armWristControl() {
        if (op.getOButton()) {
            arm.setPosition(ARM_MID_ROCKET_HATCH);
            wrist.setPosition(WRIST_MID_ROCKET_HATCH);
        }
        else if (op.getXButton()) {
            arm.setPosition(ARM_BOT_ROCKET_HATCH);
            wrist.setPosition(WRIST_BOT_ROCKET_HATCH);
        }
        else if (op.getSquareButton()) {
            arm.setPosition(ARM_CARGO_HATCH);
            wrist.setPosition(WRIST_CARGO_HATCH);
        }
        else if (op.getDPadUp()) {
            arm.setPosition(ARM_TOP_ROCKET_BALL);
            wrist.setPosition(WRIST_TOP_ROCKET_BALL);
        }
        else if (op.getDPadLeft()) {
            arm.setPosition(ARM_MID_ROCKET_BALL);
            wrist.setPosition(WRIST_MID_ROCKET_BALL);
        }
        else if (op.getDPadDown()) {
            arm.setPosition(ARM_BOT_ROCKET_BALL);
            wrist.setPosition(WRIST_BOT_ROCKET_BALL);
        }
        else if (op.getDPadRight()) {
            arm.setPosition(ARM_CARGO_BALL);
            wrist.setPosition(WRIST_CARGO_BALL);
        }
    }

    private void hatchControl() {
        if (op.getTriangleButton()) {
            hatch.place();
        }
        else {
            hatch.retract();
        }
    }
}