package com.teslasp2.ftc.acompaante_scout.actividadesDeAsistencia;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.teslasp2.ftc.acompaante_scout.R;

public class DelAsistDialog extends AppCompatDialogFragment {

    DelAsistDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete_asist, null);

        builder.setView(view).setTitle("Borrar asistencia")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.canDelete(false);
                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.canDelete(true);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (DelAsistDialogListener) context;
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface DelAsistDialogListener
    {
        void canDelete(boolean response);
    }
}
