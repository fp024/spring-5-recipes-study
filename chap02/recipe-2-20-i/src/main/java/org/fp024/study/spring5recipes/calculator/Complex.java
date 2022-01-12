package org.fp024.study.spring5recipes.calculator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Getter
@Setter
public class Complex {
  private int real;
  private int imaginary;

  public Complex(int real, int imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  public String toString() {
    return "(" + real + " + " + imaginary + "i)";
  }
}
