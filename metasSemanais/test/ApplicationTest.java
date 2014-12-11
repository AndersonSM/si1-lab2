import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import models.Meta;
import models.dao.GenericDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.twirl.api.Html;
import scala.Option;
import views.html.index;


public class ApplicationTest {

	GenericDAO dao = new GenericDAO();
	List<Meta> metas;
	Meta meta1;
	Meta meta2;
	Meta meta3;
	private static int[] contMetasSemana = {0,0,0,0,0,0};
	int contMetasCumpridas = 0;
	int contMetasNaoCumpridas = 0;
    public EntityManager em;
    
    @Before
    public void setUp() {
        FakeApplication app = Helpers.fakeApplication(new GlobalSettings());
        Helpers.start(app);
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
        em.getTransaction().begin();
        atualizaVariaveis();
        
        
        metas = new ArrayList<Meta>();
        
        meta1 = new Meta("Aprender scala", "Aprender scala pro lab");
		meta1.setPrazo(16, 11, 2014);
		meta1.setSemana((meta1.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		meta2 = new Meta("Estudar pra SI", "Estudar pro mini teste dia 20");
		meta2.setPrazo(23, 11, 2014);
		meta2.setSemana((meta2.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
		
		meta3 = new Meta("Enviar docs do CSF", "Lembrar de enviar os docs necessarios pro csf");
		meta3.setPrazo(30, 11, 2014);
		meta3.setSemana((meta3.getCalendar().get(Calendar.WEEK_OF_YEAR)-47)+1);
    }
    
    @Test
    public void deveIniciarSemMetas(){
    	metas = dao.findAllByClassName("Meta");
    	atualizaVariaveis();
    	Html html = index.render(metas, contMetasSemana, contMetasCumpridas, contMetasNaoCumpridas);
    	assertThat(contentAsString(html)).contains("Nenhuma meta adicionada.");
    	assertTrue(metas.size() == 0);
    	assertTrue(contMetasCumpridas == 0);
    	assertTrue(contMetasNaoCumpridas == 0);
    	for (int i = 0; i < 6; i++) {
    		assertTrue(contMetasSemana[i] == 0);
		}
    }
    
    @Test
    public void deveAdicionarMeta(){
    	Map<String, String> formData = new HashMap<String, String>();
		formData.put("titulo", "Aprender scala");
		formData.put("descricao", "Aprender scala pro lab");
		formData.put("prazo", "2014-11-16");
		formData.put("prioridade", "0");
		Result result = callAction(controllers.routes.ref.Application.addMeta(), fakeRequest()
						.withFormUrlEncodedBody(formData));
    	
		atualizaVariaveis();
    	metas = dao.findAllByClassName("Meta");
    	
    	assertTrue(metas.size() == 1);
    	assertTrue(contMetasCumpridas == 0);
    	assertTrue(contMetasNaoCumpridas == 1);
    	
    	formData = new HashMap<String, String>();
		formData.put("titulo", "Estudar pra SI");
		formData.put("descricao", "Estudar pro mini teste de SI dia 20");
		formData.put("prazo", "2014-11-23");
		formData.put("prioridade", "0");
		result = callAction(controllers.routes.ref.Application.addMeta(), fakeRequest()
						.withFormUrlEncodedBody(formData));
		
    	atualizaVariaveis();
		metas = dao.findAllByClassName("Meta");
    	
    	assertTrue(metas.size() == 2);
    	assertTrue(contMetasCumpridas == 0);
    	assertTrue(contMetasNaoCumpridas == 2);
    	
    	Html html = index.render(metas, contMetasSemana, contMetasCumpridas, contMetasNaoCumpridas);
    	assertThat(contentAsString(html)).contains(meta1.getNome());
    	assertThat(contentAsString(html)).contains(meta2.getNome());
    }
    
    @Test
    public void deveAlterarStatus(){
    	dao.persist(meta1);
    	dao.flush();
    	metas = dao.findAllByClassName("Meta");
    	atualizaVariaveis();
    	Html html = index.render(metas, contMetasSemana, contMetasCumpridas, contMetasNaoCumpridas);
    	
    	assertThat(contentAsString(html)).contains("A cumprir");
    	assertTrue(contMetasCumpridas == 0);
    	
    	meta1.setCumprida(true);
    	dao.merge(meta1);
    	dao.flush();
    	
    	controllers.Application.updateContadores();
    	atualizaVariaveis();
    	
    	assertTrue(contMetasCumpridas == 1);
    	assertTrue(contMetasNaoCumpridas == 0);
    	
    	html = index.render(metas, contMetasSemana, contMetasCumpridas, contMetasNaoCumpridas);
    	
    	assertThat(contentAsString(html)).contains("Cumprida");
    }
    
    @Test
    public void deveDeletarMeta(){
    	dao.persist(meta1);
    	dao.flush();
    	metas = dao.findAllByClassName("Meta");
    	Html html = index.render(metas, contMetasSemana, contMetasCumpridas, contMetasNaoCumpridas);
    	assertThat(contentAsString(html)).contains(meta1.getNome());
    	
    	dao.removeById(Meta.class, meta1.getId());
    	controllers.Application.updateContadores();
    	atualizaVariaveis();
    	metas = dao.findAllByClassName("Meta");
    	html = index.render(metas, contMetasSemana, contMetasCumpridas, contMetasNaoCumpridas);
    	assertThat(contentAsString(html)).contains("Nenhuma meta adicionada.");
    	
    }
    
    private void atualizaVariaveis(){
    	contMetasCumpridas = controllers.Application.getContMetasCumpridas();
        contMetasNaoCumpridas = controllers.Application.getContMetasNaoCumpridas();
        contMetasSemana = controllers.Application.getContMetasSemana();
    }
    
    @After
    public void tearDown() {
        em.getTransaction().commit();
        JPA.bindForCurrentThread(null);
        em.close();
        controllers.Application.setContMetasCumpridas(0);
        controllers.Application.setContMetasNaoCumpridas(0);
        controllers.Application.setContMetasSemana(new int[6]);
    }
}
