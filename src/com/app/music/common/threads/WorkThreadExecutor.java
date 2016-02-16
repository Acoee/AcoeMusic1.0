package com.app.music.common.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主线程池
 * Created by Administrator on 2015/9/14.
 */
public class WorkThreadExecutor {
    /*** 实例 */
    private static WorkThreadExecutor instance = null;

    /*** 图片下载专用 */
    private ExecutorService executor = null;

    private WorkThreadExecutor() {
    }

    public static WorkThreadExecutor getInstance() {
        if (instance == null) {
            instance = new WorkThreadExecutor();
            PriorityThreadFactory threadFactory = new PriorityThreadFactory(
                    "work-thread",
                    android.os.Process.THREAD_PRIORITY_BACKGROUND);
            instance.executor = Executors.newFixedThreadPool(5, threadFactory);
        }
        return instance;
    }

    /**
     * 提交任务
     *
     * @param task
     */
    public void execute(Runnable task) {
        instance.executor.execute(task);
    }
}
