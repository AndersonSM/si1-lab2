package controllers;

import java.util.*;

import models.Meta;
import models.dao.GenericDAO;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	private static GenericDAO dao = new GenericDAO();
	private static List<Meta> metas; 
	private static int semanaInicial = 47;
	private static int semanaFinal = 52;
	private static int[] contMetasSemana = {0,0,0,0,0,0};
	private static int contMetasCumpridas = 0;
	private static int contMetasNaoCumpridas = 0;

	@Transactional
    public static Result index() {
		getMetasFromDB();
		return ok(index.render(metas, contMetasSemana, contMetasCumpridas, contMetasNaoCumpridas));
    }
	
	@Transactional
	public static Result addMeta(){
		DynamicForm requestData = Form.form().bindFromRequest();
		
		String[] prazo = requestData.get("prazo").split("-");
		int ano = Integer.parseInt(prazo[0]);
		int mes = Integer.parseInt(prazo[1]);
		int dia = Integer.parseInt(prazo[2]);
		Calendar calendar = new GregorianCalendar(ano, mes-1, dia);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		
		if(calendar.get(Calendar.WEEK_OF_YEAR) < semanaInicial || calendar.get(Calendar.WEEK_OF_YEAR) > semanaFinal){
			Logger.warn("Valor inválido para a data.");
			return redirect("/");
		}
		
		String titulo = requestData.get("titulo");
		String descricao = requestData.get("descricao");
		
		boolean prior;
		int prioridade = Integer.parseInt(requestData.get("prioridade"));
		if(prioridade == 1)
			prior = true;
		else prior = false;
		
		Meta novaMeta = new Meta(titulo, descricao, prior);
		novaMeta.setPrazo(calendar);
		novaMeta.setSemana(verificaSemana(novaMeta.getCalendar()));
		
		
		getDAO().persist(novaMeta);
		getDAO().flush();
		
		Logger.info("Nova meta adicionada ao banco de dados.");
		
		updateContadores();
		
		return redirect("/");
		//return index();
	}
	
	@Transactional
	public static Result removeMeta(Long id){
		getDAO().removeById(Meta.class, id);
		getDAO().flush();
		
		Logger.info("Meta removida com sucesso.");
		
		updateContadores();
		
		return redirect("/");
		//return index();
	}

	@Transactional
	public static Result updateMeta(Long id){
		Meta meta = getDAO().findByEntityId(Meta.class, id);
		if(!meta.isCumprida())
			meta.setCumprida(true);
		else meta.setCumprida(false);
		getDAO().merge(meta);
		getDAO().flush();
		
		Logger.info("Meta atualizada.");
		
		updateContadores();
		
		return redirect("/");
	}
	
	@Transactional
	private static void getMetasFromDB(){
		metas = getDAO().findAllByClassName("Meta");
		getDAO().flush();
		Collections.sort(metas);
	}

	public static void updateContadores(){
		getMetasFromDB();
		
		contMetasCumpridas = 0;
		contMetasNaoCumpridas = 0;
		contMetasSemana = new int[6];
		
		for (Meta meta : metas) {
			if(!meta.isCumprida()){
				switch (meta.getSemana()) {
				case 1:
					contMetasSemana[0]++;
					break;
				case 2:
					contMetasSemana[1]++;
					break;
				case 3:
					contMetasSemana[2]++;
					break;
				case 4:
					contMetasSemana[3]++;
					break;
				case 5:
					contMetasSemana[4]++;
					break;
				case 6:
					contMetasSemana[5]++;
					break;
				}
			}
			if(meta.isCumprida()) 
				contMetasCumpridas++;
			else
				contMetasNaoCumpridas++;
		}
	}
	
	private static int verificaSemana(Calendar metaCalendar){
		return (metaCalendar.get(Calendar.WEEK_OF_YEAR)-semanaInicial)+1;
	}
	
	public static GenericDAO getDAO(){
		return dao;
	}

	public static List<Meta> getMetas(){
		return metas;
	}

	public static int[] getContMetasSemana() {
		return contMetasSemana;
	}

	public static int getContMetasCumpridas() {
		return contMetasCumpridas;
	}

	public static int getContMetasNaoCumpridas() {
		return contMetasNaoCumpridas;
	}

	public static void setContMetasSemana(int[] contMetasSemana) {
		Application.contMetasSemana = contMetasSemana;
	}

	public static void setContMetasCumpridas(int contMetasCumpridas) {
		Application.contMetasCumpridas = contMetasCumpridas;
	}

	public static void setContMetasNaoCumpridas(int contMetasNaoCumpridas) {
		Application.contMetasNaoCumpridas = contMetasNaoCumpridas;
	}
	
	
}
