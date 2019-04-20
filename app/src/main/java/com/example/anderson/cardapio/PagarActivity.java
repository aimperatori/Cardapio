package com.example.anderson.cardapio;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PagarActivity extends Activity {

    Button btnPagar;
    EditText edtvalorConta;
    EditText edtvalorPago;
    double troco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);

        edtvalorConta = findViewById(R.id.edtvalorConta);
        edtvalorPago = findViewById(R.id.edtvalorPago);
        btnPagar = findViewById(R.id.btnPagar);

        final Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            edtvalorConta.setText(extras.getString("valoraPagar"));
            //Toast.makeText(getApplicationContext(), "texto valora apagar " +edtvalorConta.getText().toString(), Toast.LENGTH_LONG).show();
        }

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtvalorPago.length() == 0) {
                    edtvalorPago.selectAll();
                    edtvalorPago.requestFocus();
                    return;
                }
                troco = Double.parseDouble(edtvalorPago.getText().toString()) - Double.parseDouble(edtvalorConta.getText().toString());

                if (troco<0) {
                    edtvalorPago.selectAll();
                    edtvalorPago.requestFocus();
                    Toast.makeText(getApplicationContext(), "Valor Pago Insuficiente!", Toast.LENGTH_LONG).show();
                    return;
                }
                extras.remove("valoraPagar");
                onBackPressed();
        }
        });

    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent();
        it.putExtra("troco", Double.toString(troco));
        setResult(1, it);
        super.onBackPressed();
    }
}
