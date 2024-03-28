package com.mozzie.nbp.domain.nbpdtos;

import java.util.List;
import lombok.Getter;

@Getter
public class NbpParentDto {
    List<NbpChildDto> rates;
}
