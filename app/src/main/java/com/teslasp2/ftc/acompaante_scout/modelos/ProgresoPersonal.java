package com.teslasp2.ftc.acompaante_scout.modelos;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ProgresoPersonal implements Serializable
{
    private int id;
    private int id_ninio;
    private String nombre_progreso;
    private Date fecha_inicio;
    private String prueba_1;
    private String prueba_2;
    private String prueba_3;
    private Date fecha_final;
    private int entregado;

    static final String serverUrl="www.ieslamarisma.net/proyectos/2020/alejandrolopez";

    public ProgresoPersonal(int id, int id_ninio, String nombre_progreso, Date fecha_inicio, String prueba_1, String prueba_2, String prueba_3, Date fecha_final, int entregado) {
        this.id = id;
        this.id_ninio = id_ninio;
        this.nombre_progreso = nombre_progreso;
        this.fecha_inicio = fecha_inicio;
        this.prueba_1 = prueba_1;
        this.prueba_2 = prueba_2;
        this.prueba_3 = prueba_3;
        this.fecha_final = fecha_final;
        this.entregado = entregado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_ninio() {
        return id_ninio;
    }

    public void setId_ninio(int id_ninio) {
        this.id_ninio = id_ninio;
    }

    public String getNombre_progreso() {
        return nombre_progreso;
    }

    public void setNombre_progreso(String nombre_progreso) {
        this.nombre_progreso = nombre_progreso;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getPrueba_1() {
        return prueba_1;
    }

    public void setPrueba_1(String prueba_1) {
        this.prueba_1 = prueba_1;
    }

    public String getPrueba_2() {
        return prueba_2;
    }

    public void setPrueba_2(String prueba_2) {
        this.prueba_2 = prueba_2;
    }

    public String getPrueba_3() {
        return prueba_3;
    }

    public void setPrueba_3(String prueba_3) {
        this.prueba_3 = prueba_3;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public int isEntregado() {
        return entregado;
    }

    public void setEntregado(int entregado) {
        this.entregado = entregado;
    }

    public String toJSONString()
    {
        JSONObject obj=new JSONObject();
        try
        {
            obj.put("Id",getId());
            obj.put("Id_ninio",getId_ninio());
            obj.put("Nombre_progreso",getNombre_progreso());
            obj.put("fecha_inicio",getFecha_inicio());
            obj.put("prueba_1",getPrueba_1());
            obj.put("prueba_2",getPrueba_2());
            obj.put("prueba_3",getPrueba_3());
            obj.put("fecha_final",getFecha_final());
            obj.put("entregado",entregado);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return obj.toString();
    }

    public static ArrayList<ProgresoPersonal> getProgressArray()
    {
        ArrayList<ProgresoPersonal> listaProgresos = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String respuesta = new ProgresoPersonal.GetALL().execute().get();
            JSONArray jsonTotal = new JSONArray(respuesta);

            for(int i=0;i<jsonTotal.length();i++)
            {
                JSONObject obj = jsonTotal.getJSONObject(i);
                String prueba_1, prueba_2, prueba_3;
                Date fecha_final;

                if(obj.getString("prueba_1")!=null)
                {
                    prueba_1 = obj.getString("prueba_1");
                }
                else
                {
                    prueba_1 = "";
                }

                if(obj.getString("prueba_2")!=null)
                {
                    prueba_2 = obj.getString("prueba_2");
                }
                else
                {
                    prueba_2 = "";
                }

                if(obj.getString("prueba_3")!=null)
                {
                    prueba_3 = obj.getString("prueba_3");
                }
                else
                {
                    prueba_3 = "";
                }

                if(obj.getString("fecha_final")!=null)
                {
                    try
                    {
                        fecha_final = sdf.parse(obj.getString("fecha_final"));
                    }
                    catch (ParseException e)
                    {
                        fecha_final=null;
                        e.printStackTrace();
                    }
                }
                else
                {
                    fecha_final = null;
                }

                listaProgresos.add(new ProgresoPersonal(obj.getInt("Id"),obj.getInt("Id_ninio"),
                        obj.getString("Nombre_progreso"),sdf.parse(obj.getString("fecha_inicio")),
                        prueba_1,prueba_2,prueba_3,fecha_final,obj.getInt("entregado")));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listaProgresos;
    }

    public static ArrayList<ProgresoPersonal> getProgressByUser(int id_user)
    {
        ArrayList<Asistencia> listaAsistencias = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String respuesta = new Asistencia.GetByNinio().execute(id_user).get();
            JSONArray jsonTotal = new JSONArray(respuesta);

            for(int i=0;i<jsonTotal.length();i++)
            {
                JSONObject obj = jsonTotal.getJSONObject(i);

                listaAsistencias.add(new Asistencia(obj.getInt("Id"),obj.getInt("Id_ninio"),
                        obj.getString("Tipo_encuentro"),sdf.parse(obj.getString("Fecha")),
                        obj.getString("Asistio")));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listaAsistencias;
    }

    public static class GetALL extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/progresos_personales").openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                connection.connect();

                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
                StringBuffer total = new StringBuffer();
                String line;

                while ((line = buffer.readLine()) != null) {
                    total.append(line);
                }
                buffer.close();
                input.close();

                return total.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if(connection!=null)
                connection.disconnect();

            return null;
        }
    }

    public static class Get extends AsyncTask<Integer, Void, String>
    {
        @Override
        protected String doInBackground(Integer... integers)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/progreso_personal/"+integers[0]).openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                connection.connect();

                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
                StringBuffer total = new StringBuffer();
                String line;

                while ((line = buffer.readLine()) != null) {
                    total.append(line);
                }
                buffer.close();
                input.close();

                return total.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if(connection!=null)
                connection.disconnect();

            return null;
        }
    }

    public static class GetByNinio extends AsyncTask<Integer, Void, String>
    {
        @Override
        protected String doInBackground(Integer... integers)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/progreso_ninio/"+integers[0]).openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                connection.connect();

                BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
                StringBuffer total = new StringBuffer();
                String line;

                while ((line = buffer.readLine()) != null) {
                    total.append(line);
                }
                buffer.close();
                input.close();

                return total.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if(connection!=null)
                connection.disconnect();

            return null;
        }
    }

    public static class Post extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/progreso_personal?").openConnection();

                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestMethod("POST");

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(strings[0]);

                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                System.err.println(connection.getResponseMessage()+"");
                return connection.getResponseMessage()+"";
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if(connection!=null)
                connection.disconnect();

            return null;
        }
    }

    public static class Put extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/progreso_personal?").openConnection();

                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestMethod("PUT");

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(strings[0]);

                writer.flush();
                writer.close();
                os.close();

                connection.connect();

                System.err.println(connection.getResponseMessage()+"");
                return connection.getResponseMessage()+"";
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if(connection!=null)
                connection.disconnect();

            return null;
        }
    }

    public static class Delete extends AsyncTask<Integer, Void, String>
    {
        @Override
        protected String doInBackground(Integer... integers)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/progreso_personal/"+integers[0]).openConnection();

                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestMethod("DELETE");
                connection.setDoOutput(true);

                connection.connect();

                System.err.println(connection.getResponseMessage()+"");
                return connection.getResponseMessage()+"";


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            if(connection!=null)
                connection.disconnect();

            return null;
        }
    }
}
