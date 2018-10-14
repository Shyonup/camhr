package com.camhr.job.schedule;

import com.camhr.job.entity.PublishJob;
import com.camhr.job.service.JobService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 职位相关的定时任务
 *
 * Created by Allen on 2018/10/10.
 */
@Service
public class JobScheduledTask {

  @Autowired
  private JobService jobService;

  /**
   * 自动把那些 置顶的职位，拉下来。如果过期了话
   */
  @Scheduled(cron = "${modules.job.autoCloseExpiredJobsCron:0 30 0 * * ?}")
  public void autoDownTheTopJobs() {
    jobService.downTheTopJobsWhichIsExpired();
  }

  /**
   * 过期职位自动关闭
   * 每天0晨1点整开始关闭
   *
   * 关闭职位，迟1个小时执行 没关系。因为已过期的状态的职位，在用户端已经看不见了。这里只是改改状态而已
   * 慢慢等 自动续费任务执行完毕 后，再执行关闭过期职位，免得出现多线程问题。
   * 因为自动关闭和自动续费，有可能 操作同一条记录同一个字段
   */
  @Scheduled(cron = "${modules.job.autoCloseExpiredJobsCron:0 0 1 * * ?}")
  public void autoCloseExpiredJobs() {
    jobService.closeExpiredJobs();
  }

  /**
   * 过期职位自动续费（如果用户开启了自动 续费功能）
   * 每天的0晨0点01分开始执行
   */
  @Scheduled(cron = "${modules.job.autoRenewJobsCron:0 1 0 * * ?}")
  public void autoRenewJobs() {
    /*
        Step_1 把所有需要自动续费的job查询出来（需要jobId和employerId，还有上次使用过的服务项）
        Step_2 for循环调用发布职位接口（发布失败的话，展示不做处理。）
     */
    for (PublishJob publishJob : jobService.findJobsWhichNeedToAutoRenew()) {
      try {
        jobService.publishJob(publishJob);
      } catch (Exception e) {
        // 重新发布失败，一般是服务包没有余额了。不做任何处理
      }
    }
  }

}
