package com.minhaagenciadeviagens.agenciaviagens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.minhaagenciadeviagens.agenciaviagens.Commom.Commom;
import com.minhaagenciadeviagens.agenciaviagens.Commom.RegCons;
import com.minhaagenciadeviagens.agenciaviagens.DAO.UsuarioDAO;
import com.minhaagenciadeviagens.agenciaviagens.Model.Usuario;
import com.minhaagenciadeviagens.agenciaviagens.Util.MakeToastUtil;
import com.minhaagenciadeviagens.agenciaviagens.Util.MaskEdit;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

public class UsuarioActivity extends AppCompatActivity {

    //Componentes Layout
    private Button btnCadastro;
    private MaterialEditText editNome,
            editEndereco,
            editDataNascimento,
            editTelefone,
            editCidade,
            editUf,
            editEmail,
            editSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //Componentes Layout
        editNome = findViewById(R.id.activity_usuario_editNome);
        editEndereco = findViewById(R.id.activity_usuario_editEndereco);
        editDataNascimento = findViewById(R.id.activity_usuario_editDataNascimento);
        editTelefone = findViewById(R.id.activity_usuario_editTelefone);
        editCidade = findViewById(R.id.activity_usuario_editCidade);
        editUf = findViewById(R.id.activity_usuario_editUf);
        editEmail = findViewById(R.id.activity_usuario_editEmail);
        editSenha = findViewById(R.id.activity_usuario_editSenha);
        btnCadastro = findViewById(R.id.activity_usuario_btnCadastrar);

        MaskEdit.mask(editDataNascimento, MaskEdit.FORMAT_DATE);
        MaskEdit.mask(editTelefone, MaskEdit.FORMAT_FONE);

        editNome.setText(Commom.usuarioLogado.getNome());
        editEndereco.setText(Commom.usuarioLogado.getEndereco());
        editDataNascimento.setText(Commom.usuarioLogado.getDataNacimento());
        editTelefone.setText(Commom.usuarioLogado.getTelefone());
        editCidade.setText(Commom.usuarioLogado.getCidade());
        editUf.setText(Commom.usuarioLogado.getUf());
        editEmail.setText(Commom.usuarioLogado.getEmail());
        editSenha.setText(Commom.usuarioLogado.getSenha());

        //ClickListeners
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarFormulario()){
                    atualizar();
                }else {
                    MakeToastUtil.error("Por favor, preencha todos os campos corretamente!", UsuarioActivity.this);
                }
            }
        });
    }

    private void atualizar() {

        Usuario usuario = new Usuario(
                Commom.usuarioLogado.getIdUsuario(),
                1,
                editNome.getText().toString(),
                editEndereco.getText().toString(),
                editCidade.getText().toString(),
                editUf.getText().toString(),
                editDataNascimento.getText().toString(),
                editTelefone.getText().toString(),
                editEmail.getText().toString(),
                editSenha.getText().toString());

        long idUsuario = new UsuarioDAO(this).atualizar(usuario);

        if(idUsuario > 0) {

            MakeToastUtil.success("Cadastro atualizado com sucesso!", UsuarioActivity.this);
            Commom.usuarioLogado = usuario;
            startActivity(new Intent(UsuarioActivity.this, ListaPacotesActivity.class));
            finish();

        }else{
            MakeToastUtil.error("Cadastro não pôde ser realizado no momento!", UsuarioActivity.this);
        }
    }

    private boolean validarFormulario() {

        boolean isValid = true;

        if (!editNome.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editEndereco.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editDataNascimento.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editTelefone.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editCidade.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editUf.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.CHARS2))) {
            isValid = false;
        }

        if (!editUf.validateWith(new RegexpValidator("Este campo deve conter 2 caracteres! Ex.: RS", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editEmail.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editEmail.validateWith(new RegexpValidator("Email invalido!", RegCons.EMAIL))) {
            isValid = false;
        }

        if (!editSenha.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        return isValid;
    }
}
