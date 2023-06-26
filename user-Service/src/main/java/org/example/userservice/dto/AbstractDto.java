package org.example.userservice.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
public abstract class AbstractDto {
//    @JsonIgnore
    private Integer id;

}
