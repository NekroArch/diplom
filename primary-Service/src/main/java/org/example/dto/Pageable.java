package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
public class Pageable {
    private int number;
    private int size = 10;

    public int getOffset() {
        return number * size;

    }

    public static Pageable maxPage() {
        return new Pageable(0, Integer.MAX_VALUE);
    }
}
