package com.testlab1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class Company{
private Company parent;
private long employeeCount;
}
