package com.mojtaba_shafaei.android;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Checkable;
import android.widget.ExpandableListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView.OnTagClickListener;
import com.google.android.material.button.MaterialButton;
import com.jakewharton.rxrelay2.PublishRelay;
import com.mojtaba_shafaei.android.androidBottomDialog.BottomDialog;
import com.mojtaba_shafaei.android.androidExpandableLovMultiSelect.R;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LovExpandableMultiSelect extends AppCompatDialogFragment {

  private static final String TAG = "LovExpandableMultiSelect";

  private ContentLoadingProgressBar progressBar;
  private AppCompatEditText searchView;
  private AppCompatImageButton btnClearSearch;
  private AppCompatImageButton btnBack;
  private MaterialButton btnOk;
  private ExpandableListView listView;
  private TagContainerLayout tagView;
  private AppCompatTextView tvMessage;


  private ListAdapter listAdapter;

  private TextWatcher textWatcher;

  private final CompositeDisposable disposable = new CompositeDisposable();
  private final Locale FA_LOCALE = new Locale("fa");

  static Typeface sDefaultTypeface = null;
  private Property sProperties;
  private List<ItemModel> sItemModelList;
  private List<Item> sDefaultItems = new ArrayList<>();

  private final String SPACE = String.valueOf(' ');

  private Observable<Lce<List<ItemModel>>> lceObservable;

  ////////////////////////////    /////////////////////////////////////
  private OnResultListener mOnResultListener;
  private Dialog.OnCancelListener mOnCancelListener;
  private Dialog.OnDismissListener mOnDismissListener;

  ////////////////////////////    /////////////////////////////////////
  public interface ItemModel {

    int getCod();

    void setCod(int cod);

    int getPriority();

    void setPriority(int priority);

    String getDes();

    void setDes(CharSequence des);

    List<Item> getChildren();

    void setChildren(List<Item> children);

    void setDeleted(boolean deleted);

    boolean isDeleted();
  }

  public interface Item extends Checkable {

    void setCod(int cod);

    int getCod();

    int getPriority();

    void setPriority(int priority);

    String getDes();

    void setDes(CharSequence des);

    void setDeleted(boolean deleted);

    boolean isDeleted();
  }

  public static LovExpandableMultiSelect start(FragmentManager fragmentManager,
      Typeface typeface,
      Property uiParams,
      List<ItemModel> itemModelList,
      List<Item> selectedItems,
      OnResultListener onResultListener,
      Dialog.OnCancelListener onCancelListener,
      Dialog.OnDismissListener onDismissListener) {

    LovExpandableMultiSelect dialog = new LovExpandableMultiSelect();
    dialog.sDefaultTypeface = typeface;
    dialog.sProperties = uiParams;
    dialog.sItemModelList = itemModelList;
    dialog.sDefaultItems = selectedItems;
    dialog.mOnCancelListener = onCancelListener;
    dialog.mOnDismissListener = onDismissListener;
    dialog.mOnResultListener = onResultListener;

    dialog.show(fragmentManager, TAG);
    return dialog;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NO_TITLE,
        R.style.ThemeOverlay_AppCompat_Light);
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup ignored,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.lov_multi_select_layout1, null);
    initUi(view);

    if (sDefaultTypeface != null) {
      searchView.setTypeface(sDefaultTypeface);
      btnOk.setTypeface(sDefaultTypeface);
      tagView.setTagTypeface(sDefaultTypeface);
    }

    if (sProperties != null) {
      if (sProperties.getButtonOkBackgroundTint() != null) {
        btnOk.setBackgroundTintMode(Mode.MULTIPLY);
        btnOk.setBackgroundTintList(sProperties.getButtonOkBackgroundTint());
      }

      if (sProperties.getButtonOkTextColorState() != null) {
        btnOk.setTextColor(sProperties.getButtonOkTextColorState());
      }

      if (sProperties.getTagBackgroundColor() != null) {
        tagView.setTagBackgroundColor(ContextCompat.getColor(getContext(),
            sProperties.getTagBackgroundColor()));
      }

      if (sProperties.getTagBorderColor() != null) {
        tagView.setTagBorderColor(ContextCompat.getColor(getContext(),
            sProperties.getTagBorderColor()));
      }
    }

    btnBack.setOnClickListener((ignored1) -> onBackPressed());

    btnClearSearch.setOnClickListener((ignored2) -> searchView.setText(""));

    lceObservable = Observable.just(Lce.data(sItemModelList));

    btnOk.setOnClickListener((ignored3) -> {
      disposable.add(
          lceObservable
              .subscribeOn(Schedulers.io())
              .zipWith(Observable.just(tagView.getTags())
                      .subscribeOn(AndroidSchedulers.mainThread())
                      .observeOn(Schedulers.computation()),
                  (listLce, strings) -> {
                    if (listLce.hasError() || listLce.getData() == null) {
                      return Lce.<Content>error(
                          new NullPointerException("can not get list of source data"));
                    } else {
                      return Lce.data(new Content(listLce.getData(), strings));
                    }
                  })
              .observeOn(Schedulers.computation())
              .switchMap(lce -> {
                if (lce.hasError() || lce.getData() == null) {
                  return Observable.<Lce<List<Item>>>just(Lce.error(lce.getError()));
                } else {
                  if (lce.getData().getSelectedTags() != null) {
                    final List<ItemModel> itemModels = lce.getData().getDataSet();
                    Set<Item> result = new LinkedHashSet<>();
                    for (String tagDes : lce.getData().getSelectedTags()) {
                      for (ItemModel itemModel : itemModels) {
                        for (Item item : itemModel.getChildren()) {
                          if (item.getDes().contentEquals(tagDes)) {
                            result.add(item);
                            break;
                          }
                        }
                      }
                    }

                    return Observable.just(Lce.data(result));
                  } else {
                    return Observable.<Lce<List<Item>>>just(Lce.error(new Exception("TET")));
                  }
                }
              })
              .observeOn(AndroidSchedulers.mainThread())
              .doOnComplete(disposable::clear)
              .doOnError(this::showError)
              .subscribe(lce -> {
                if (lce.hasError()) {
                  showError(lce.getError());
                } else {
                  onResult(new ArrayList<>(lce.getData()));
                }
              })
      );
    });

    tagView.setOnTagClickListener(new OnTagClickListener() {
      @Override
      public void onTagClick(int position, String text) {

      }

      @Override
      public void onTagLongClick(int position, String text) {
      }

      @Override
      public void onTagCrossClick(int position) {
        removeTagFromSelectedList(tagView.getTags().get(position));
        listAdapter.refreshAdapter();
        refreshSelectedCounter();
      }
    });

    listAdapter = new ListAdapter(getContext()
        , () -> tagView.getTags()
        , (ignored5, item, isChecked) -> {
      if (isChecked) {
        addTag(item.getDes());
      } else {
        removeTagFromSelectedList(item.getDes());
      }
    });

    listView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    listView.setAdapter(listAdapter);

    try {
      tagView.removeAllTags();
      if (sDefaultItems != null) {
        for (Item item : sDefaultItems) {
          addTag(item.getDes());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    refreshSelectedCounter();
    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    PublishRelay<String> subject = PublishRelay.create();
    textWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        subject.accept(charSequence.toString());
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    };

    searchView.addTextChangedListener(textWatcher);

    disposable.add(
        subject.subscribeOn(Schedulers.io())
            .startWith(getQueryText())
            .observeOn(AndroidSchedulers.mainThread())
            .map(query -> {
              if (query.isEmpty()) {
                btnClearSearch.setVisibility(View.GONE);
              } else {
                btnClearSearch.setVisibility(View.VISIBLE);
              }
              return query;
            })
            .debounce(
                getResources().getInteger(R.integer.lov_multi_select_config_debounce_duration),
                TimeUnit.MILLISECONDS)
            .throttleWithTimeout(
                getResources().getInteger(R.integer.lov_multi_select_config_throttle_duration),
                TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(Schedulers.computation())
            .switchMap(query ->
                Observable.just(query.toLowerCase())
                    .zipWith(Observable.just(Lce.data(sItemModelList)),
                        (BiFunction<String, Lce<List<ItemModel>>, Lce<ContentDataSetAndQueryText>>) (query1, listLce) -> {
                          if (listLce.hasError()) {
                            return Lce.error(listLce.getError());
                          } else {
                            return Lce.data(new ContentDataSetAndQueryText(listLce.getData()
                                , query1)
                            );
                          }
                        })
                    .startWith(Lce.<ContentDataSetAndQueryText>loading()))
            .switchMap(lce -> {
              if (!lce.hasError() && !lce.isLoading()) {
                final List<ItemModel> itemModelList = lce.getData().getList();

                if (lce.getData().getQuery().length() == 0) {
                  for (ItemModel itemModel : itemModelList) {
                    itemModel.setDeleted(false);
                    for (Item item : itemModel.getChildren()) {
                      item.setDeleted(false);
                    }
                  }
                } else {
                  final String fQuery = lce.getData().getQuery().replaceAll("\\s+", " ");
                  String[] queries = fQuery.split(SPACE);
                  for (int i = queries.length - 1; i >= 0; i--) {
                    if (queries[i].length() <= 1) {
                      queries = removeElementAt(queries, i);
                    }
                  }

                  for (ItemModel itemModel : itemModelList) {
                    int priority = getPriorityOf(itemModel.getDes(), fQuery, queries);
                    if (priority == Integer.MAX_VALUE) {
                      List<Item> children = itemModel.getChildren();
                      for (Item item : children) {
                        int itemPriority = getPriorityOf(item.getDes(), fQuery, queries);
                        if (itemPriority == Integer.MAX_VALUE) {
                          item.setDeleted(true);
                        } else {
                          item.setDeleted(false);
                          item.setPriority(priority);
                        }
                      }

                      int _count = 0;
                      for (Item item : itemModel.getChildren()) {
                        if (!item.isDeleted()) {
                          _count++;
                        }
                      }
                      if (_count == 0) {
                        itemModel.setDeleted(true);
                      } else {
                        itemModel.setDeleted(false);
                      }
                    } else {
                      itemModel.setDeleted(false);
                      itemModel.setPriority(priority);
                    }
                  }
                }

                return Observable.just(Lce.data(
                    new ContentDataSetAndQueryText(itemModelList, lce.getData().getQuery())));
              } else {
                return Observable.just(lce);
              }
            })
//            .onErrorResumeNext(
//                Observable.just(Lce.data(new ContentDataSetAndQueryText(new ArrayList<>(), ""))))
            .toFlowable(BackpressureStrategy.BUFFER)
            .toObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<Lce<ContentDataSetAndQueryText>>() {
              @Override
              public void onNext(Lce<ContentDataSetAndQueryText> lce) {
                if (isDisposed()) {
                  return;
                }
                if (lce.isLoading()) {
                  hideErrors();
                  showContentLoading(true);

                } else if (lce.hasError()) {
                  showContentLoading(false);
                  showInternetError();

                } else {
                  hideErrors();
                  showContentLoading(false);
                  if (lce.getData() != null) {
                    listAdapter.setData(lce.getData().getList());
                    observeAdapter();
                  } else {
                    showInternetError();
                  }
                }
              }

              @Override
              public void onError(Throwable e) {
                if (isDisposed()) {
                  return;
                }
                setFormStatus(ViewStatus.READY);
                showError(e);
                Log.e(TAG, "onError: ", e);
              }

              @Override
              public void onComplete() {

              }
            })
    );
  }

  private String[] removeElementAt(String[] source, int index) {
    String[] result = new String[source.length - 1];
    int ii = 0;
    for (int i = 0; i < source.length; i++) {
      if (i != index) {
        result[ii] = source[i];
      }
      ii++;
    }
    return result;
  }

  private int getPriorityOf(String des, String fQuery, String[] queries) {
    int priority = Integer.MAX_VALUE;
    for (String k : queries) {
      if (k.length() > 1) {
        if (des.toLowerCase().contains(k.toLowerCase())) {
          priority--;
        }
      }
    }

    if (des.contentEquals(fQuery)) {
      priority--;
    }

    if (des.toLowerCase().startsWith(fQuery)) {
      priority--;
    }
    return priority;
  }

  private void initUi(View root) {
    //<editor-fold desc="Ui Binding">
    progressBar = root.findViewById(R.id.progressBar);
    searchView = root.findViewById(R.id.search_view);
    btnClearSearch = root.findViewById(R.id.lov_multi_select_btn_clear_search);
    btnBack = root.findViewById(R.id.lov_multi_select_btn_back);
    btnOk = root.findViewById(R.id.lov_multi_select_btn_ok);
    listView = root.findViewById(R.id.list);
    tagView = root.findViewById(R.id.tag_group);
    tvMessage = root.findViewById(R.id.tv_message);
    //</editor-fold>
    btnClearSearch.setVisibility(View.GONE);
    ViewCompat.setNestedScrollingEnabled(tagView, false);

    if (getContext() != null) {
      btnClearSearch.setImageDrawable(
          ContextCompat
              .getDrawable(getContext(), R.drawable.lov_multi_select_ic_clear_light_theme));
      btnBack.setImageDrawable(
          ContextCompat.getDrawable(getContext(), R.drawable.lov_multi_select_ic_back_dark));
    }
    ViewCompat.setLayoutDirection(root, ViewCompat.LAYOUT_DIRECTION_RTL);

    searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH | EditorInfo.IME_FLAG_NO_EXTRACT_UI);

    btnBack.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            if (btnBack.getViewTreeObserver().isAlive()) {
              btnBack.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              ObjectAnimator animator = ObjectAnimator.ofFloat(btnBack, "translationX", 0);
              animator.setInterpolator(new DecelerateInterpolator(1.5F));
              animator.setStartDelay(500);
              animator.setDuration(300);
              animator.start();
            }
          }
        });
  }

  @Override
  public void onResume() {
    super.onResume();
    if (getView() != null) {
      getView().setFocusableInTouchMode(true);
      getView().requestFocus();
      getView().setOnKeyListener((v, keyCode, event) -> {
        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
          onBackPressed();
          return true;
        }

        return false;
      });
    }
  }

  private void observeAdapter() {
    refreshSelectedCounter();

    if (listAdapter.getGroupCount() == 0) {
      showError(new Exception("مقداری یافت نشد"));
    } else {
      hideErrors();
    }
  }

  private void removeTagFromSelectedList(String tag) {
    List<String> tags = tagView.getTags();
    if (tags != null) {
      final int len = tags.size();
      for (int i = 0; i < len; i++) {
        if (tags.get(i).contentEquals(tag)) {
          tags.remove(i);
          break;
        }
      }
      tagView.setTags(tags);
    }
    observeAdapter();
  }

  private void hideErrors() {
    tvMessage.setVisibility(View.GONE);
  }

  private void showInternetError() {
    Log.e(TAG, "showInternetError: ");
  }

  private void showContentLoading(boolean b) {
    if (b) {
      progressBar.show();
    } else {
      progressBar.hide();
    }
  }

  private String getQueryText() {
    if (searchView.getText() != null) {
      return searchView.getText().toString();
    } else {
      return "";
    }
  }

  private void addTag(String des) {
    tagView.addTag(des, 0);
    refreshSelectedCounter();
  }

  private void refreshSelectedCounter() {
    int count = tagView.getTags() != null ? tagView.getTags().size() : 0;
    try {
      if (sProperties.getMinLimit() != -1 && count < sProperties.getMinLimit()) {
        btnOk.setText(
            String.format(getString(R.string.lov_multi_select_choose_at_least_one),
                sProperties.getMinLimit())
        );
        btnOk.setEnabled(false);

      } else {
        if (sProperties.getMaxLimit() != -1 && count > sProperties.getMaxLimit()) {
          btnOk.setText(
              String.format(getString(R.string.lov_multi_select_choose_at_most),
                  sProperties.getMaxLimit())
          );
          btnOk.setEnabled(false);
        } else {
          btnOk.setText(String
              .format(FA_LOCALE,
                  getString(R.string.lov_multi_select_btn_ok_text),
                  (sProperties != null && sProperties.getBtnOkText() != null) ? sProperties
                      .getBtnOkText()
                      : getString(R.string.lov_multi_select_choose_it),
                  count)
          );
          btnOk.setEnabled(true);
        }
      }
    } catch (Exception e) {
      Log.e(TAG, "refreshSelectedCounter: ", e);
    }
  }

  private void showError(Throwable e) {
    try {
      tvMessage.setVisibility(View.VISIBLE);
      if (sDefaultTypeface != null) {
        tvMessage.setTypeface(sDefaultTypeface);
      }
      tvMessage.setText(e.getMessage());
      if (getContext() != null) {
        tvMessage.setTextColor(
            ContextCompat.getColor(getContext(), R.color.lov_multi_select_primaryTextDark));
      }

    } catch (Exception error) {
      Log.e(TAG, "showErrors: " + error.getMessage(), error);
    }
  }

  public void setFormStatus(int status) {
    switch (status) {
      case ViewStatus.READY: {
        showContentLoading(false);
      }
      break;

      case ViewStatus.LOADING: {
        showContentLoading(true);
      }
      break;
    }
  }

  @Override
  public void onPause() {
    hideSoftKeyboard(searchView);
    disposable.clear();

    super.onPause();
    dismissAllowingStateLoss();
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    hideSoftKeyboard(searchView);
    if (mOnDismissListener != null) {
      mOnDismissListener.onDismiss(dialog);
    }
    super.onDismiss(dialog);
  }

  @Override
  public void onCancel(DialogInterface dialog) {
    hideSoftKeyboard(searchView);
    if (mOnCancelListener != null) {
      mOnCancelListener.onCancel(dialog);
    }
    super.onCancel(dialog);
  }

  private void onResult(List<Item> data) {
    hideSoftKeyboard(searchView);
    if (mOnResultListener != null) {
      mOnResultListener.onResult(data);
    }
    dismissAllowingStateLoss();
  }

  @Override
  public void onDestroy() {
    if (textWatcher != null) {
      searchView.removeTextChangedListener(textWatcher);
      textWatcher = null;
    }
    sDefaultTypeface = null;
    sItemModelList = null;
    sDefaultItems = null;
    sProperties = null;
    super.onDestroy();
  }

  private void hideSoftKeyboard(AppCompatEditText searchView) {
    try {
      if (searchView != null) {
        InputMethodManager inputManager = (InputMethodManager)
            searchView.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (inputManager != null) {
          inputManager.hideSoftInputFromInputMethod(searchView.getWindowToken(), 0);
        }
        if (inputManager != null) {
          inputManager.hideSoftInputFromWindow(searchView.getApplicationWindowToken(), 0);
        }

        if (getDialog() != null && getDialog().getWindow() != null) {
          getDialog().getWindow().setSoftInputMode(
              WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

        searchView.clearFocus();
        searchView.setSelected(false);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void onBackPressed() {
    //dismiss all changes
    BottomDialog.builder()
        .withCancelable(true)
        .withHiddenHeader(true)
        .withContent(getContentString())
        .withPositiveText(R.string.lov_multi_select_yes)
        .withNegativeText(R.string.lov_multi_select_no)
        .withDirection(BottomDialog.RTL)
        .withNegativeBackground(R.drawable.bottom_dialog_button_background_transparent)
        .withDefaultTypeface(sDefaultTypeface)
        .withOnNegative(bottomDialog -> {
          onCancel(LovExpandableMultiSelect.this.getDialog());
          hideSoftKeyboard(searchView);

          btnBack.getViewTreeObserver()
              .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                  if (btnBack.getViewTreeObserver().isAlive()) {
                    btnBack.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    float value =
                        ((View) btnBack.getParent()).getRight() - btnBack.getRight() + btnBack
                            .getWidth();
                    ObjectAnimator animator = ObjectAnimator
                        .ofFloat(btnBack, "translationX", value);
                    animator.setInterpolator(new DecelerateInterpolator(.8F));
                    animator.setDuration(300);
                    animator.addListener(new AnimatorListenerAdapter() {
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        dismiss();
                      }
                    });
                    animator.start();
                  }
                }
              });
        })
        .build()
        .show(this);
  }

  private String getContentString() {
    StringBuilder content = new StringBuilder();

    content.append("با خروج از این صفحه، دوباره باید موارد مورد نظرتان را انتخاب نمایید.")
        .append("\n\n")
        .append("در این صفحه ادامه میدهید؟");

    return content.toString();
  }

  public interface OnResultListener {

    void onResult(List<Item> items);
  }

  interface FetchDataListener {

    List<Item> fetch();
  }
}
