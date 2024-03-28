package com.mozzie.nbp.domain.nbpdtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NbpParentDto {
    List<NbpChildDto> rates;
}