package br.com.upsolutions.controller.acessoTanque;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ManagedBean
@ApplicationScoped
public class Calculo {
	
	@PersistenceContext
    protected EntityManager entityManager;

	public double caluculaVolumeHorizontal(double alturaLiquido, double comprimentoCilindro, double raioCilindro) {
		double h = alturaLiquido;
		double l = comprimentoCilindro;
		double r = raioCilindro;
		double volume = (Math.pow(r, 2) * Math.acos((r - h) / r) - (r - h) * Math.pow((h * (2 * r - h)), 0.5)) * l;
		volume = volume / 100;
		return volume;
	}

	public double caluculaVolumeVertical(double alturaLiquido, double raioCilindro) {
		double l = alturaLiquido;
		double r = raioCilindro;
		double volume = ((Math.pow(r, 2)) * 3.14) * l;
		return volume / 1000;
	}
}