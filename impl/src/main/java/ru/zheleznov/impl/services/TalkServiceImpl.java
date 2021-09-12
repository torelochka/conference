package ru.zheleznov.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zheleznov.api.dto.TalkDto;
import ru.zheleznov.api.services.TalkService;
import ru.zheleznov.impl.repositories.TalkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TalkServiceImpl implements TalkService {

    private final TalkRepository talkRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TalkServiceImpl(TalkRepository talkRepository, ModelMapper modelMapper) {
        this.talkRepository = talkRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TalkDto> getAllTalk() {
        return talkRepository.findAll()
                .stream().map(talk -> modelMapper.map(talk, TalkDto.class))
                .collect(Collectors.toList());
    }
}
