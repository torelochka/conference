package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.TalkDto;

import java.util.List;

public interface TalkService {
    List<TalkDto> getAllTalk();
}
