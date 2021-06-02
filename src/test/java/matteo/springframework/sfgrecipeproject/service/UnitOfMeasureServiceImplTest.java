package matteo.springframework.sfgrecipeproject.service;

import matteo.springframework.sfgrecipeproject.commands.UnitOfMeasureCommand;
import matteo.springframework.sfgrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import matteo.springframework.sfgrecipeproject.model.UnitOfMeasure;
import matteo.springframework.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureServiceImpl unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUOMs() {
        // given
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();

        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);
        unitOfMeasureSet.add(unitOfMeasure1);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure1.setId(2L);
        unitOfMeasureSet.add(unitOfMeasure2);

        // when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);
        Set<UnitOfMeasureCommand> unitOfMeasureCommandSet = unitOfMeasureService.listAllUOMs();

        // then
        assertEquals(2, unitOfMeasureCommandSet.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}