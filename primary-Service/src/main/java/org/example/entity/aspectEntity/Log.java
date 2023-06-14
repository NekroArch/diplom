package org.example.entity.aspectEntity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    Object returnValue;
    String args;
    String workingMethod;
    String methodName;
    LocalDateTime localDateTime;
    Long runningTime;
}
