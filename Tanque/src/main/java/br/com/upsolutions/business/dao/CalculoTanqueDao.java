package br.com.upsolutions.business.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.upsolutions.business.entity.CalculoTanque;

@Stateless
public class CalculoTanqueDao extends GenericDao<CalculoTanque> {

	@SuppressWarnings("unchecked")
	public List<CalculoTanque> listaCalculosCliente() {
		try {
			Query query = null;
			query = createNamedQuery("CalculoTanque.listCalculos");
			query.setParameter("cliente", "TRATASUL");
			List<CalculoTanque> calculos = query.getResultList();
			for (CalculoTanque c : calculos)
				System.out.println("Código: " + c.getCodigo());
			return calculos;
		} catch (IllegalStateException e) {
			e.printStackTrace();
//			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
//			throw new Exception(e);
		}
		return null;
	}
}
