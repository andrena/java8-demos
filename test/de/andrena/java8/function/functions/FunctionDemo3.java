package de.andrena.java8.function.functions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.andrena.java8.Person;

public class FunctionDemo3 {

	@Test
	public void allePersonenMitGeburtsdatumErsetzen() throws Exception {
		Person antonio = new Person("Antonio", "Calabrese");
		Person julia = new Person("Julia", "Hall", LocalDate.of(1978, 3, 25));
		Person stefan = new Person("Stefan", "Maier");

		List<Person> personen = Arrays.asList(antonio, julia, stefan);

		personen.replaceAll(p -> (p.getGeburtstag() == null) ? p : p.ohneGeburtstag());

		assertThat(personen.get(0), is(antonio));
		assertThat(personen.get(1).getVorname(), is("Julia"));
		assertThat(personen.get(1).getGeburtstag(), is(nullValue()));
		assertThat(personen.get(2), is(stefan));
	}
}
