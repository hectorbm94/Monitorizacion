package es.upm.monitorizacion.dao;

import es.upm.monitorizacion.emf.EMFService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.upm.monitorizacion.model.Dispositivos;

public class DispositivosDAOImpl implements DispositivosDAO {

	private static DispositivosDAOImpl instance;
	
	private DispositivosDAOImpl() {
	}
	
	public static DispositivosDAOImpl getInstance() {
		if (instance == null)
			instance = new DispositivosDAOImpl();
		return instance;
	}

	@Override
	public Dispositivos create(Long systime, int canal, String lAP,
			int potenciaSeñal, int potenciaRuido, int sNR) {
		
		EntityManager em = EMFService.get().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		Dispositivos dispositivo = new Dispositivos(systime, canal, lAP, potenciaSeñal, potenciaRuido, sNR);
		transaction.begin();
		em.persist(dispositivo);
		transaction.commit();
		em.close();
		
		return dispositivo;
	}

	@Override
	public List<Dispositivos> readDispositivos() {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from Dispositivos t");

		List<Dispositivos> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}

	@Override
	public List<Dispositivos> readDispositivos_LAP(String LAP) {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from Dispositivos t where t.LAP = :LAP");
	    q.setParameter("LAP", LAP);
	    
	    List<Dispositivos> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	    
	}

	@Override
	public List<Dispositivos> readDispositivos_canal(int canal) {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from Dispositivos t where t.canal = :canal");
	    q.setParameter("canal", canal);
	    
	    List<Dispositivos> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}

	@Override
	public List<Dispositivos> readDispositivos_id(Long id) {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from Dispositivos t where t.id = :id");
	    q.setParameter("id", id);
	    
	    List<Dispositivos> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}

	@Override
	public List<Dispositivos> readDispositivos_systime(Long systime) {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from Dispositivos t where t.systime = :systime");
	    q.setParameter("systime", systime);
	    
	    List<Dispositivos> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}
	
	@Override
	public void borrado() {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("DELETE FROM Dispositivos t");
	    
	    q.executeUpdate();
		em.close();
		
	}
}
