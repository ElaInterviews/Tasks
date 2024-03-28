package com.mozzie.nbp;

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
