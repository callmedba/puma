package com.dianping.puma.syncserver.remote.reporter;

import com.dianping.puma.core.monitor.SwallowEventPublisher;
import com.dianping.puma.core.monitor.event.SyncTaskStateEvent;
import com.dianping.puma.core.service.SyncTaskStateService;
import com.dianping.puma.syncserver.config.SyncServerConfig;
import com.dianping.swallow.common.producer.exceptions.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("syncTaskStateReporter")
public class SyncTaskStateReporter {

	@Autowired
	SwallowEventPublisher syncTaskStatePublisher;

	@Autowired
	SyncTaskStateService syncTaskStateService;

	@Autowired
	SyncServerConfig syncServerConfig;

	@Scheduled(cron = "0/5 * * * * ?")
	public void report() throws SendFailedException {
		SyncTaskStateEvent event = new SyncTaskStateEvent();
		event.setServerName(syncServerConfig.getSyncServerName());
		event.setTaskStates(syncTaskStateService.findAll());
		syncTaskStatePublisher.publish(event);
	}
}