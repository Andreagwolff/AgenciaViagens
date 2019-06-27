package com.minhaagenciadeviagens.agenciaviagens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.minhaagenciadeviagens.agenciaviagens.Commom.Commom;
import com.minhaagenciadeviagens.agenciaviagens.Commom.Constantes;
import com.minhaagenciadeviagens.agenciaviagens.Commom.RegCons;
import com.minhaagenciadeviagens.agenciaviagens.DAO.CompraDAO;
import com.minhaagenciadeviagens.agenciaviagens.Model.Pacote;
import com.minhaagenciadeviagens.agenciaviagens.Util.MakeToastUtil;
import com.minhaagenciadeviagens.agenciaviagens.Util.MoedaUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;


public class PagamentoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Pagamento";

    private MaterialEditText editNumeroCartao, editMesCartao, editAnoCartao, editCvcCartao, editNomeCartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        setTitle(TITULO_APPBAR);

        editNumeroCartao = findViewById(R.id.pagamento_numero_cartao);
        editMesCartao = findViewById(R.id.pagamento_mes_cartao);
        editAnoCartao = findViewById(R.id.pagamento_ano_cartao);
        editCvcCartao = findViewById(R.id.pagamento_cvc_cartao);
        editNomeCartao = findViewById(R.id.pagamento_nome_no_cartao);

        carregaPacoteRecebido();
    }

    private void carregaPacoteRecebido() {
        Intent intent = getIntent();
        if(intent.hasExtra(Constantes.CHAVE_PACOTE)){
            final Pacote pacote = (Pacote) intent.getSerializableExtra(Constantes.CHAVE_PACOTE);
            mostraPreco(pacote);
            configuraBotao(pacote);
        }
    }

    private void configuraBotao(final Pacote pacote) {
        Button botaoFinalizaCompra = findViewById(R.id.pagamento_botao_finaliza_compra);
        botaoFinalizaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarFormulario()) {
                    if(new CompraDAO(PagamentoActivity.this).inserir(pacote)>0) {
                        vaiParaResumoCompra(pacote);
                    }else{
                        MakeToastUtil.error("Não foi possível realizar sua compra neste momento!", PagamentoActivity.this);
                    }
                }
            }
        });
    }

    private void vaiParaResumoCompra(Pacote pacote) {
        Intent intent = new Intent(PagamentoActivity.this,
                ResumoCompraActivity.class);
        intent.putExtra(Constantes.CHAVE_PACOTE, pacote);
        startActivity(intent);
        finish();
    }

    private void mostraPreco(Pacote pacote) {
        TextView preco = findViewById(R.id.pagamento_preco_pacote);
        String moedaBrasileira = MoedaUtil
                .formataParaBrasileiro(pacote.getPreco());
        preco.setText(moedaBrasileira);
    }

    private boolean validarFormulario() {

        boolean isValid = true;

        if (!editNumeroCartao.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editMesCartao.validateWith(new RegexpValidator("", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editAnoCartao.validateWith(new RegexpValidator("", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editCvcCartao.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editNomeCartao.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        return isValid;
    }
}
