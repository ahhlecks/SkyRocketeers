package com.mygdx.skygame.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.mygdx.skygame.mappings.Ps4;
import com.mygdx.skygame.objects.Player;
import com.mygdx.skygame.screens.GameScreen;

/**
 * Created by alexa on 2/20/2017.
 */
public class Ps4PlayerController {

    private Controller controller;
    private GameScreen screen;
    private boolean isDs4 = false;
    private float deadzone; // controller deadzone
    private float pressedElapsedOptions = 0f;
    private float pressedElapsedShare = 0f;
    private float pressedElapsedX = 0f;
    private float pressedElapsedSquare = 0f;
    private float pressedElapsedTriangle = 0f;
    private float pressedElapsedCircle = 0f;
    private float pressedElapsedL1 = 0f;
    private float pressedElapsedL2 = 0f;
    private float pressedElapsedL3 = 0f;
    private float pressedElapsedR1 = 0f;
    private float pressedElapsedR2 = 0f;
    private float pressedElapsedR3 = 0f;
    private float pressedElapsedUp = 0f;
    private float pressedElapsedDown = 0f;
    private float pressedElapsedLeft = 0f;
    private float pressedElapsedRight = 0f;
    private float onePress = 0.012f;
    private float accumulator = 0f;

    public boolean xIsDown = false;
    public boolean xIsPressed = false;
    public boolean squareIsDown = false;
    public boolean squareIsPressed = false;
    public boolean triangleIsDown = false;
    public boolean triangleIsPressed = false;
    public boolean circleIsDown = false;
    public boolean circleIsPressed = false;
    public boolean l1IsDown = false;
    public boolean l1IsPressed = false;
    public boolean r1IsDown = false;
    public boolean r1IsPressed = false;
    public boolean l2IsDown = false;
    public boolean l2IsPressed = false;
    public boolean r2IsDown = false;
    public boolean r2IsPressed = false;
    public boolean shareIsDown = false;
    public boolean shareIsPressed = false;
    public boolean optionsIsDown = false;
    public boolean optionsIsPressed = false;
    public boolean l3IsDown = false;
    public boolean l3IsPressed = false;
    public boolean r3IsDown = false;
    public boolean r3IsPressed = false;
    public boolean psIsDown = false;
    public boolean psIsPressed = false;
    public boolean touchpadIsDown = false;
    public boolean touchpadIsPressed = false;

    public Ps4PlayerController() {
        deadzone = .1f;
        initialize();
    }

    public Ps4PlayerController(float deadzone, GameScreen screen) {
        this.deadzone = deadzone;
        this.screen = screen;
        initialize();
    }

    private void initialize() {
        for (Controller c : Controllers.getControllers()) {
            if(Ps4.isPs4Controller(c)) {
                controller = c;
                Controllers.clearListeners();
                if (controller.getName().contains("Controller (XBOX 360 For Windows")) {
                    isDs4 = true;
                    Controllers.clearListeners();
                }
            }
        }
    }

    /**
     * This is the update method that the screen uses for controller input.
     * @param player Player reference
     * @return Nothing.
     */

    public void update(float delta, Player player) {
        // Buttons
        if(controller.getButton(Ps4.OPTIONS)) {
            optionsIsDown = true;
            if (pressedElapsedOptions < onePress) {
                optionsIsPressed = true;
                if(!screen.isPaused) {
                    screen.isPaused = true;
                }  else {
                    screen.isPaused = false;
                }
            } else {
                optionsIsPressed = false;
            }
            pressedElapsedOptions += delta;
        } else {
            optionsIsDown = false;
            pressedElapsedOptions = 0;
        }

        if(controller.getButton(Ps4.SHARE)) {
            shareIsDown = true;
            if(pressedElapsedShare < onePress) {
                shareIsPressed = true;
            } else {
                shareIsPressed = false;
            }
            pressedElapsedShare += delta;
        } else {
            shareIsDown = false;
            pressedElapsedShare = 0;
        }

        if(controller.getButton(Ps4.X)) {
            xIsDown = true;
            if(pressedElapsedX < onePress) {
                xIsPressed = true;
            } else {
                xIsPressed = false;
            }
            if(pressedElapsedX < onePress*5) {
                player.jump(controller.getButton(Ps4.X));
            }
            pressedElapsedX += delta;
        } else {
            xIsDown = false;
            pressedElapsedX = 0;
        }

        if(controller.getButton(Ps4.R3)) {
            r3IsDown = true;
            if(pressedElapsedR3 < onePress) {
                r3IsPressed = true;
            } else {
                r3IsPressed = false;
            }
            if(pressedElapsedR3 < onePress*5) {
                player.jump(controller.getButton(Ps4.R3));
            }
            pressedElapsedR3 += delta;
        } else {
            r3IsDown = false;
            pressedElapsedR3 = 0;
        }

        //player.jump(controller.getButton(Ps4.X) || controller.getButton(Ps4.R3));

        //right stick
        if (controller.getAxis(Ps4.R_STICK_VERTICAL_AXIS) > deadzone ||
                controller.getAxis(Ps4.R_STICK_VERTICAL_AXIS) < -deadzone) {
            player.rightVerticalAxis = -controller.getAxis(Ps4.R_STICK_VERTICAL_AXIS);
        }  else {
            player.rightVerticalAxis = 0;
        }
        if (controller.getAxis(Ps4.R_STICK_HORIZONTAL_AXIS) > deadzone ||
                controller.getAxis(Ps4.R_STICK_HORIZONTAL_AXIS) < -deadzone) {
            player.rightHorizontalAxis = controller.getAxis(Ps4.R_STICK_HORIZONTAL_AXIS);
        }  else {
            player.rightHorizontalAxis = 0;
        }

        //left stick
        if (controller.getAxis(Ps4.L_STICK_VERTICAL_AXIS) > deadzone ||
                controller.getAxis(Ps4.L_STICK_VERTICAL_AXIS) < -deadzone) {
            player.leftVerticalAxis = -controller.getAxis(Ps4.L_STICK_VERTICAL_AXIS);
        } else {
            player.leftVerticalAxis = 0;
        }
        if (controller.getAxis(Ps4.L_STICK_HORIZONTAL_AXIS) > deadzone ||
                controller.getAxis(Ps4.L_STICK_HORIZONTAL_AXIS) < -deadzone) {
            player.leftHorizontalAxis = controller.getAxis(Ps4.L_STICK_HORIZONTAL_AXIS);
        } else {
            player.leftHorizontalAxis = 0;
        }

        //R2 L2
        if(isDs4) {
            if (controller.getAxis(Ps4.R2_AXIS) < 0) {
                r2IsDown = true;
                if(pressedElapsedR2 < onePress) {
                    r2IsPressed = true;
                    player.r2JustPressed = true;
                } else {
                    r2IsPressed = false;
                    player.r2JustPressed = false;
                }
                pressedElapsedR2 += delta;
                player.r2Axis = -controller.getAxis(Ps4.R2_AXIS);
                player.shoot(delta);
            } else {

                pressedElapsedR2 = 0;
                player.r2Axis = 0;
            }
        } else {
            if (controller.getAxis(Ps4.R2_AXIS) > 0) {
                r2IsDown = true;
                if(pressedElapsedR2 < onePress) {
                    r2IsPressed = true;
                    player.r2JustPressed = true;
                } else {
                    r2IsPressed = false;
                    player.r2JustPressed = false;
                }
                pressedElapsedR2 += delta;
                player.r2Axis = controller.getAxis(Ps4.R2_AXIS);
                player.shoot(delta);
            } else {
                r2IsDown = false;
                pressedElapsedR2 = 0;
                player.r2Axis = 0;
            }
        }

        if (controller.getAxis(Ps4.L2_AXIS) > 0) {
            l2IsDown = true;
            if(pressedElapsedL2 < onePress) {
                l2IsPressed = true;
                player.l2JustPressed = true;
            } else {
                l2IsPressed = false;
                player.l2JustPressed = false;
            }
            player.l2Axis = controller.getAxis(Ps4.L2_AXIS);
            pressedElapsedL2 += delta;
        } else {
            l2IsDown = false;
            pressedElapsedL2 = 0;
            player.l2Axis = 0;
        }

        //D-PAD
        switch(controller.getPov(0)) {
            case north:
                System.out.println("North");
                break;
            case northEast:
                System.out.println("northEast");
                break;
            case east:
                System.out.println("east");
                break;
            case southEast:
                System.out.println("southEast");
                break;
            case south:
                System.out.println("south");
                break;
            case southWest:
                System.out.println("southWest");
                break;
            case west:
                System.out.println("west");
                break;
            case northWest:
                System.out.println("northWest");
                break;
        }
    }
}
