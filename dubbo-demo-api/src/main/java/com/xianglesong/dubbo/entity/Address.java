package com.xianglesong.dubbo.entity;

import java.io.Serializable;

public class Address implements Serializable {
  private float x;
  private float y;
  private String location;

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public float getX() {
    return this.x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return this.y;
  }

  public void setY(float y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "Address{" + "location='" + location + '\'' + ", x=" + x + ", y=" + y + '}';
  }
}
