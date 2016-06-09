package es.upm.monitorizacion.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import es.upm.monitorizacion.emf.EMFService;
import es.upm.monitorizacion.model.ResumenDisp;

public class ResumenDispDAOImpl implements ResumenDispDAO {

private static ResumenDispDAOImpl instance;
	
	private ResumenDispDAOImpl() {
	}
	
	public static ResumenDispDAOImpl getInstance() {
		if (instance == null)
			instance = new ResumenDispDAOImpl();
		return instance;
	}

	@Override
	public ResumenDisp create(Long systimeIN, int canal, String lAP,
			Long systimeOUT, String fechaEntrada, String fechaSalida,
			int sNRmedia) {

		EntityManager em = EMFService.get().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		ResumenDisp dispositivo = new ResumenDisp(systimeIN, canal, lAP, systimeOUT, fechaEntrada, fechaSalida, sNRmedia);
		transaction.begin();
		em.persist(dispositivo);
		transaction.commit();
		em.close();
		
		return dispositivo;
	}

	@Override
	public List<ResumenDisp> readResumenDisp() {

		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from ResumenDisp t");

		List<ResumenDisp> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}

	@Override
	public List<ResumenDisp> readResumenDisp_LAP(String LAP) {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from ResumenDisp t where t.LAP = :LAP");
	    q.setParameter("LAP", LAP);
	    
	    List<ResumenDisp> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}

	@Override
	public List<ResumenDisp> readResumenDisp_systimeIN(Long systimeIN) {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from ResumenDisp t where t.systimeIN = :systimeIN");
	    q.setParameter("systimeIN", systimeIN);
	    
	    List<ResumenDisp> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}
	
	@Override
	public List<ResumenDisp> readResumenDisp_systimeOUT(Long systimeOUT) {
		
		EntityManager em = EMFService.get().createEntityManager();
		
		Query q = em.createQuery("select t from ResumenDisp t where t.systimeOUT = :systimeOUT");
	    q.setParameter("systimeOUT", systimeOUT);
	    
	    List<ResumenDisp> dispositivos = q.getResultList();
		em.close();
		
		return dispositivos;
	}

}
