package br.com.upsolutions.controller.temporizador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Singleton
@Lock // Use (LockType.READ) allows timers to execute in parallel
@Startup
public class TanqueTemp implements Serializable {

	@Resource
	private TimerService timerService;

	@PostConstruct
	public void construct() {
		stopTimers();
//		final TimerConfig timerConfig = new TimerConfig(EMAIL_RANKING_LOJAS, false);
//        timerService.createCalendarTimer(new ScheduleExpression().second(segundo).minute(minuto).hour(hora), timerConfig);
	}

	@Timeout
	public void timeout(Timer timer) throws Exception {

	}

	private void stopTimers() {
		if (timerService.getTimers() != null)
			for (Object obj : timerService.getTimers())
				((Timer) obj).cancel();
	}
}