package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;


@Service
public class PersonInputDataServiceImpl implements PersonInputDataService {
    private final IOService ioservice;
    private final MessageSourceService messageSourceService;

    @Autowired
    public PersonInputDataServiceImpl(IOService ioservice, MessageSourceService messageSourceService) {
        this.ioservice = ioservice;
        this.messageSourceService = messageSourceService;
    }

    public Person inputData() throws PersonInputDataException{
        try {
            ioservice.print(messageSourceService.getMessage("message.input.surname", null));
            final Person person = new Person(ioservice.readLine());
            ioservice.print(messageSourceService.getMessage("message.input.name", null));
            person.setName(ioservice.readLine());
            return person;
        }
        catch(IOServiceException e) {
            throw new PersonInputDataException("Person data input error", e);
        }
    }
}
