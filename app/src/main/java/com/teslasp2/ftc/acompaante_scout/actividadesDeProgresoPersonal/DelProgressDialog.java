package com.teslasp2.ftc.acompaante_scout.actividadesDeProgresoPersonal;

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

/*
 * Esta clase es una ventana emergente para que salga a la hora de que el usuario intente
 * borrar un progreso personal. En caso de que le de a cancelar o fuera de la ventana no se
 * borrará el progreso personal, en el caso de que le de a aceptar lo borrará.
 */

public class DelProgressDialog extends AppCompatDialogFragment {

    DelProgressDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete_progress, null);

        builder.setView(view).setTitle("Borrar progreso")
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
            listener = (DelProgressDialogListener) context;
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface DelProgressDialogListener
    {
        void canDelete(boolean response);
    }
}
