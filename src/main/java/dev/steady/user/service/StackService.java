package dev.steady.user.service;

import dev.steady.user.domain.Stack;
import dev.steady.user.domain.repository.StackRepository;
import dev.steady.user.dto.response.StackResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;

    @Transactional(readOnly = true)
    public StackResponses getStacks() {
        List<Stack> stacks = stackRepository.findAll();
        return StackResponses.from(stacks);
    }

}
