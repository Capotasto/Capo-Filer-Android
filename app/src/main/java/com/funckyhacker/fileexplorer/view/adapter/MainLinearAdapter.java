package com.funckyhacker.fileexplorer.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.funckyhacker.fileexplorer.databinding.ItemMainLinearBinding;
import com.funckyhacker.fileexplorer.event.ClickItemEvent;
import com.funckyhacker.fileexplorer.util.DateUtils;
import java.io.File;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class MainLinearAdapter extends RecyclerView.Adapter<MainLinearAdapter.ViewHolder> {

  private List<File> files;

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemMainLinearBinding linearBinding = ItemMainLinearBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(linearBinding);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(files.get(position));
    holder.binding.getRoot().setOnClickListener(v -> {
      File file = files.get(position);
      EventBus.getDefault().post(new ClickItemEvent(file));
    });
  }

  @Override public int getItemCount() {
    if (files == null) {
      return 0;
    }
    return files.size();
  }

  public void setData(List<File> files) {
    this.files = files;
    notifyDataSetChanged();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemMainLinearBinding binding;
    private File file;

    public ViewHolder(ItemMainLinearBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(File file) {
      this.file = file;
      binding.setFile(file);
      binding.setViewHolder(this);
      binding.executePendingBindings();
    }

    public String getModified() {
      return DateUtils.getItemDate(file.lastModified());
    }
  }
}
