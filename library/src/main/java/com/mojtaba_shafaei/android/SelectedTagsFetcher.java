package com.mojtaba_shafaei.android;

import com.mojtaba_shafaei.android.LovExpandableMultiSelect.Item;
import java.util.List;

public interface SelectedTagsFetcher {

  List<Item> fetch();
}
