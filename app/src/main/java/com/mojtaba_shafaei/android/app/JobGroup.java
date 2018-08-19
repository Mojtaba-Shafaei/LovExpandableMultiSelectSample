package com.mojtaba_shafaei.android.app;

import com.mojtaba_shafaei.android.LovExpandableMultiSelect.Item;
import com.mojtaba_shafaei.android.LovExpandableMultiSelect.ItemModel;
import java.util.List;

public class JobGroup implements ItemModel {

  int cod;
  String des;
  int priority;
  List<Item> jobs;
  boolean deleted;

  @Override
  public int getCod() {
    return cod;
  }

  @Override
  public void setCod(int cod) {
    this.cod = cod;
  }

  @Override
  public int getPriority() {
    return priority;
  }

  @Override
  public void setPriority(int priority) {
    this.priority = priority;
  }

  @Override
  public String getDes() {
    return des;
  }

  @Override
  public void setDes(CharSequence des) {
    this.des = des.toString();
  }

  @Override
  public List<Item> getChildren() {
    return jobs;
  }

  @Override
  public void setChildren(List<Item> children) {
    jobs = children;
  }

  @Override
  public boolean isDeleted() {
    return deleted;
  }

  @Override
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
