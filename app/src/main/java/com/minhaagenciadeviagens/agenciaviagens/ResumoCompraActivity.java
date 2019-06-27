package com.minhaagenciadeviagens.agenciaviagens;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.minhaagenciadeviagens.agenciaviagens.Commom.Commom;
import com.minhaagenciadeviagens.agenciaviagens.Commom.Constantes;
import com.minhaagenciadeviagens.agenciaviagens.Model.Pacote;
import com.minhaagenciadeviagens.agenciaviagens.Util.DataUtil;
import com.minhaagenciadeviagens.agenciaviagens.Util.MakeToastUtil;
import com.minhaagenciadeviagens.agenciaviagens.Util.MoedaUtil;
import com.minhaagenciadeviagens.agenciaviagens.Util.ResourceUtil;

public class ResumoCompraActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Resumo da compra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_compra);
        setTitle(TITULO_APPBAR);
        carregaPacoteRecebido();
    }

    private void carregaPacoteRecebido() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constantes.CHAVE_PACOTE)){
            Pacote pacote = (Pacote) intent.getSerializableExtra(Constantes.CHAVE_PACOTE);
            inicializaCampos(pacote);
            enviarEmail(pacote);
        }
    }

    private void enviarEmail(final Pacote pacote) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Enviar comprovante!");
        alertDialog.setMessage("Por favor, selecione a seguir o seu cliente Gmail.");

        alertDialog.setPositiveButton(
                "Entendi",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{Commom.emailServer});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Pagamento Via Aplicativo - "+ getString(R.string.app_name));
                        i.putExtra(Intent.EXTRA_TEXT   ,
                                "Pagamento realizado com sucesso!\n" +
                                        "----------------------------------------\n" +
                                        "Dados do cliente: \n" +
                                        "Nome: " + Commom.usuarioLogado.getNome() + "\n" +
                                        "Endereço: " + Commom.usuarioLogado.getEndereco() + "\n" +
                                        "Cidade: " + Commom.usuarioLogado.getCidade() + "\n" +
                                        "UF: " + Commom.usuarioLogado.getUf() + "\n" +
                                        "Data Nascimento: " + Commom.usuarioLogado.getDataNacimento() + "\n" +
                                        "Telefone: " + Commom.usuarioLogado.getTelefone() + "\n" +
                                        "Email: " + Commom.usuarioLogado.getEmail() + "\n" +
                                        "-----------------------------------------\n" +
                                        "Pacote: \n" +
                                        "Local: " + pacote.getLocal() + "\n" +
                                        "Dias: " + pacote.getDias() + "\n" +
                                        "Valor Pago: " + MoedaUtil.formataParaBrasileiro(pacote.getPreco()));
                        try {
                            startActivity(Intent.createChooser(i, "Enviar email..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            MakeToastUtil.error("Não encontrado um cliente de email válido!", ResumoCompraActivity.this);
                        }
                    }
                }
        );

        alertDialog.setCancelable(false);

        alertDialog.show();
    }

    private void inicializaCampos(Pacote pacote) {
        mostraLocal(pacote);
        mostraImagem(pacote);
        mostraData(pacote);
        mostraPreco(pacote);
    }

    private void mostraPreco(Pacote pacote) {
        TextView preco = findViewById(R.id.resumo_compra_preco_pacote);
        String moedaBrasileira = MoedaUtil
                .formataParaBrasileiro(pacote.getPreco());
        preco.setText(moedaBrasileira);
    }

    private void mostraData(Pacote pacote) {
        TextView data = findViewById(R.id.resumo_compra_data_viagem);
        String periodoEmTexto = DataUtil
                .periodoEmTexto(pacote.getDias());
        data.setText(periodoEmTexto);
    }

    private void mostraImagem(Pacote pacote) {
        ImageView imagem = findViewById(R.id.resumo_compra_imagem_pacote);
        Drawable drawableDoPacote = ResourceUtil
                .devolveDrawable(this, pacote.getImagem());
        imagem.setImageDrawable(drawableDoPacote);
    }

    private void mostraLocal(Pacote pacote) {
        TextView local = findViewById(R.id.resumo_compra_local_pacote);
        local.setText(pacote.getLocal());
    }
}
