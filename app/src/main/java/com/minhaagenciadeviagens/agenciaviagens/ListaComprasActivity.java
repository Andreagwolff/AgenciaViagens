package com.minhaagenciadeviagens.agenciaviagens;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.github.rubensousa.floatingtoolbar.FloatingToolbarMenuBuilder;
import com.minhaagenciadeviagens.agenciaviagens.Adapter.ListaPacotesAdapter;
import com.minhaagenciadeviagens.agenciaviagens.Commom.Commom;
import com.minhaagenciadeviagens.agenciaviagens.Commom.Constantes;
import com.minhaagenciadeviagens.agenciaviagens.DAO.CompraDAO;
import com.minhaagenciadeviagens.agenciaviagens.DAO.PacoteDAO;
import com.minhaagenciadeviagens.agenciaviagens.Model.Pacote;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

public class ListaComprasActivity extends AppCompatActivity {

    public String TITULO_APPBAR = "Lista de Compras de "+Commom.usuarioLogado.getNome();

    private ListView listaDePacotes;

    private List<Pacote> pacotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);

        setTitle(TITULO_APPBAR);

        listaDePacotes = findViewById(R.id.activity_lista_compras_listview);

        pacotes = new CompraDAO(this).listar();

        configuraLista();
    }

    private void configuraLista() {
        listaDePacotes.setAdapter(new ListaPacotesAdapter(pacotes, this));
        listaDePacotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Pacote pacoteClicado = pacotes.get(posicao);
                vaiParaResumoPacote(pacoteClicado);
            }
        });
    }

    private void vaiParaResumoPacote(Pacote pacoteClicado) {
        Intent intent = new Intent(ListaComprasActivity.this,
                ResumoPacoteActivity.class);
        intent.putExtra(Constantes.CHAVE_PACOTE, pacoteClicado);
        startActivity(intent);
    }
}
