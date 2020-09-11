package com.DanyFids.Math;

public class Vec2 {
	public float x;
	public float y;
	
	public Vec2() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	public Vec2 add(Vec2 o) {
		return new Vec2(this.x + o.x, this.y + o.y);
	}
	
	public Vec2 subtract(Vec2 o) {
		return new Vec2(this.x - o.x, this.y - o.y);
	}
	
	public Vec2 divide(float s) {
		return new Vec2(x/s, y/s);
	}
	
	public Vec2 multiply(float s) {
		return new Vec2(x*s, y*s);
	}
	
	public float length() {
		return (float) Math.sqrt((x * x) + (y * y));
	}
	
	public Vec2 normalize() {
		float len = length();
		return divide(len);
	}
}
