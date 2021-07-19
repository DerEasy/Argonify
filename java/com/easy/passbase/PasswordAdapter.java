package com.easy.passbase;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private final DgSelectEntry dgSelectEntry;
    private final Context context;
    private Cursor cursor;

    public PasswordAdapter(DgSelectEntry dialog, Context pContext, Cursor pCursor) {
        dgSelectEntry = dialog;
        context = pContext;
        cursor = pCursor;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class PasswordViewHolder extends RecyclerView.ViewHolder {
        public TextView passName;
        public Button selector;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);

            passName = itemView.findViewById(R.id.passwordItemName);
            selector = itemView.findViewById(R.id.bt_entryClickListener);
        }
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new PasswordViewHolder(
                inflater.inflate(R.layout.password_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.PasswordViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) return;

        String passwordName = cursor.getString(cursor.getColumnIndex(PasswordDBHelper.COLUMN_NAME));
        holder.passName.setText(passwordName);
        holder.selector.setOnClickListener((isChecked) -> {
            dgSelectEntry.setSelectedEntry(holder.getAdapterPosition(), cursor);
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null)
            cursor.close();

        cursor = newCursor;

        if (newCursor != null)
            notifyDataSetChanged();
    }
}
