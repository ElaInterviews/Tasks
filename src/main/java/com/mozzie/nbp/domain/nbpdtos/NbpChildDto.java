package com.mozzie.nbp.domain.nbpdtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NbpChildDto {
    String currency;
    String mid;
    String code;
}
