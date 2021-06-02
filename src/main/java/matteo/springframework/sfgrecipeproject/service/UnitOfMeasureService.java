package matteo.springframework.sfgrecipeproject.service;

import matteo.springframework.sfgrecipeproject.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUOMs();
}
