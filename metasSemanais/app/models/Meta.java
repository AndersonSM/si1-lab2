package models;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.*;


@Entity
public class Meta implements Comparable<Meta>{
	@Transient
	public final int SEM_PRIORIDADE = 0;
	@Transient
	public final int COM_PRIORIDADE = 1;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private int semana;
	private String titulo;
	private String descricao;
	private int prioridade;
	private boolean cumprida;
	
	@Temporal(TemporalType.DATE)
	private Calendar prazo;
	
	public Meta(String titulo, String descricao){
		this.setNome(titulo);
		this.setDescricao(descricao);
		this.setPrioridade(SEM_PRIORIDADE);
		this.setCumprida(false);
		this.setPrazo();
	}
	
	public Meta(String titulo, String descricao, int prioridade){
		this.setNome(titulo);
		this.setDescricao(descricao);
		this.setPrioridade(prioridade);
		this.setCumprida(false);
		this.setPrazo();
	}
	
	public Meta(){
	}

	
	@Override
	public int compareTo(Meta o) {
		int result = this.comparaSemana(o);
		if(result == 0){
			result = this.comparaPrioridade(o);
			if(result == 0){
				result = this.getCalendar().compareTo(o.getCalendar());
			}
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
		return o.getPrioridade() - this.prioridade;
	}
	
	public Long getId() {
		return id;
	}

	/*private void setId(Long id) {
		this.id = id;
	}*/

	public String getNome() {
		return titulo;
	}

	public void setNome(String nome) {
		this.titulo = nome;
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
	
	public Calendar getCalendar(){
		return prazo;
	}

	public void setPrazo(int dia, int mes, int ano) {
		this.prazo = new GregorianCalendar(ano, mes-1, dia);
		this.prazo.setFirstDayOfWeek(Calendar.SUNDAY);
	}
	
	public void setPrazo(Calendar calendar){
		this.prazo = calendar;
	}
	
	public void setPrazo(){
		this.prazo = new GregorianCalendar();
		this.prazo.clear();
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public boolean isCumprida() {
		return cumprida;
	}

	public void setCumprida(boolean cumpriu) {
		this.cumprida = cumpriu;
	}

	public int getSemana() {
		return semana;
	}

	public void setSemana(int semana) {
		this.semana = semana;
	}
}
