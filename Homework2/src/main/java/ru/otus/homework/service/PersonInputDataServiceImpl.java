package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Person;

@Service
public class PersonInputDataServiceImpl implements PersonInputDataService {
    private final IOService ioservice;

    @Autowired
    public PersonInputDataServiceImpl(IOService ioservice) {
        this.ioservice = ioservice;
    }

    public Person inputData() throws PersonInputDataException{
        try {
            ioservice.print("Your surname: ");
            final Person person = new Person(ioservice.readLine());
            ioservice.print("Your name: ");
            person.setName(ioservice.readLine());
            return person;
        }
        catch(IOServiceException e) {
            throw new PersonInputDataException("Person data input error", e);
        }
    }
}
