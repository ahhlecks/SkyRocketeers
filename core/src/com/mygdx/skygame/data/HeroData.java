package com.mygdx.skygame.data;

import java.io.Serializable;

public class HeroData implements Serializable{
    public String name = "Default"; // Hero Name
    public int level = 1; // Hero Level
    public float speed = 20f; // Normal walking speed; 20 Newtons
    public float maxWalkSpeed = 8f; // Max walking velocity; 8m/s
    public float jumpSpeed = 120f; //120 Newtons
    public float maxTerrainSpeedX = 20f; // Added speed from skiing
    public Jetpack jetpack;
}
