package ru.otus.homework12.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework12.dto.IntegrationTestResultDto;
import ru.otus.homework12.dto.UnitTestResultDto;

import java.util.List;

import static ru.otus.homework12.config.IntegrationFlowConfig.UNIT_TEST_CHANNEL;

@MessagingGateway
public interface IntegrationTestGateway {
    @Gateway(requestChannel = UNIT_TEST_CHANNEL)
    IntegrationTestResultDto process(List<UnitTestResultDto> unitTestResultDtoList);
}
