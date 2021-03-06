package br.com.upsolutions.controller.mb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.upsolutions.business.dao.CalculoTanqueDao;
import br.com.upsolutions.business.entity.CalculoTanque;

@ViewScoped
@ManagedBean
public class CalculoTanqueMB {

	@EJB
	private CalculoTanqueDao calculoTanqueDao;

	public List<CalculoTanque> listaCalculosCliente() throws Exception {
		try {
			return calculoTanqueDao.listaCalculosCliente();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	public double testeDefinicaoImgemNivel(double nivel, double tanqueCheio) {
		double percentual = (nivel / tanqueCheio) * 100;
		return arrendondamento(percentual);
	}
	public double arrendondamento(double number){
		BigDecimal progress=new BigDecimal(number).setScale(1, RoundingMode.HALF_EVEN);
		System.out.println(progress.doubleValue());
		return progress.doubleValue();

	}

}