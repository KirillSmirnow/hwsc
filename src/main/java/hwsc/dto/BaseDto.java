package hwsc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public abstract class BaseDto {

    private long id;
    private LocalDateTime created;
    private LocalDateTime updated;
}
