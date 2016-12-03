package com.framework.runtime.application.process;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class SingleSyncExecutor implements Executor {

	@Override
	public void execute(Runnable task) {
		task.run();
    }
}