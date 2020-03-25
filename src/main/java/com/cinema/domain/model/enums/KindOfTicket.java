package com.cinema.domain.model.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public enum  KindOfTicket {
    @Value("1")
    DZIECKO(50)
    ,
    @Value("1")
    STUDENCKI(35)
    ,SENIOR(50)
    ,NORMALNY(0)
    , GRUPOWY(15)
    , PORANNY_SEANS(10)
    ,NOCNE_KINO(40);

    public final Integer discount;
}
