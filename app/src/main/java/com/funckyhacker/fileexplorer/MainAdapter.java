package com.funckyhacker.fileexplorer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.funckyhacker.fileexplorer.databinding.ItemMainLinearBinding;
import java.io.File;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

  private List<File> files;

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemMainLinearBinding linearBinding = ItemMainLinearBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(linearBinding);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(files.get(position));
  }

  @Override public int getItemCount() {
    if (files == null) {
      return 0;
    }
    return files.size();
  }

  public void setData(List<File> files) {
    this.files = files;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemMainLinearBinding binding;

    public ViewHolder(ItemMainLinearBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(File file) {
      binding.setName(file.getName());
    }
  }
}