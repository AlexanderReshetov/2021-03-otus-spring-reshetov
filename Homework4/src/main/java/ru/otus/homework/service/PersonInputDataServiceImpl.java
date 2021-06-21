package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;
import ru.otus.homework.service.exception.PersonInputDataException;


@Service
public class PersonInputDataServiceImpl implements PersonInputDataService {
    private final MessageSourceIOService messageSourceIOService;

    @Autowired
    public PersonInputDataServiceImpl(MessageSourceIOService messageSourceIOService) {
        this.messageSourceIOService = messageSourceIOService;
    }

    public Person inputData() throws PersonInputDataException {
        try {
            messageSourceIOService.print("message.input.surname", null);
            final Person person = new Person(messageSourceIOService.readLine());
            messageSourceIOService.print("message.input.name", null);
            person.setName(messageSourceIOService.readLine());
            return person;
        }
        catch(MessageSourceIOServiceException e) {
            throw new PersonInputDataException("Person data input error", e);
        }
    }
}
