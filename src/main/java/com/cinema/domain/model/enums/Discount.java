package com.cinema.domain.model.enums;

import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@RequiredArgsConstructor
public enum Discount {

    DZIECKO(new BigDecimal(50)),
    STUDENCKI(new BigDecimal(35)),
    SENIOR(new BigDecimal(50)),
    NORMALNY(new BigDecimal(0)),
    GRUPOWY(new BigDecimal(15)),
    PORANNY_SEANS(new BigDecimal(10)),
    NOCNE_KINO(new BigDecimal(40));

    public final BigDecimal discount;


}
