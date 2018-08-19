package com.mojtaba_shafaei.android.app;

import com.mojtaba_shafaei.android.LovExpandableMultiSelect;

/**
 * Created by mojtaba on 2/28/18.
 */
public class Job implements LovExpandableMultiSelect.Item {

  boolean checked;
  int cod;
  String des;
  boolean deleted;

  public Job() {
  }

  public Job(int cod, String des) {
    this.cod = cod;
    this.des = des;
    this.deleted = false;
  }

  public Job(int cod, String des, boolean checked) {
    this.cod = cod;
    this.des = des;
    this.checked = checked;
  }

  @Override
  public void setCod(int cod) {
    this.cod = cod;
  }

  @Override
  public int getCod() {
    return cod;
  }

  @Override
  public int getPriority() {
    return 0;
  }

  @Override
  public void setPriority(int priority) {

  }

  @Override
  public String getDes() {
    return des;
  }

  @Override
  public void setDes(CharSequence des) {

  }

  @Override
  public void setChecked(boolean b) {
    checked = b;
  }

  @Override
  public boolean isChecked() {
    return checked;
  }

  @Override
  public void toggle() {
    checked = !checked;
  }

  @Override
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public boolean isDeleted() {
    return deleted;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Job{");
    sb.append("checked=").append(checked);
    sb.append(", cod=").append(cod);
    sb.append(", des='").append(des).append('\'');
    sb.append(", deleted=").append(deleted);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Job)) {
      return false;
    }

    Job job = (Job) o;

    if (checked != job.checked) {
      return false;
    }
    if (cod != job.cod) {
      return false;
    }
    return des.equals(job.des);
  }

  @Override
  public int hashCode() {
    int result = cod;
    result = 31 * result + des.hashCode();
    return result;
  }
}
