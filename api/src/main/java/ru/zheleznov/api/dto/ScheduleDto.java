package ru.zheleznov.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private Long id;
    private RoomDto room;
    private TalkDto talk;
    private Date dateStart;
    private Date dateEnd;
}
