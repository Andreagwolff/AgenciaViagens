package com.minhaagenciadeviagens.agenciaviagens.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.minhaagenciadeviagens.agenciaviagens.Commom.Commom;
import com.minhaagenciadeviagens.agenciaviagens.Helper.DBHelper;
import com.minhaagenciadeviagens.agenciaviagens.Model.Pacote;
import com.minhaagenciadeviagens.agenciaviagens.Model.Usuario;
import com.minhaagenciadeviagens.agenciaviagens.Util.MoedaUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    private com.minhaagenciadeviagens.agenciaviagens.Helper.DBHelper DBHelper;
    private SQLiteDatabase databaseWritable, databaseReadable  ;
    private Context context;

    public CompraDAO(Context context){
        DBHelper = new DBHelper(context);
        databaseWritable = DBHelper.getWritableDatabase();
        databaseReadable = DBHelper.getReadableDatabase();
        this.context = context;
    }

    public long inserir (Pacote pacote){

        ContentValues values = new ContentValues();
        values.put("local", pacote.getLocal());
        values.put("dias", pacote.getDias());
        values.put("preco", pacote.getPreco().toString());
        values.put("imagem", pacote.getImagem());
        values.put("IdUsuario", Commom.usuarioLogado.getIdUsuario());

        long idCompra = databaseWritable.insert("Compras", null, values);

        return idCompra;
    }

    public List<Pacote> listar(){

        List<Pacote> pacotes = new ArrayList<>();

        Cursor cursor = databaseReadable.rawQuery(
                "SELECT idCompra, local, dias, preco, imagem " +
                        "FROM " + "Compras " +
                        "WHERE idUsuario = " + Commom.usuarioLogado.getIdUsuario()
                ,null,null);

        while (cursor.moveToNext()){
            Pacote pacote = new Pacote();
            pacote.setLocal(cursor.getString(1));
            pacote.setDias(cursor.getInt(2));
            pacote.setPreco(new BigDecimal(cursor.getString(3)));
            pacote.setImagem(cursor.getString(4));

            pacotes.add(pacote);
        }

        return pacotes;
    }

}
