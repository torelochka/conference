package ru.zheleznov.api.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TalkDto {
    private Long id;
    private String title;
    private Set<UserDto> speakers;
}
