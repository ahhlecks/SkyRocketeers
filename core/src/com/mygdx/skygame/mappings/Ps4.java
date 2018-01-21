package com.mygdx.skygame.mappings;


import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;

/** Mappings for the PS4 series of controllers. Works only on desktop so far.
 *
 *
 * All codes are for buttons expect the L_STICK_XXX, R_STICK_XXX, L2 and R2 codes, which are axes.
 *
 * @author badlogic + alex.mckee */
public class Ps4 {
    // Buttons

    public static int SQUARE = 0;
    public static int X = 1;
    public static int CIRCLE = 2;
    public static int TRIANGLE = 3;
    public static int L1 = 4;
    public static int R1 = 5;
    public static int L2_BUTTON = 6;
    public static int R2_BUTTON = 7;
    public static int SHARE = 8;
    public static int OPTIONS = 9;
    public static int L3 = 10;
    public static int R3 = 11;
    public static int PLAYSTATION = 12;
    public static int TOUCHPAD = 13;

    public static final PovDirection DPAD_UP = PovDirection.north;
    public static final PovDirection DPAD_UP_RIGHT = PovDirection.northEast;
    public static final PovDirection DPAD_RIGHT = PovDirection.east;
    public static final PovDirection DPAD_DOWN_RIGHT = PovDirection.southWest;
    public static final PovDirection DPAD_DOWN = PovDirection.south;
    public static final PovDirection DPAD_DOWN_LEFT = PovDirection.southWest;
    public static final PovDirection DPAD_LEFT = PovDirection.west;
    public static final PovDirection DPAD_UP_LEFT = PovDirection.northWest;



    // Axes
    /** left trigger, -1 if not pressed, 1 if pressed **/
    public static int L2_AXIS = 5;
    /** right trigger, -1 if not pressed, 1 if pressed **/
    public static int R2_AXIS = 4;
    /** left stick vertical axis, -1 if up, 1 if down **/
    public static int L_STICK_VERTICAL_AXIS = 2;
    /** left stick horizontal axis, -1 if left, 1 if right **/
    public static int L_STICK_HORIZONTAL_AXIS = 3;
    /** right stick vertical axis, -1 if up, 1 if down **/
    public static int R_STICK_VERTICAL_AXIS = 0;
    /** right stick horizontal axis, -1 if left, 1 if right **/
    public static int R_STICK_HORIZONTAL_AXIS = 1;

    static {

    }


    /** @return whether the {@link Controller} is an Xbox controller
     */
    public static boolean isPs4Controller(Controller controller) {
        if (controller.getName().contains("Controller (XBOX 360 For Windows")) {
            SQUARE = 2;
            X = 0;
            CIRCLE = 1;
            TRIANGLE = 3;
            L1 = 4;
            R1 = 5;
            L2_BUTTON = -1;
            R2_BUTTON = -1;
            SHARE = 6;
            OPTIONS = 7;
            L3 = 8;
            R3 = 9;
            PLAYSTATION = 12;
            TOUCHPAD = 13;
            L2_AXIS = 4;
            R2_AXIS = 4;
            L_STICK_VERTICAL_AXIS = 0;
            L_STICK_HORIZONTAL_AXIS = 1;
            R_STICK_VERTICAL_AXIS = 2;
            R_STICK_HORIZONTAL_AXIS = 3;
            return true;
        } else if(controller.getName().contains("Wireless Controller")){
            SQUARE = 0;
            X = 1;
            CIRCLE = 2;
            TRIANGLE = 3;
            L1 = 4;
            R1 = 5;
            L2_BUTTON = 6;
            R2_BUTTON = 7;
            SHARE = 8;
            OPTIONS = 9;
            L3 = 10;
            R3 = 11;
            PLAYSTATION = 12;
            TOUCHPAD = 13;
            L2_AXIS = 5;
            R2_AXIS = 4;
            L_STICK_VERTICAL_AXIS = 2;
            L_STICK_HORIZONTAL_AXIS = 3;
            R_STICK_VERTICAL_AXIS = 0;
            R_STICK_HORIZONTAL_AXIS = 1;
            return true;
        }
        return false;
    }
}
