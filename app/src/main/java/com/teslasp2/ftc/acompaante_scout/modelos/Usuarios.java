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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Usuarios implements Serializable
{
    private int id;
    private String nombre_user;
    private String contra;
    private int monitor;
    private String nombre;
    private String apellidos;
    private String seccion;
    private String subgrupo;
    private String cargo;
    private String alergenos;
    private static Usuarios usuarioActual;

    static final String serverUrl="https://www.ieslamarisma.net/proyectos/2020/alejandrolopez";

    public Usuarios(int id, String nombre_user, String contra, int monitor, String nombre, String apellidos, String seccion, String subgrupo, String cargo, String alergenos) {
        this.id = id;
        this.nombre_user = nombre_user;
        this.contra = contra;
        this.monitor = monitor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.seccion = seccion;
        this.subgrupo = subgrupo;
        this.cargo = cargo;
        this.alergenos = alergenos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_user() {
        return nombre_user;
    }

    public void setNombre_user(String nombre_user) {
        this.nombre_user = nombre_user;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int isMonitor() {
        return monitor;
    }

    public void setMonitor(int monitor) {
        this.monitor = monitor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(String subgrupo) {
        this.subgrupo = subgrupo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(String alergenos) {
        this.alergenos = alergenos;
    }

    public static int getLastID()
    {
        ArrayList<Usuarios> lista = getUsersArray();
        int ultimaPos = lista.size()-1;
        return lista.get(ultimaPos).getId();
    }

    public String toString()
    {
        return "Nombre: "+this.nombre+"\nApellidos: ";
    }

    public String toJSONString()
    {

        JSONObject obj=new JSONObject();
        try
        {
            obj.put("id",getId());
            obj.put("nombre_user",getNombre_user());
            obj.put("contra",getContra());
            obj.put("monitor",isMonitor());
            obj.put("nombre",getNombre());
            obj.put("apellidos",getApellidos());

            if(getSeccion()==null)
                obj.put("seccion","");
            else
                obj.put("seccion",getSeccion());

            if(getSubgrupo()==null)
                obj.put("subgrupo","");
            else
                obj.put("subgrupo",getSubgrupo());

            obj.put("cargo",getCargo());

            if(getAlergenos()==null)
                obj.put("alergenos","");
            else
                obj.put("alergenos",getAlergenos());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return obj.toString();
    }

    public static void setCurrentUser(Usuarios usuario)
    {
        usuarioActual = usuario;
    }

    public static Usuarios getCurrentUser()
    {
        return usuarioActual;
    }

    public static Usuarios getUserById(int id)
    {
        Usuarios user = null;
        try {
            String respuesta = new Usuarios.Get().execute(id).get();
            JSONObject obj = new JSONObject(respuesta);

            String seccion, subgrupo, alergenos;

            if(obj.getString("seccion")!=null)
            {
                seccion = obj.getString("seccion");
            }
            else
            {
                seccion = "";
            }

            if(obj.getString("subgrupo")!=null)
            {
                subgrupo = obj.getString("subgrupo");
            }
            else
            {
                subgrupo = "";
            }

            if(obj.getString("alergenos")!=null)
            {
                alergenos = obj.getString("alergenos");
            }
            else
            {
                alergenos = "";
            }

            user =  new Usuarios(obj.getInt("Id"), obj.getString("Nombre_User"),
                    obj.getString("Contra"), obj.getInt("Monitor"), obj.getString("nombre"),
                    obj.getString("apellidos"), seccion,
                    subgrupo, obj.getString("Cargo"), alergenos);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static ArrayList<Usuarios> getUsersArray()
    {
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        try {
            String respuesta = new Usuarios.GetALL().execute().get();
            JSONArray jsonTotal = new JSONArray(respuesta);

            for(int i=0;i<jsonTotal.length();i++)
            {
                JSONObject obj = jsonTotal.getJSONObject(i);
                String seccion, subgrupo, alergenos;

                if(obj.getString("seccion")!=null)
                {
                    seccion = obj.getString("seccion");
                }
                else
                {
                    seccion = "";
                }

                if(obj.getString("subgrupo")!=null)
                {
                    subgrupo = obj.getString("subgrupo");
                }
                else
                {
                    subgrupo = "";
                }

                if(obj.getString("alergenos")!=null)
                {
                    alergenos = obj.getString("alergenos");
                }
                else
                {
                    alergenos = "";
                }

                listaUsuarios.add(new Usuarios(obj.getInt("Id"), obj.getString("Nombre_User"),
                        obj.getString("Contra"), obj.getInt("Monitor"), obj.getString("nombre"),
                        obj.getString("apellidos"), seccion,
                        subgrupo, obj.getString("Cargo"), alergenos));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    public static class GetALL extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/usuarios").openConnection();
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
                connection = (HttpURLConnection) new URL(serverUrl+"/usuario/"+integers[0]).openConnection();
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
                connection = (HttpURLConnection) new URL(serverUrl+"/usuario?").openConnection();

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

    public static class Put extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/usuario?").openConnection();

                connection.setRequestMethod("PUT");
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

    public static class Delete extends AsyncTask<Integer, Void, Integer>
    {
        @Override
        protected Integer doInBackground(Integer... integers)
        {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(serverUrl+"/usuario/"+integers[0]).openConnection();

                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connection.setRequestMethod("DELETE");
                connection.setDoOutput(true);

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                connection.connect();

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
}
