package matteo.springframework.sfgrecipeproject.repositories;

import matteo.springframework.sfgrecipeproject.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
Integration tests are designed to test behaviors between objects and parts of the overall system! :
* they have a much larger scope than Unit tests (wich are designed to test specific details in the code)
* they can include the spring context, database and ?message brokers?
* will run much slower than Unit tests
*/

@ExtendWith(SpringExtension.class)
@DataJpaTest // jpa specific test?
public class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void findByDescription() throws Exception {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }
}