package ru.zheleznov.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalkDto {
    private Long id;
    private String title;
    private Set<UserDto> speakers;
    private ScheduleDto schedule;
}
