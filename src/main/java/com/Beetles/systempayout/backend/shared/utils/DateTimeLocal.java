package com.Beetles.systempayout.backend.shared.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeLocal {
    public static LocalDate pegarHorarioAtual(){
        return LocalDateTime.now().toLocalDate();
    }
}
