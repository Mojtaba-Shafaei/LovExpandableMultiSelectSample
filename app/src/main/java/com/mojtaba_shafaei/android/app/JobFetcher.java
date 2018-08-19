package com.mojtaba_shafaei.android.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JobFetcher {

  public List<JobGroup> fetch() {
    List<JobGroup> jobGroups = new LinkedList<>();

    {
      final JobGroup jobGroup = new JobGroup();
      jobGroup.setCod(1003);
      jobGroup.setDes("کارمند آشنا به زبان انگلیسی");
      final ArrayList<Job> jobs = new ArrayList<>();
      jobs.add(new Job(1, "یک"));
      jobs.add(new Job(2, "دو"));
      jobs.add(new Job(3, "سه"));
      jobs.add(new Job(4, "چهارمین آیتم از لیست موجود این است"));
      jobs.add(new Job(5, "پنجمین آیتم از لیست موجود این میباشد"));
      jobs.add(new Job(6, "شش"));
      jobs.add(new Job(7, "SEVEN"));
      jobs.add(new Job(8, "EIGHT"));
      jobs.add(new Job(9, "NINE"));
      jobs.add(new Job(10, "TEN"));
      jobs.add(new Job(11, "ELEVEN"));
      jobs.add(new Job(12, "TWELVE"));
      jobs.add(new Job(13, "THIRTEEN"));
      jobs.add(new Job(14, "FOURTEEN"));
      jobs.add(new Job(15, "FIFTEEN"));
      jobs.add(
          new Job(16, "شانزدهمین آیتم از لیست که این یک متن ساختگی است برای اینکه متن تست شود"));

      jobGroup.setChildren(new ArrayList<>(jobs));
      jobGroups.add(jobGroup);
    }

    {
      final JobGroup jobGroup = new JobGroup();
      jobGroup.setCod(2);
      jobGroup.setDes("ردیف 2");
      final ArrayList<Job> jobs = new ArrayList<>();
      jobs.add(new Job(1, "یک"));
      jobs.add(new Job(2, "دو"));
      jobs.add(new Job(3, "سه"));
      jobs.add(new Job(4, "چهارمین آیتم از لیست موجود این است"));
      jobs.add(new Job(5, "پنجمین آیتم از لیست موجود این میباشد"));
      jobs.add(new Job(6, "شش"));
      jobs.add(new Job(7, "SEVEN"));
      jobs.add(new Job(8, "EIGHT"));
      jobs.add(new Job(9, "NINE"));
      jobs.add(new Job(10, "TEN"));
      jobs.add(new Job(11, "ELEVEN"));
      jobs.add(new Job(12, "TWELVE"));
      jobs.add(new Job(13, "THIRTEEN"));
      jobs.add(new Job(14, "FOURTEEN"));
      jobs.add(new Job(15, "FIFTEEN"));
      jobs.add(
          new Job(16, "شانزدهمین آیتم از لیست که این یک متن ساختگی است برای اینکه متن تست شود"));

      jobGroup.setChildren(new ArrayList<>(jobs));
      jobGroups.add(jobGroup);
    }

    return jobGroups;
  }
}
