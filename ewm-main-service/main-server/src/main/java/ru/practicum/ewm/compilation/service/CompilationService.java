package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto dto);

    void delete(long compId);

    CompilationDto update(long compId, UpdateCompilationRequest dto);

    List<CompilationDto> getPublicCompilations(Boolean pinned, int from, int size);

    CompilationDto getPublicCompilation(long compId);
}
