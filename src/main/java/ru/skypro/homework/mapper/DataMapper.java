package ru.skypro.homework.mapper;

import java.time.Instant;
import java.time.ZoneOffset;

public class DataMapper {
    public Integer instantToInteger(Instant instant) {
        return (int) instant.atZone(ZoneOffset.ofHours(3)).toInstant().toEpochMilli();
    }
}
