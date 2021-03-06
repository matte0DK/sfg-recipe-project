package matteo.springframework.sfgrecipeproject.converters;

import lombok.Synchronized;
import matteo.springframework.sfgrecipeproject.commands.NotesCommand;
import matteo.springframework.sfgrecipeproject.model.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes notes) {
        if (notes == null) {
            return null;
        }

        NotesCommand notesCommand = new NotesCommand();

        notesCommand.setId(notes.getId());
        notesCommand.setRecipeNotes(notes.getRecipeNotes());

        return notesCommand;
    }
}
