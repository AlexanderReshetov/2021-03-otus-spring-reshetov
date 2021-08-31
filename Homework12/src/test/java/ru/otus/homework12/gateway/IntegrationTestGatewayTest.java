package ru.otus.homework12.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.homework12.dto.IntegrationTestResultDto;
import ru.otus.homework12.dto.UnitDto;
import ru.otus.homework12.dto.UnitTestResultDto;
import ru.otus.homework12.service.TestApplicationRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@IntegrationComponentScan
@DisplayName("Шлюз интеграционного тестирования должен")
public class IntegrationTestGatewayTest {
    @MockBean
    private TestApplicationRunner testApplicationRunner;
    @Autowired
    private IntegrationTestGateway integrationTestGateway;

    private static final String UNIT_NAME = "unitName";
    private static final String UNIT_TEXT = "unitText";
    private static final Boolean UNIT_RESULT = true;

    @Test
    @DisplayName("передать сообщение и получить ответ")
    void shouldSendMessageAndReceiveAnswer() {
        IntegrationTestResultDto integrationTestResultDto = integrationTestGateway.process(Collections.singletonList(new UnitTestResultDto(new UnitDto(UNIT_NAME, UNIT_TEXT), UNIT_RESULT)));

        assertEquals(1, integrationTestResultDto.getUnitTestResultList().size());
        assertEquals(UNIT_NAME, integrationTestResultDto.getUnitTestResultList().get(0).getUnit().getName());
        assertEquals(UNIT_TEXT, integrationTestResultDto.getUnitTestResultList().get(0).getUnit().getText());
        assertEquals(UNIT_RESULT, integrationTestResultDto.getUnitTestResultList().get(0).getResult());
    }
}
