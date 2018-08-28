package com.mojtaba_shafaei.android.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JobFetcher {

  public List<JobGroup> fetch() {
    List<JobGroup> jobGroups = new LinkedList<>();

    {
      final JobGroup jobGroup = new JobGroup();
      jobGroup.setCod(1);
      jobGroup.setDes("کارمند و دفتری");
      final ArrayList<Job> jobs = new ArrayList<>();
      jobs.add(new Job(1000, "کارمند آژانس مسافرتی"));
      jobs.add(new Job(1001, "مترجم انگلیسی"));
      jobs.add(new Job(1002, "کارمند بیمه"));
      jobs.add(new Job(1003, "کارمند آشنا به زبان انگلیسی"));
      jobs.add(new Job(1004, "کارمند آشنا به کامپیوتر"));
      jobs.add(new Job(1005, "اپراتور کامپیوتر"));
      jobs.add(new Job(1006, "کارمند اداری"));
      jobs.add(new Job(1007, "تایپیست"));
      jobs.add(new Job(1008, "کارمند فروش"));
      jobs.add(new Job(1009, "انباردار"));
      jobs.add(new Job(1010, "محتواگذار"));
      jobs.add(new Job(1012, "مسئول دفتر"));
      jobs.add(new Job(1013, "کارمند پذیرشگر"));
      jobs.add(new Job(1014, "کارمند رزرویشن"));
      jobs.add(new Job(1015, "آمارگر"));
      jobs.add(new Job(1016, "پرسشگر"));

      jobGroup.setChildren(new ArrayList<>(jobs));
      jobGroups.add(jobGroup);
    }

    {
      final JobGroup jobGroup = new JobGroup();
      jobGroup.setCod(2);
      jobGroup.setDes("آموزشی");
      final ArrayList<Job> jobs = new ArrayList<>();
      jobs.add(new Job(1019, "مربی ورزشی"));
      jobs.add(new Job(1020, "مدرس زبان انگلیسی"));
      jobs.add(new Job(1021, "مدرس موسیقی"));
      jobs.add(new Job(1022, "مربی رانندگی"));
      jobs.add(new Job(1023, "مربی مهد"));
      jobs.add(new Job(1025, "مشاور آموزشی"));
      jobs.add(new Job(1026, "مشاور تحصیلی"));
      jobs.add(new Job(1333, "مدرس آلمانی"));
      jobs.add(new Job(1334, "مدرس عربی"));

      jobGroup.setChildren(new ArrayList<>(jobs));
      jobGroups.add(jobGroup);
    }

    {
      final JobGroup jobGroup = new JobGroup();
      jobGroup.setCod(22);
      jobGroup.setDes("ردیف هیچی 2");
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
