package com.example.marcelo.forfood;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcelo.forfood.beans.Cliente;
import com.example.marcelo.forfood.beans.Pedido;
import com.example.marcelo.forfood.servicos.DataBase;
import com.example.marcelo.forfood.sinc.JSONDados;
import com.example.marcelo.forfood.sinc.JSONParser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marcelo on 05/06/17.
 */



public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    /*
    Primeiramente deve ser incluido no gradle a biblioteca de autenticação google play service, pode ser feita incluindo uma linha]: compile 'com.google.android.gms:play-services-auth:10.2.0'
    ou pode ser feito entrando em file-> project structure-> autentication e selecionar a opção e dar ok

    depois teremos que criar um  ID de cliente OAuth(em credenciais): https://console.developers.google.com/apis/credentials
    - executanto o comando no seu terminal:
        keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore -list -v
    - e pegando o path do manifest: com.example.fernando.logingoogle

    depois terá que ser gerado o json de configuração do Sign In e transportado para a pasta App
    https://developers.google.com/mobile/add?refresh=1

    depois é só iserir o botão especial no xml e realizar os códigos abaixo
     */

    private static final int RC_SIGN_IN = 9001;

    //Declarando a ApiClient a ser usada
    private GoogleApiClient mGoogleApiClient;

    //posta o status da autenticação
    private TextView tvStatus;
    private ProgressDialog mProgressDialog;
    private Intent intent ;
    private String mensagem;
    private String mensagem1;
    private DataBase db;
    private MediaPlayer mp  = null;
    private boolean loga;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //[Inicio confituração SignIn]
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(new Scope(Scopes.DRIVE_APPFOLDER)).requestEmail().build();
        //[Fim confituração SignIn]

        // [Inicio Criação cliente]
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [Fim Criação cliente]

        //[Inicio customiza botão]
        SignInButton signInButton = (SignInButton) findViewById(R.id.btnSiginIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        //[Fim customiza botão]

        //setando os listeners dos botões nesta activity
        findViewById(R.id.btnSiginIn).setOnClickListener(this);
        findViewById(R.id.btnSair).setOnClickListener(this);


        //tvStatus = (TextView) findViewById(R.id.tvStatus);

        // Solicita as permissões
        String[] permissoes = new String[]{
                Manifest.permission.INTERNET,
        };
        PermissionUtils.validate(this, 0, permissoes);

        intent = getIntent();
        mensagem = "sair";
        mensagem1 = "saindo";
        if (intent.getStringExtra("sair") != null){
            Log.d("[IFMG]", "ENTROU NO IF O ONCREATE");
            mensagem = intent.getStringExtra("sair");
        }

        db = new DataBase(getApplicationContext());


        mp = MediaPlayer.create(this,R.raw.bemvindo);

        loga = false;
    }

    //método onStard é executado assim aque a activity é iniciada a execução
    //por este motivo, aqui é verificado se o usuário já está ou não autenticado
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);

        if (opr.isDone()) {
            // Se as credenciais em cache do usuário forem válidas, o OptionalPendingResult será
            // "concluído" eo GoogleSignInResult estará disponível instantaneamente.
            Log.d("[IFMG]", "Tem login no cache");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // Se o usuário não tiver autenticado anteriormente neste dispositivo ou o login tiver expirado,
            // este trecho tentará fazer login no usuário silenciosamente.
            // A autenticação entre dispositivos ocorrerá neste trecho.
            Log.d("[IFMG]", "Tentando logar silencioso");
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //depois da autenticação a barra de progresso é fechada
        hideProgressDialog();
    }


    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            dialog = ProgressDialog.show(this, "Login", "Validando seus dados... Aguarde.", false, true);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {

            Log.d("[IFMG]", "MENSAGEM:" + mensagem);
            Log.d("[IFMG]", "Resultado se entrou:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                //signed_in_fmt
                //tvStatus.setText("Logado: " + acct.getEmail());
                try {
                    if (!mensagem.equalsIgnoreCase(mensagem1)) {
                        Log.d("[IFMG]", "ENTROU NO IF DO HANDLE:");
                        updateUI(true);



                        requisitaPost(JSONDados.geraJsonLogin(
                                acct.getId(),
                                acct.getDisplayName(),
                                acct.getEmail()),
                                "https://forfood.000webhostapp.com/json2.php");

                    }else {
                            mensagem = "";
                    }


                }catch (Exception e){
                    Log.d("[IFMG]", "ERRO TRY/CATCH:" + e);

                }




            } else {
                // Signed out, show unauthenticated UI.
                updateUI(false);
            }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    //mostra progressão indeterminada
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Carregando...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    //suprime progressão indeterminada
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.btnSiginIn).setVisibility(View.GONE);
            findViewById(R.id.btnSair).setVisibility(View.VISIBLE);
        } else {
            //tvStatus.setText("Deslogado");

            findViewById(R.id.btnSiginIn).setVisibility(View.VISIBLE);
            findViewById(R.id.btnSair).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSiginIn:
                signIn();
                break;
            case R.id.btnSair:
                loga = false;
                revokeAccess();
                break;
        }
    }


    //Método exigido pela implementação da OnConnectionFailedListener
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("[IFMG]", "Conexão falhou:" + connectionResult);
    }

    public void requisitaPost(final String parametroJSON, final String URL_) {


        //thread obrigatória para realização da requisição pode ser usado com outras formas de thread
        new Thread(new Runnable() {
            public void run() {
                final JSONParser jsonParser = new JSONParser();
                JSONObject json = null;
                try {
                    //prepara parâmetros para serem enviados via método POST
                    HashMap<String, String> params = new HashMap<>();
                    params.put("dados", parametroJSON);

                    Log.d("[IFMG]", parametroJSON);
                    Log.d("[IFMG]", "JSON Envio Iniciando...");

                    //faz a requisição POST e retorna o que o webservice REST envoiu dentro de json
                    json = jsonParser.makeHttpRequest(URL_, "POST", params);

                    Log.d("[IFMG]", " JSON Envio Terminado...");

                    //Mostra no log e retorna o que o json retornou, caso não retornou nulo
                    if (json != null) {
                        Log.d("[IFMG]", json.toString());
                        //return json;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("[IFMG]", "finalizando baixar defeitos");

                //----------------------------------------------
                //PÓS DOWNLOAD
                //----------------------------------------------

                //teste para ferificar se o json chegou corretamente e foi interpretado
                if (json != null) {
                    //------------------------------------------------------------
                    //AQUI SE PEGA O JSON RETORNADO E TRATA O QUE DEVE SER TRATADO
                    //------------------------------------------------------------
                  //  final String resp = interpretaJSON_Campos(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final String resp =  jsonParser.result();
                            Log.d("[IFMG]", "VARIAVEL RESP:" + resp+":");
                            if (resp.equalsIgnoreCase("{\"true\":\"true\"}  ")) {
                                Log.d("[IFMG]", "VARIAVEL LOGA É TRUE!!!!!!!!!!!!!!");
                                dialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
                                mp.start();
                                startActivity(i);
                            }else if(resp.equalsIgnoreCase("{\"false\":\"false\"}  ")){
                                dialog.dismiss();
                                Log.d("[IFMG]", "VARIAVEL LOGA È FALSE !!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                Toast.makeText(getApplicationContext(), "Você não esta registrado no ForFood", Toast.LENGTH_LONG).show();
                            }
                            Log.d("[IFMG]", "VARIAVEL LOGA É TRUE SAIU DO IF");
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Falha na conexão!!!", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        }).start();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////


}





