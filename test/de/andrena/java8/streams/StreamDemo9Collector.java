package de.andrena.java8.streams;

import static de.andrena.java8.PersonenGenerator.personenStream;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import de.andrena.java8.Person;

public class StreamDemo9Collector {

	@Test
	public void joining() {
		Person antonio = new Person("Antonio", "Calabrese");
		Person julia = new Person("Julia", "Hall");
		Person stefan = new Person("Stefan", "Maier");
		List<Person> personenListe = Arrays.asList(antonio, julia, stefan);

		String vornamen = personenListe.stream() //
				.map(Person::getVorname) //
				.collect(Collectors.joining(", "));

		assertThat(vornamen, is("Antonio, Julia, Stefan"));
	}

	@Test
	public void grouping() {
		Map<String, List<Person>> personenNachStadt = personenStream() //
				.limit(100_000) //
				.filter(person -> !person.getAdressen().isEmpty()) //
				.collect(Collectors.groupingBy( //
						person -> person.getAdressen().get(0).getStadt()));

		personenNachStadt.entrySet() //
				.stream() //
				.sorted(Comparator.comparing(entry -> entry.getKey())) //
				.forEach(entry -> System.out.println( //
						entry.getKey() + ": " + entry.getValue().size()));

		System.out.println(personenNachStadt.size());

		assertThat(personenNachStadt.get("Karlsruhe"), hasSize(48));
		assertThat(personenNachStadt.get("Stuttgart"), hasSize(51));
	}

	@Test
	public void groupingByAge() {
		Map<Integer, List<Person>> personenNachAlter = personenStream() //
				.limit(100_000) //
				.filter(person -> !person.getAdressen().isEmpty()) //
				.collect(groupingBy(person -> person.alterInJahren()));

		personenNachAlter.entrySet() //
				.stream() //
				.sorted(Comparator.comparing(entry -> entry.getKey())) //
				.forEach(entry -> System.out.println( //
						entry.getKey() + ": " + entry.getValue().size()));

		System.out.println(personenNachAlter.size());

		assertThat(personenNachAlter.get(34), hasSize(greaterThan(500)));
		assertThat(personenNachAlter.get(99), hasSize(greaterThan(500)));
	}
}
