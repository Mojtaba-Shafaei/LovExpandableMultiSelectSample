package com.mojtaba_shafaei.android;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.mojtaba_shafaei.android.LovMultiSelect.Item;
import com.mojtaba_shafaei.android.library.androidLovMultiSelect.R;
import java.util.LinkedList;
import java.util.List;

class ListAdapter extends RecyclerView.Adapter<ListHolder> {

  private final List<Item> data = new LinkedList<>();
  private final LayoutInflater inflater;
  private final OnCheckedChangeListener onCheckedChangeListener;
  @Nullable
  private final SelectedTagsFetcher selectedTagsFetcher;

  public ListAdapter(Context context
      , @Nullable SelectedTagsFetcher selectedTagsFetcher
      , OnCheckedChangeListener onCheckedChangeListener) {
    inflater = LayoutInflater.from(context);
    this.onCheckedChangeListener = onCheckedChangeListener;
    this.selectedTagsFetcher = selectedTagsFetcher;
  }

  @Override
  public long getItemId(int position) {
    return data.get(position).hashCode();
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.lov_multi_select_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(ListHolder holder, int _position) {
    final int position = holder.getAdapterPosition();
    final Item item = data.get(position);

    holder.des.setText(item.getDes());
    ////////////////////////////////
    boolean checked = false;
    if (selectedTagsFetcher != null) {
      for (String tag : selectedTagsFetcher.fetch()) {
        if (tag.contentEquals(item.getDes())) {
          checked = true;
          break;
        }
      }
    }
    holder.des.setChecked(checked);
    //////////////////////////////////
    holder.des.setOnClickListener(view -> {
      holder.des.toggle();
      item.setChecked(holder.des.isChecked());

      onCheckedChangeListener.onCheckedChanged(view, item, holder.des.isChecked());
    });

    if (LovMultiSelect.sDefaultTypeface != null) {
      holder.des.setTypeface(LovMultiSelect.sDefaultTypeface);
    }
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  public void setData(List<Item> data) {
    this.data.clear();
    this.data.addAll(data);
    notifyDataSetChanged();
  }

  public List<Item> getSelectedItems() {
    List<Item> items = new LinkedList<>();
    for (Item i : data) {
      if (i.isChecked()) {
        items.add(i);
      }
    }
    return items;
  }

  public void refreshAdapter() {
    notifyDataSetChanged();
  }

  public void chooseItem(Item item) {
    int position = -1;
    int len = data.size();
    for (int i = 0; i < len; i++) {
      if (data.get(i).getDes().equals(item.getDes())) {
        position = i;
        break;
      }
    }

    item.setChecked(true);
    data.set(position, item);
    notifyDataSetChanged();
  }

  public void chooseItems(List<? extends Item> selectedItems) {
    if (selectedItems == null || selectedItems.size() == 0) {
      return;
    }

    int len = data.size();
    for (int i = 0; i < len; i++) {
      Item item0 = data.get(i);
      item0.setChecked(false);
      data.set(i, item0);
    }

    for (int i = 0; i < len; i++) {
      Item item0 = data.get(i);

      for (Item item : selectedItems) {
        if (item0.getDes().equals(item.getDes())) {
          item0.setChecked(true);
          data.set(i, item0);
        }
      }
    }
    notifyDataSetChanged();
  }
}