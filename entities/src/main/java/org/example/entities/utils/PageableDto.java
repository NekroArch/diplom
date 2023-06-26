package org.example.entities.utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageableDto {

    Integer pageNumber;

    Integer pageSize;
}
