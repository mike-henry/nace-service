package com.demo.nace.service;

import lombok.Getter;

@Getter
public class OrderNumberNotFoundException extends  RuntimeException {

  private static final String MESSAGE = "Order %d not found";
  final Long order;

  public OrderNumberNotFoundException(Long order) {
    super(String.format(MESSAGE,order));
    this.order = order;
  }
}
