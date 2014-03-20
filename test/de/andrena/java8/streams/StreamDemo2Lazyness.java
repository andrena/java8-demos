package de.andrena.java8.streams;

import static de.andrena.java8.PersonenGenerator.personenStream;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import org.junit.Test;

import de.andrena.java8.Person;

public class StreamDemo2Lazyness {

	@Test
	public void streamsPipelinesSindLazy() throws Exception {
		Stream<Person> stream = personenStream() //
				.limit(10_000) //
				.filter(person -> {
					return "Müller".equals(person.getNachname());
				});

		System.out.println("Wird ausgeführt bevor gefiltert wird");

		assertThat(stream.count(), is(greaterThan(1L)));
	}
}
