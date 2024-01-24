package org.fp024.study.spring5recipes.vehicle.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Alias("vehicle")
public class Vehicle {
  private String vehicleNo;
  private String color;
  private int wheel;
  private int seat;

  public Vehicle(String vehicleNo, String color, int wheel, int seat) {
    this.vehicleNo = vehicleNo;
    this.color = color;
    this.wheel = wheel;
    this.seat = seat;
  }
}
