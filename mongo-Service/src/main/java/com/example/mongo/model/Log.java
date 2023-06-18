package com.example.mongo.model;

import com.mongodb.lang.Nullable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@Getter
@Setter
@Document("logs")
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    String id;

    @Nullable
    Object returnValue;
    String args;
    String workingMethod;
    String methodName;
    LocalDateTime localDateTime;
    Long runningTime;
}
