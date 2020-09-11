package com.DanyFids.graphics;

import com.DanyFids.Math.Vec2;

public class Sprite {
	private Vec2 pos;
	private Vec2 dimm;
	private Vec2 offset;
	
	public Sprite(Vec2 p, Vec2 d, Vec2 o) {
		pos = p;
		dimm = d;
		offset = o;
	}
}
