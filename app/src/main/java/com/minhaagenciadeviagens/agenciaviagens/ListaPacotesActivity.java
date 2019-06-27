package com.minhaagenciadeviagens.agenciaviagens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.github.rubensousa.floatingtoolbar.FloatingToolbarMenuBuilder;
import com.minhaagenciadeviagens.agenciaviagens.Adapter.ListaPacotesAdapter;
import com.minhaagenciadeviagens.agenciaviagens.Commom.Constantes;
import com.minhaagenciadeviagens.agenciaviagens.DAO.PacoteDAO;
import com.minhaagenciadeviagens.agenciaviagens.Model.Pacote;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ListaPacotesActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Pacotes";

    private LinearLayout layoutPesquisa;
    private MaterialEditText editPesquisa;
    private ImageView imgFecharPesquisa;
    private FloatingToolbar floatingToolbar;
    private FloatingActionButton fab;
    private ListView listaDePacotes;

    private List<Pacote> pacotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacotes);

        setTitle(TITULO_APPBAR);

        layoutPesquisa = findViewById(R.id.activity_lista_pacotes_layoutPesquisa);
        editPesquisa = findViewById(R.id.activity_lista_pacotes_editPesquisa);
        imgFecharPesquisa = findViewById(R.id.activity_lista_pacotes_imgFecharPesquisa);
        floatingToolbar = findViewById(R.id.activity_lista_pacotes_floatingToolbar);
        fab = findViewById(R.id.activity_lista_pacotes_fab);
        listaDePacotes = findViewById(R.id.activity_lista_pacotes_listview);

        pacotes = new PacoteDAO().lista();

        layoutPesquisa.setVisibility(View.GONE);

        imgFecharPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ListaPacotesActivity.this, ListaPacotesActivity.class);
                startActivity(intent);
            }
        });

        editPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    pacotes = new PacoteDAO().listaFiltro(editable.toString());
                    configuraLista();
                }else{
                    pacotes = new PacoteDAO().lista();
                    configuraLista();
                }
            }
        });

        configuraFab();
        configuraLista();
    }

    private void configuraFab() {

        floatingToolbar.setMenu(new FloatingToolbarMenuBuilder(this)
                .addItem(R.id.fab_pacotes_viagens, R.drawable.ic_business_center_white_24dp, "Viagens")
                .addItem(R.id.fab_pacotes_profile, R.drawable.ic_person_white_24dp, "Profile")
                .addItem(R.id.fab_pacotes_buscar, R.drawable.ic_search_white_24dp, "Buscar")
                .build());

        floatingToolbar.attachFab(fab);

        floatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.fab_pacotes_buscar) {
                    layoutPesquisa.setVisibility(View.VISIBLE);
                    configuraLista();
                }else if(id == R.id.fab_pacotes_profile){
                    Intent intent = new Intent(ListaPacotesActivity.this, UsuarioActivity.class);
                    startActivity(intent);
                }else if(id == R.id.fab_pacotes_viagens){
                    Intent intent = new Intent(ListaPacotesActivity.this, ListaComprasActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(MenuItem item) {}
        });
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
        Intent intent = new Intent(ListaPacotesActivity.this,
                ResumoPacoteActivity.class);
        intent.putExtra(Constantes.CHAVE_PACOTE, pacoteClicado);
        startActivity(intent);
    }
}
