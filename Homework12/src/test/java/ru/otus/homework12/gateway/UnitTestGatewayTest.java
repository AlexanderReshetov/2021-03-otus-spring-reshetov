package ru.otus.homework12.gateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.homework12.dto.UnitDto;
import ru.otus.homework12.dto.UnitTestResultDto;
import ru.otus.homework12.service.TestApplicationRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@IntegrationComponentScan
@DisplayName("Шлюз юнит-тестирования должен")
public class UnitTestGatewayTest {
    @MockBean
    private TestApplicationRunner testApplicationRunner;
    @Autowired
    private UnitTestGateway unitTestGateway;

    private static final String UNIT_NAME = "unitName";
    private static final String UNIT_TEXT = "unitText";

    @Test
    @DisplayName("передать сообщение и получить ответ")
    void shouldSendMessageAndReceiveAnswer() {
        List<UnitTestResultDto> unitTestResultDtoList = unitTestGateway.process(Collections.singletonList(new UnitDto(UNIT_NAME, UNIT_TEXT)));

        assertEquals(1, unitTestResultDtoList.size());
        assertEquals(UNIT_NAME, unitTestResultDtoList.get(0).getUnit().getName());
        assertEquals(UNIT_TEXT, unitTestResultDtoList.get(0).getUnit().getText());
    }
}
