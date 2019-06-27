package com.minhaagenciadeviagens.agenciaviagens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import com.minhaagenciadeviagens.agenciaviagens.Commom.Commom;
import com.minhaagenciadeviagens.agenciaviagens.Commom.RegCons;
import com.minhaagenciadeviagens.agenciaviagens.DAO.UsuarioDAO;
import com.minhaagenciadeviagens.agenciaviagens.Model.Usuario;
import com.minhaagenciadeviagens.agenciaviagens.Util.MakeToastUtil;

public class LoginActivity extends AppCompatActivity {

    private Button btnAcessar;
    private MaterialEditText editEmail, editSenha;
    private TextView txtCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Componentes Layout
        btnAcessar = findViewById(R.id.activity_login_btnAcessar);
        editEmail = findViewById(R.id.activity_login_editEmail);
        editSenha = findViewById(R.id.activity_login_editSenha);
        txtCadastrar = findViewById(R.id.activity_login_txtCadastrar);

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin();
            }
        });

        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void realizarLogin() {

        boolean isValid = true;
        if (!editEmail.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (!editSenha.validateWith(new RegexpValidator("Este campo não pode ser vazio!", RegCons.VAZIO))) {
            isValid = false;
        }

        if (isValid) {

            Usuario usuario = new UsuarioDAO(this)
                    .listarPorEmailSenha(editEmail.getText().toString(),
                            editSenha.getText().toString());

            if (usuario.getIdUsuario() > 0) {
                Commom.usuarioLogado = usuario;
                Intent intent = new Intent(LoginActivity.this, ListaPacotesActivity.class);
                startActivity(intent);
                finish();
            } else {
                MakeToastUtil.error("Usuário ou senha inválidos!", LoginActivity.this);
            }
        } else {
            MakeToastUtil.error("Por favor, preencha todos os campos corretamente!", LoginActivity.this);
        }
    }
}
