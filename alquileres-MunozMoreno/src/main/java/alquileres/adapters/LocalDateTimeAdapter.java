package alquileres.adapters;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
	@Override
	public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
		if (localDate != null) {
			jsonWriter.value(localDate.toString());
		} else {
			jsonWriter.value("pendiente");
		}

	}

	@Override
	public LocalDateTime read(final JsonReader jsonReader) throws IOException {
		return LocalDateTime.parse(jsonReader.nextString());
	}
}
