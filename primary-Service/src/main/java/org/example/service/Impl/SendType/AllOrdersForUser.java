package org.example.service.Impl.SendType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@Data
@SuperBuilder
public class AllOrdersForUser {
    private Pageable pageable;
    private Integer id;
}
