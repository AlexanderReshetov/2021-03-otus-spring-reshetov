package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.service.exception.IOServiceException;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;

@Service
public class MessageSourceIOServiceImpl implements MessageSourceIOService {
    private final IOService ioService;
    private final MessageSourceService messageSourceService;

    public MessageSourceIOServiceImpl(IOService ioService, MessageSourceService messageSourceService) {
        this.ioService = ioService;
        this.messageSourceService = messageSourceService;
    }

    public void print(String name, Object...varArgs) {
        ioService.print(messageSourceService.getMessage(name, varArgs));
    }

    public void println(String name, Object...varArgs) {
        ioService.println(messageSourceService.getMessage(name, varArgs));
    }

    public String readLine() throws MessageSourceIOServiceException {
        try {
            return ioService.readLine();
        }
        catch(IOServiceException e) {
            throw new MessageSourceIOServiceException("Input error in input/output facade", e);
        }
    }
}
