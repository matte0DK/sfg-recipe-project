package matteo.springframework.sfgrecipeproject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matteo.springframework.sfgrecipeproject.commands.UnitOfMeasureCommand;
import matteo.springframework.sfgrecipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import matteo.springframework.sfgrecipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import matteo.springframework.sfgrecipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Slf4j

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;


    @Override
    public Set<UnitOfMeasureCommand> listAllUOMs() {
        log.debug("I'm in the uom service");

        Set<UnitOfMeasureCommand> unitOfMeasureSet = new HashSet<>();
        unitOfMeasureRepository.findAll()
                .iterator()
                .forEachRemaining(unitOfMeasure -> unitOfMeasureSet.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)));

        return unitOfMeasureSet;
    }
}
