package org.fp024.study.spring5recipes.calculator;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configurable
@Component
@Scope("prototype")
@Getter
public class Complex {
  private int real;
  private int imaginary;
  private ComplexFormatter formatter;

  public Complex(int real, int imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  @Autowired
  public void setFormatter(ComplexFormatter formatter) {
    this.formatter = formatter;
  }

  @Override
  public String toString() {
    return formatter.format(this);
  }
}
