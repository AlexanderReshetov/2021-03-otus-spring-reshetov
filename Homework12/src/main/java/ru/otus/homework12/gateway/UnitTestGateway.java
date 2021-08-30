package ru.otus.homework12.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework12.dto.UnitDto;
import ru.otus.homework12.dto.UnitTestResultDto;

import java.util.List;

import static ru.otus.homework12.config.IntegrationFlowConfig.UNIT_CHANNEL;
import static ru.otus.homework12.config.IntegrationFlowConfig.UNIT_TEST_CHANNEL;

@MessagingGateway
public interface UnitTestGateway {
    @Gateway(requestChannel = UNIT_CHANNEL, replyChannel = UNIT_TEST_CHANNEL)
    List<UnitTestResultDto> process(List<UnitDto> unitDtoList);
}
