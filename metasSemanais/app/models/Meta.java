package models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Meta implements Comparable<Meta>{
	public final int SEM_PRIORIDADE = 0;
	public final int COM_PRIORIDADE = 1;
		
	private int id;
	private String nome;
	private String descricao;
	private Calendar prazo;
	private int prioridade;
	
	public Meta(String nome, String descricao){
		this.setNome(nome);
		this.setDescricao(descricao);
		this.setPrioridade(SEM_PRIORIDADE);
	}
	
	public Meta(String nome, String descricao, int prioridade){
		this.setNome(nome);
		this.setDescricao(descricao);
		this.setPrioridade(prioridade);
	}
	
	public Meta(){
	}

	
	@Override
	public int compareTo(Meta o) {
		int result = this.comparaSemana(o);
		if(result == 0){
			result = this.comparaPrioridade(o);
		}
		return result;
	}

	private int comparaSemana(Meta o){
		int result;
		
		if(this.getCalendar().get(Calendar.WEEK_OF_YEAR) == o.getCalendar().get(Calendar.WEEK_OF_YEAR)){
			result = 0;
		}
		else if(this.getCalendar().get(Calendar.WEEK_OF_YEAR) > o.getCalendar().get(Calendar.WEEK_OF_YEAR)){
			result = 1;
		}
		else{
			result = -1;
		}
		
		return result;
	}
	
	private int comparaPrioridade(Meta o){
		int result;
		
		if(this.prioridade == COM_PRIORIDADE && o.getPrioridade() == COM_PRIORIDADE){
			result = 0;
		}
		else if(this.prioridade == COM_PRIORIDADE && o.getPrioridade() == SEM_PRIORIDADE){
			result = 1;
		}
		else{
			result = -1;
		}
		
		return result;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPrazo() {
		return "" + prazo.get(Calendar.DAY_OF_MONTH) + "/" + (prazo.get(Calendar.MONTH)+1) + "/" + prazo.get(Calendar.YEAR);
	}
	
	private Calendar getCalendar(){
		return prazo;
	}

	public void setPrazo(int dia, int mes, int ano) {
		this.prazo = new GregorianCalendar(ano, mes-1, dia);
		this.prazo.setFirstDayOfWeek(Calendar.SUNDAY);
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
}
