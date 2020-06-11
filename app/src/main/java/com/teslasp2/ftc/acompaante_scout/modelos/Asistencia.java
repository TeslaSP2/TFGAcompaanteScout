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

public class Asistencia implements Serializable
{
    private int id;
    private int id_ninio;
    private String tipo_encuentro;
    private Date fecha;
    private String asistio;

    static final String serverUrl="https://www.ieslamarisma.net/proyectos/2020/alejandrolopez";

    public Asistencia(int id, int id_ninio, String tipo_encuentro, Date fecha, String asistio) {
        this.id = id;
        this.id_ninio = id_ninio;
        this.tipo_encuentro = tipo_encuentro;
        this.fecha = fecha;
        this.asistio = asistio;
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

    public String getTipo_encuentro() {
        return tipo_encuentro;
    }

    public void setTipo_encuentro(String tipo_encuentro) {
        this.tipo_encuentro = tipo_encuentro;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getFechaString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAsistio() {
        return asistio;
    }

    public void setAsistio(String asistio) {
        this.asistio = asistio;
    }

    public static int getLastID()
    {
        ArrayList<Asistencia> lista = getAsistsArray();
        int ultimaPos = lista.size()-1;
        return lista.get(ultimaPos).getId();
    }

    public String toJSONString()
    {
        JSONObject obj=new JSONObject();
        try
        {
            obj.put("id",getId());
            obj.put("id_ninio",getId_ninio());
            obj.put("tipo_encuentro",getTipo_encuentro());
            obj.put("fecha",getFechaString());
            obj.put("asistio",getAsistio());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return obj.toString();
    }

    public static ArrayList<Asistencia> getAsistsArray()
    {
        ArrayList<Asistencia> listaAsistencias = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String respuesta = new Asistencia.GetALL().execute().get();
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

    public static ArrayList<Asistencia> getAsistsByUser(int id_user)
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
                connection = (HttpURLConnection) new URL(serverUrl+"/asistencias").openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

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
                connection = (HttpURLConnection) new URL(serverUrl+"/asistencia/"+integers[0]).openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

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
                connection = (HttpURLConnection) new URL(serverUrl+"/asistencia_ninio/"+integers[0]).openConnection();
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

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

    public static class Post extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected Integer doInBackground(String... strings)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/asistencia?").openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                connection.connect();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(strings[0]);

                writer.flush();
                writer.close();
                os.close();

                System.err.println(connection.getResponseMessage()+"");
                return connection.getResponseCode();
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

    public static class Put extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected Integer doInBackground(String... strings)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/asistencia?").openConnection();

                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                connection.connect();

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(strings[0]);

                writer.flush();
                writer.close();
                os.close();

                System.err.println(connection.getResponseMessage()+"");
                return connection.getResponseCode();
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
                connection = (HttpURLConnection) new URL(serverUrl+"/asistencia/"+integers[0]).openConnection();

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
