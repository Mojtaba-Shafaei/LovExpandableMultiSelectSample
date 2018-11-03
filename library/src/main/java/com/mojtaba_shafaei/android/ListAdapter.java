package com.mojtaba_shafaei.android;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.mojtaba_shafaei.android.LovExpandableMultiSelect.Item;
import com.mojtaba_shafaei.android.LovExpandableMultiSelect.ItemModel;
import com.mojtaba_shafaei.android.androidExpandableLovMultiSelect.R;
import java.text.Collator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.collections4.ComparatorUtils;

class ListAdapter extends BaseExpandableListAdapter{

private final List<ItemModel> data = new LinkedList<>();

private final LayoutInflater inflater;
private final OnCheckedChangeListener onCheckedChangeListener;
@Nullable
private final SelectedTagsFetcher selectedTagsFetcher;
private final Collator vCollator = Collator.getInstance(new Locale("fa"));

private ColorStateList categoryTextColor = null;

public ListAdapter(Context context
    , ColorStateList categoryTextColor
    , @Nullable SelectedTagsFetcher selectedTagsFetcher
    , OnCheckedChangeListener onCheckedChangeListener){

  inflater = LayoutInflater.from(context);
  this.onCheckedChangeListener = onCheckedChangeListener;
  this.selectedTagsFetcher = selectedTagsFetcher;
  this.categoryTextColor = categoryTextColor;
}

public void setData(List<ItemModel> data){
  this.data.clear();
  if(data != null){
    this.data.addAll(data);

    Collections.sort(this.data
        , ComparatorUtils.chainedComparator((f1, f2) -> Integer.compare(f1.getPriority(), f2.getPriority())
            , ((o1, o2) -> vCollator.compare(o1.getDes(), o2.getDes()))
        ));
  }
  notifyDataSetChanged();
}

public void refreshAdapter(){
  notifyDataSetChanged();
}

@Override
public int getGroupCount(){
  int _count = 0;
  for(ItemModel itemModel : data){
    if(!itemModel.isDeleted()){
      _count++;
    }
  }
  return _count;
}

@Override
public int getChildrenCount(int i){
  int _count = 0;
  for(Item item : ((ItemModel) getGroup(i)).getChildren()){
    if(!item.isDeleted()){
      _count++;
    }
  }
  return _count;
}

@Override
public Object getGroup(int i){
  if(data.size() == 0){
    return null;
  }

  int _counter = -1;
  for(int realIndex = 0, data1Size = data.size(); realIndex < data1Size; realIndex++){
    if(!data.get(realIndex).isDeleted()){
      _counter++;
    }
    if(_counter == i){
      return data.get(realIndex);
    }
  }
  return null;
}

@Override
public Object getChild(int i, int i1){
  if(data.size() == 0){
    return null;
  }

  final List<Item> children = ((ItemModel) getGroup(i)).getChildren();
  int _counter = -1;
  for(int realIndex = 0, size = children.size(); realIndex < size; realIndex++){
    if(!children.get(realIndex).isDeleted()){
      _counter++;
    }

    if(_counter == i1){
      return children.get(realIndex);
    }
  }
  return null;
}

@Override
public long getGroupId(int i){
  if(data.size() == 0){
    return -1;
  }

  return ((ItemModel) getGroup(i)).getCod();
}

@Override
public long getChildId(int i, int i1){
  if(data.size() == 0){
    return -1;
  }
  Item a = (Item) getChild(i, i1);
  if(a != null){
    return a.getCod();
  }
  return -1;
}

@Override
public boolean hasStableIds(){
  return true;
}

@Override
public int getChildTypeCount(){
  return 1;
}

@Override
public View getGroupView(int position, boolean b, View view, ViewGroup parent){
  view = inflater.inflate(R.layout.lov_multi_select_li_group, parent, false);

  final ItemModel itemModel = (ItemModel) getGroup(position);

  // bind ui's views
  AppCompatTextView des = view.findViewById(R.id.label);

  //
  des.setText(itemModel.getDes());
  ////////////////////////////////

  if(LovExpandableMultiSelect.sCategoryTypeface != null){
    des.setTypeface(LovExpandableMultiSelect.sCategoryTypeface);
  }

  if(categoryTextColor != null){
    des.setTextColor(categoryTextColor);
  }

  return view;
}

@Override
public View getChildView(int i, int i1, boolean b, View view, ViewGroup parent){
  view = inflater.inflate(R.layout.lov_multi_select_list_item, parent, false);

  final Item item = (Item) getChild(i, i1);

  boolean checked = false;
  if(selectedTagsFetcher != null){
    for(Item tag : selectedTagsFetcher.fetch()){
      if(tag.getDes().contentEquals(item.getDes())){
        checked = true;
        break;
      }
    }
  }
  AppCompatCheckedTextView des = view.findViewById(R.id.des);
  if(LovExpandableMultiSelect.sDefaultTypeface != null){
    des.setTypeface(LovExpandableMultiSelect.sDefaultTypeface);
  }

  des.setText(item.getDes());
  des.setChecked(checked);
  //////////////////////////////////
  des.setOnClickListener(view1 -> {
    des.toggle();
    item.setChecked(des.isChecked());
    onCheckedChangeListener.onCheckedChanged(view1, item, des.isChecked());
  });
  return view;
}

@Override
public boolean isChildSelectable(int i, int i1){
  return false;
}
}
