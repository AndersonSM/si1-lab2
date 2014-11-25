

import java.util.Calendar;
import java.util.GregorianCalendar;

import models.Meta;

import org.junit.*;

import static org.junit.Assert.*;

public class MetaTest {
	String nome;
	String descricao;
	Meta meta;
	
	@Before
	public void iniciaMeta(){
		nome = "Aprender scala";
		descricao = "Preciso aprender scala para o lab de SI";
		meta = new Meta(nome, descricao);
	}
	
	@Test
	public void construtorMetaTest(){
		assertTrue(meta.getDescricao().equals(descricao));
		assertTrue(meta.getNome().equals(nome));

		Calendar cal = new GregorianCalendar();
		cal.clear();
		assertTrue(meta.getCalendar().equals(cal));
		
		assertTrue(meta.getPrioridade() == false);
		assertTrue(meta.getSemana() == 0);
	}
	
	@Test
	public void comparaSemanaTest(){
		Meta meta1 = new Meta(nome, descricao);
		meta1.setPrazo(16, 11, 2014);
		meta1.setSemana((meta1.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		Meta meta2 = new Meta(nome, descricao);
		meta2.setPrazo(23, 11, 2014);
		meta2.setSemana((meta2.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		Meta meta3 = new Meta(nome, descricao);
		meta3.setPrazo(30, 11, 2014);
		meta3.setSemana((meta3.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		Meta meta4 = new Meta(nome, descricao);
		meta4.setPrazo(16, 11, 2014);
		meta4.setSemana((meta4.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		assertTrue(meta1.comparaSemana(meta2) == -1);
		assertTrue(meta1.comparaSemana(meta4) == 0);
		assertTrue(meta2.comparaSemana(meta1) == 1);
		assertTrue(meta2.comparaSemana(meta3) == -1);
		assertTrue(meta3.comparaSemana(meta1) == 1);
	}
	
	@Test
	public void comparaPrioridadeTest(){
		Meta meta1 = new Meta(nome, descricao, false);
		Meta meta2 = new Meta(nome, descricao, false);
		Meta meta3 = new Meta(nome, descricao, true);
		Meta meta4 = new Meta(nome, descricao, true);
		
		assertTrue(meta1.comparaPrioridade(meta2) == 0);
		assertTrue(meta3.comparaPrioridade(meta4) == 0);
		assertTrue(meta3.comparaPrioridade(meta1) == -1);
		assertTrue(meta1.comparaPrioridade(meta3) == 1);
	}
	
	@Test
	public void compareToTest(){
		Meta meta1 = new Meta(nome, descricao, false);
		meta1.setPrazo(16, 11, 2014);
		meta1.setSemana((meta1.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		Meta meta2 = new Meta(nome, descricao, false);
		meta2.setPrazo(23, 11, 2014);
		meta2.setSemana((meta2.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		Meta meta3 = new Meta(nome, descricao, false);
		meta3.setPrazo(30, 11, 2014);
		meta3.setSemana((meta3.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		Meta meta4 = new Meta(nome, descricao, true);
		meta4.setPrazo(19, 11, 2014);
		meta4.setSemana((meta4.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		Meta meta5 = new Meta(nome, descricao, true);
		meta5.setPrazo(19, 11, 2014);
		meta5.setSemana((meta5.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		assertTrue(meta1.compareTo(meta4) == 1);
		assertTrue(meta4.compareTo(meta1) == -1);
		assertTrue(meta2.compareTo(meta3) == -1);
		assertTrue(meta3.compareTo(meta2) == 1);
		assertTrue(meta4.compareTo(meta5) == 0);
	}
}
