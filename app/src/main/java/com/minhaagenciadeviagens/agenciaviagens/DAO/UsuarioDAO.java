package com.minhaagenciadeviagens.agenciaviagens.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.minhaagenciadeviagens.agenciaviagens.Helper.DBHelper;
import com.minhaagenciadeviagens.agenciaviagens.Model.Usuario;

public class UsuarioDAO {

    private com.minhaagenciadeviagens.agenciaviagens.Helper.DBHelper DBHelper;
    private SQLiteDatabase databaseWritable, databaseReadable  ;
    private Context context;

    public UsuarioDAO(Context context){
        DBHelper = new DBHelper(context);
        databaseWritable = DBHelper.getWritableDatabase();
        databaseReadable = DBHelper.getReadableDatabase();
        this.context = context;
    }

    public long inserir (Usuario usuario){
        
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("endereco", usuario.getEndereco());
        values.put("cidade", usuario.getCidade());
        values.put("uf", usuario.getUf());
        values.put("dataNascimento", usuario.getDataNacimento());
        values.put("telefone", usuario.getTelefone());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("isCliente", usuario.getIsCliente());
        
        long idUsuario = databaseWritable.insert("Usuarios", null, values);

        return idUsuario;
    }

    public Usuario listarPorEmailSenha(String email, String senha){

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(-1);

        Cursor cursor = databaseReadable.rawQuery(
                "SELECT idUsuario, nome, endereco, cidade, uf, dataNascimento, telefone, email, senha, isCliente " +
                        "FROM " + "Usuarios " +
                        "WHERE email = '" + email + "' AND senha = '" + senha + "' "
                ,null,null);

        while (cursor.moveToNext()){
            usuario.setIdUsuario(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEndereco(cursor.getString(2));
            usuario.setCidade(cursor.getString(3));
            usuario.setUf(cursor.getString(4));
            usuario.setDataNacimento(cursor.getString(5));
            usuario.setTelefone(cursor.getString(6));
            usuario.setEmail(cursor.getString(7));
            usuario.setSenha(cursor.getString(8));
            usuario.setIsCliente(cursor.getInt(9));
        }

        return usuario;
    }

    public long atualizar(Usuario usuario){

        ContentValues values = new ContentValues();

        values.put("nome", usuario.getNome());
        values.put("endereco", usuario.getEndereco());
        values.put("cidade", usuario.getCidade());
        values.put("uf", usuario.getUf());
        values.put("dataNascimento", usuario.getDataNacimento());
        values.put("telefone", usuario.getTelefone());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("isCliente", 1);

        long idUsuario = databaseWritable.update("Usuarios",
                values,
                "idUsuario = ?",
                new String[]{String.valueOf(usuario.getIdUsuario())});

        return idUsuario;

    }
}
