package com.mojtaba_shafaei.android;

import com.mojtaba_shafaei.android.LovExpandableMultiSelect.ItemModel;
import java.util.List;

class Content {

  private List<ItemModel> dataSet;
  private List<String> selectedTags;

  public Content(List<ItemModel> dataSet, List<String> selectedTags) {
    this.dataSet = dataSet;
    this.selectedTags = selectedTags;
  }

  public List<ItemModel> getDataSet() {
    return dataSet;
  }

  public List<String> getSelectedTags() {
    return selectedTags;
  }
}
