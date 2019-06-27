package com.minhaagenciadeviagens.agenciaviagens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import com.minhaagenciadeviagens.agenciaviagens.Commom.Commom;
import com.minhaagenciadeviagens.agenciaviagens.Commom.RegCons;
import com.minhaagenciadeviagens.agenciaviagens.DAO.UsuarioDAO;
import com.minhaagenciadeviagens.agenciaviagens.Model.Usuario;
import com.minhaagenciadeviagens.agenciaviagens.Util.MakeToastUtil;
import com.minhaagenciadeviagens.agenciaviagens.Util.MaskEdit;

public class CadastroUsuarioActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_cadastro_usuario);

        //Componentes Layout
        editNome = findViewById(R.id.activity_cadastro_usuario_editNome);
        editEndereco = findViewById(R.id.activity_cadastro_usuario_editEndereco);
        editDataNascimento = findViewById(R.id.activity_cadastro_usuario_editDataNascimento);
        editTelefone = findViewById(R.id.activity_cadastro_usuario_editTelefone);
        editCidade = findViewById(R.id.activity_cadastro_usuario_editCidade);
        editUf = findViewById(R.id.activity_cadastro_usuario_editUf);
        editEmail = findViewById(R.id.activity_cadastro_usuario_editEmail);
        editSenha = findViewById(R.id.activity_cadastro_usuario_editSenha);
        btnCadastro = findViewById(R.id.activity_cadastro_usuario_btnCadastrar);

        MaskEdit.mask(editDataNascimento, MaskEdit.FORMAT_DATE);
        MaskEdit.mask(editTelefone, MaskEdit.FORMAT_FONE);

        //ClickListeners
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarFormulario()){
                    adicionarUsuario();
                }else {
                    MakeToastUtil.error("Por favor, preencha todos os campos corretamente!", CadastroUsuarioActivity.this);
                }
            }
        });
    }

    private void adicionarUsuario() {

        Usuario usuario = new Usuario(
                1,
                editNome.getText().toString(),
                editEndereco.getText().toString(),
                editCidade.getText().toString(),
                editUf.getText().toString(),
                editDataNascimento.getText().toString(),
                editTelefone.getText().toString(),
                editEmail.getText().toString(),
                editSenha.getText().toString());

        long idUsuario = new UsuarioDAO(this).inserir(usuario);

        if(idUsuario > 0) {

            MakeToastUtil.success("Cadastro realizado com sucesso!", CadastroUsuarioActivity.this);
            Commom.usuarioLogado = usuario;
            Commom.usuarioLogado.setIdUsuario((int)idUsuario);
            startActivity(new Intent(CadastroUsuarioActivity.this, ListaPacotesActivity.class));
            finish();

        }else{
            MakeToastUtil.error("Cadastro não pôde ser realizado no momento!", CadastroUsuarioActivity.this);
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
