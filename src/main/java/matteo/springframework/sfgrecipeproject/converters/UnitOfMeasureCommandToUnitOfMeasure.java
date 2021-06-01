package matteo.springframework.sfgrecipeproject.converters;

import lombok.Synchronized;
import matteo.springframework.sfgrecipeproject.commands.UnitOfMeasureCommand;
import matteo.springframework.sfgrecipeproject.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasureCommand) {
        if (unitOfMeasureCommand == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();

        uom.setId(unitOfMeasureCommand.getId());
        uom.setDescription(unitOfMeasureCommand.getDescription());

        return uom;
    }
}
