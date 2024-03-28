package com.mozzie.nbp.domain.DTOs;

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
