package com.easy.argonify.Main;

import static com.easy.argonify.Utility.PasswordDBHelper.COLUMN_NAME;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easy.argonify.Main.Dialogs.TupleSelection;
import com.easy.argonify.R;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private final TupleSelection dgSelection;
    private final Context context;
    private Cursor cursor;

    public PasswordAdapter(TupleSelection dialog, Context pContext, Cursor pCursor) {
        dgSelection = dialog;
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
            selector = itemView.findViewById(R.id.bt_tupleClickListener);
        }
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new PasswordViewHolder(
                inflater.inflate(R.layout.tuple_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordAdapter.PasswordViewHolder holder, int position) {
        if (!cursor.moveToPosition(position))
            return;

        String passwordName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        holder.passName.setText(passwordName);
        holder.selector.setOnClickListener((isChecked) -> {
            cursor.moveToFirst();
            dgSelection.onTupleSelection(holder.getAdapterPosition(), cursor);
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
