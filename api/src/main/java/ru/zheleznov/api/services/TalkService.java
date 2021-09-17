package ru.zheleznov.api.services;

import ru.zheleznov.api.dto.TalkDto;
import ru.zheleznov.api.dto.UserDto;
import ru.zheleznov.api.forms.TalkCreateForm;

import java.util.List;

public interface TalkService {
    List<TalkDto> getAllTalk();
    TalkDto save(TalkCreateForm talkForm);

    Boolean deleteTalk(Long id, UserDto userDto);
    Boolean deleteTalk(Long id);

    TalkDto updateTalk(Long id, TalkCreateForm talkCreateForm);

    TalkDto getTalk(Long id);
}
