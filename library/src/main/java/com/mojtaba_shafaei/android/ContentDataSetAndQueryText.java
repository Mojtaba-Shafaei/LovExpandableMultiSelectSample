package com.mojtaba_shafaei.android;

import com.mojtaba_shafaei.android.LovExpandableMultiSelect.ItemModel;
import java.util.List;

public class ContentDataSetAndQueryText {

  private List<ItemModel> items;
  private String query;

  public ContentDataSetAndQueryText(List<ItemModel> items, String query) {
    this.items = items;
    this.query = query;
  }

  public List<ItemModel> getList() {
    return items;
  }

  public String getQuery() {
    return query;
  }

  public void setItems(List<ItemModel> items) {
    this.items = items;
  }
}
