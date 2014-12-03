import java.util.Calendar;
import java.util.GregorianCalendar;

import models.Meta;
import models.dao.GenericDAO;
import play.*;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAO dao = new GenericDAO();
	private Calendar calendar;
	private String titulo;
	private String descricao;
	private Meta novaMeta;
	

	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				calendar = new GregorianCalendar(2014, 11-1, 29);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Lab 2 de SI";
				descricao = "Fazer pelo menos a parte 1 do lab 2 de SI";
				novaMeta = new Meta(titulo, descricao, false);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 11-1, 28);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Lab 2 de SI";
				descricao = "Terminar o lab 2 de SI todo";
				novaMeta = new Meta(titulo, descricao, true);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 12-1, 5);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Estudar Logica";
				descricao = "Estudar e fazer os exercicios pra prova";
				novaMeta = new Meta(titulo, descricao, true);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 11-1, 30);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Common Application";
				descricao = "Terminar de preencher os dados pessoais do CoA";
				novaMeta = new Meta(titulo, descricao, false);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 12-1, 1);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Common Application Essays";
				descricao = "Fazer as essays do CoA";
				novaMeta = new Meta(titulo, descricao, true);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 12-1, 15);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Estudar Calculo 2";
				descricao = "Estudar pra prova de Calculo 2 dia 15/12";
				novaMeta = new Meta(titulo, descricao, true);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 12-1, 11);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Estudar pra PLP";
				descricao = "Estudar pro mini teste em grupo da proxima aula";
				novaMeta = new Meta(titulo, descricao, false);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 12-1, 20);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Aprender JSF + Primefaces";
				descricao = "Aprender essas tecnologias pro projeto";
				novaMeta = new Meta(titulo, descricao, false);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 12-1, 15);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Pagar o curso de Inglês";
				descricao = "Lembrar de pagar o curso de Inglês";
				novaMeta = new Meta(titulo, descricao, false);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
				
				calendar = new GregorianCalendar(2014, 12-1, 7);
				calendar.setFirstDayOfWeek(Calendar.SUNDAY);
				titulo = "Comprar presente";
				descricao = "Lembrar de comprar o presente do aniversário de namoro";
				novaMeta = new Meta(titulo, descricao, true);
				novaMeta.setPrazo(calendar);
				novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
				dao.persist(novaMeta);
				dao.flush();
			}
		});
	}
	
	private static int verificaSemana(Calendar metaCalendar){
		int SEMANA_INICIAL = 47;
		return (metaCalendar.get(Calendar.WEEK_OF_YEAR)-SEMANA_INICIAL)+1;
	}
}
