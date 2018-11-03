package com.mojtaba_shafaei.android;

import android.content.res.ColorStateList;
import android.support.annotation.IntRange;

/**
 * Created by mojtaba on 3/5/18.
 */
public final class Property{

private String btnOkText = "";
private ColorStateList buttonOkTextColorState = null;
private ColorStateList buttonOkBackgroundTint = null;

private ColorStateList chipBackgroundColor = null;
private Integer chipStrokeWidth = null;
private ColorStateList chipStrokeColor = null;
private ColorStateList chipTextColor = null;
private Integer chipTextSize = null;
private ColorStateList closeIconTintColor = null;

private ColorStateList categoryTextColor = null;

@IntRange(from = -1)
private int minLimit;

@IntRange(from = -1)
private int maxLimit;

public Property(){
}

private Property(Builder builder){
  btnOkText = builder.btnOkText;
  buttonOkTextColorState = builder.buttonOkTextColorState;
  buttonOkBackgroundTint = builder.buttonOkBackgroundTint;
  chipBackgroundColor = builder.chipBackgroundColor;
  chipStrokeWidth = builder.chipStrokeWidth;
  chipStrokeColor = builder.chipStrokeColor;
  chipTextColor = builder.chipTextColor;
  chipTextSize = builder.chipTextSize;
  closeIconTintColor = builder.closeIconTintColor;
  categoryTextColor = builder.categoryTextColor;
  minLimit = builder.minLimit;
  maxLimit = builder.maxLimit;
}

public static Builder newBuilder(){
  return new Builder();
}

////////////////////////////    /////////////////////////////////////

public String getBtnOkText(){
  return btnOkText;
}

public ColorStateList getButtonOkTextColorState(){
  return buttonOkTextColorState;
}

public ColorStateList getButtonOkBackgroundTint(){
  return buttonOkBackgroundTint;
}

public ColorStateList getChipBackgroundColor(){
  return chipBackgroundColor;
}

public Integer getChipStrokeWidth(){
  return chipStrokeWidth;
}

public ColorStateList getChipStrokeColor(){
  return chipStrokeColor;
}

public ColorStateList getChipTextColor(){
  return chipTextColor;
}

public Integer getChipTextSize(){
  return chipTextSize;
}

public ColorStateList getCloseIconTintColor(){
  return closeIconTintColor;
}

public int getMinLimit(){
  return minLimit;
}

public int getMaxLimit(){
  return maxLimit;
}

public ColorStateList getCategoryTextColor(){
  return categoryTextColor;
}

public static final class Builder{

  private String btnOkText;
  private ColorStateList buttonOkTextColorState;
  private ColorStateList buttonOkBackgroundTint;
  private ColorStateList chipBackgroundColor;
  private Integer chipStrokeWidth;
  private ColorStateList chipStrokeColor;
  private ColorStateList chipTextColor;
  private Integer chipTextSize;
  private ColorStateList closeIconTintColor;
  private ColorStateList categoryTextColor;
  private int minLimit;
  private int maxLimit;

  private Builder(){
  }

  public Builder withBtnOkText(String btnOkText){
    this.btnOkText = btnOkText;
    return this;
  }

  public Builder withButtonOkTextColorState(ColorStateList buttonOkTextColorState){
    this.buttonOkTextColorState = buttonOkTextColorState;
    return this;
  }

  public Builder withButtonOkBackgroundTint(ColorStateList buttonOkBackgroundTint){
    this.buttonOkBackgroundTint = buttonOkBackgroundTint;
    return this;
  }

  public Builder withChipBackgroundColor(ColorStateList chipBackgroundColor){
    this.chipBackgroundColor = chipBackgroundColor;
    return this;
  }

  public Builder withChipStrokeWidth(Integer chipStrokeWidth){
    this.chipStrokeWidth = chipStrokeWidth;
    return this;
  }

  public Builder withChipStrokeColor(ColorStateList chipStrokeColor){
    this.chipStrokeColor = chipStrokeColor;
    return this;
  }

  public Builder withChipTextColor(ColorStateList chipTextColor){
    this.chipTextColor = chipTextColor;
    return this;
  }

  public Builder withChipTextSize(Integer chipTextSize){
    this.chipTextSize = chipTextSize;
    return this;
  }

  public Builder withCloseIconTintColor(ColorStateList closeIconTintColor){
    this.closeIconTintColor = closeIconTintColor;
    return this;
  }

  public Builder withCategoryTextColor(ColorStateList categoryTextColor){
    this.categoryTextColor = categoryTextColor;
    return this;
  }

  public Builder withMinLimit(int minLimit){
    this.minLimit = minLimit;
    return this;
  }

  public Builder withMaxLimit(int maxLimit){
    this.maxLimit = maxLimit;
    return this;
  }

  public Property build(){
    return new Property(this);
  }
}

////////////////////////////    /////////////////////////////////////


}
