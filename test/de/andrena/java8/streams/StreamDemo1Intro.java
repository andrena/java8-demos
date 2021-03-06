package de.andrena.java8.streams;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import de.andrena.java8.Adresse;
import de.andrena.java8.Person;

public class StreamDemo1Intro extends TestMit100000Personen {

	@Test
	public void filterLoop() {
		Person mueller = null;
		for (Person person : personen) {
			if ("Müller".equals(person.getNachname())) {
				mueller = person;
				break;
			}
		}

		assertThat(mueller, is(notNullValue()));
	}

	@Test
	public void filter() {
		Person mueller = personen.stream() //
				.filter(person -> "Müller".equals(person.getNachname())) //
				.findFirst().get();

		assertThat(mueller, is(notNullValue()));
	}

	@Test
	public void mapLoop() {
		List<String> nachnamen = new ArrayList<>();
		for (Person person : personen) {
			nachnamen.add(person.getNachname());
		}

		assertThat(nachnamen, hasSize(100000));
	}

	@Test
	public void map() {
		List<String> nachnamen = personen.stream() //
				.map(person -> person.getNachname()) //
				.collect(Collectors.toList());

		assertThat(nachnamen, hasSize(100000));
	}

	@Test
	public void mapAndFilter() {
		List<String> vornamen = personen.stream() //
				.filter(person -> "Müller".equals(person.getNachname())) //
				.map(person -> person.getVorname()) //
				.collect(Collectors.toList());

		assertThat(vornamen, hasSize(950));
	}

	@Test
	public void loopingIsUgly() {
		boolean esGibtJemandAusKarlsruheDer1917GeborenWurde = false;
		for (Person person : personen) {
			if (person.getGeburtstag().getYear() == 1917) {
				for (Adresse adresse : person.getAdressen()) {
					if ("Karlsruhe".equals(adresse.getStadt())) {
						esGibtJemandAusKarlsruheDer1917GeborenWurde = true;
						break;
					}
				}
			}
		}

		assertTrue(esGibtJemandAusKarlsruheDer1917GeborenWurde);
	}

	@Test
	public void streamingIsBeautiful() {
		boolean esGibtJemandAusKarlsruheDer1917GeborenWurde = personen.stream()
				.filter(person -> person.getGeburtstag().getYear() == 1917)
				.flatMap(person -> person.getAdressen().stream())
				.anyMatch(adresse -> "Karlsruhe".equals(adresse.getStadt()));

		assertTrue(esGibtJemandAusKarlsruheDer1917GeborenWurde);
	}

}
