package com.example.anderson.cardapio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class MainActivity extends Activity {

    RadioGroup radiogroup;
    RadioButton rbcachorroQuente;
    RadioButton rbbauruS;
    RadioButton rbbauruOvo;
    Spinner spinner;
    EditText edtcachorroQuente;
    EditText edtnomeCliente;
    EditText edtQuantidade;
    Button btnIncluir;
    ListView lista;
    EditText edtTotal;
    CheckBox cbComissao;
    Button btnefetuarPag;
    Button btnnovoCliente;
    ImageView imagemView;
    String item;
    double itemValor;
    double valor = 0;
    boolean comissao = false;
    int posicaoSelecionada;
    int cont = 0;
    ProgressDialog progresso;
    Handler handler;

    ArrayList<String> listItem = new ArrayList<String>();
    ArrayAdapter<String> aListItem;

    ArrayList<Double> valorItem = new ArrayList<Double>();
    ArrayAdapter<Double> aListValor;

    String salsicha[] = {"Um salsicha",
                        "Duas salsichas"};
    ArrayAdapter<String> array_salsicha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        rbcachorroQuente = (RadioButton) findViewById(R.id.rbcachorroQuente);
        rbbauruS = (RadioButton) findViewById(R.id.rbbauruS);
        rbbauruOvo = (RadioButton) findViewById(R.id.rbbauruOvo);
        spinner = (Spinner) findViewById(R.id.spinner);
        edtcachorroQuente = (EditText) findViewById(R.id.edtcachorroQuente);
        edtnomeCliente = (EditText) findViewById(R.id.edtnomeCliente);
        edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
        btnIncluir = (Button) findViewById(R.id.btnIncluir);
        lista = (ListView) findViewById(R.id.lista);
        edtTotal = (EditText) findViewById(R.id.edtTotal);
        cbComissao = (CheckBox) findViewById(R.id.cbComissao);
        btnefetuarPag = (Button) findViewById(R.id.btnefetuarPag);
        btnnovoCliente = (Button) findViewById(R.id.btnnovoCliente);
        imagemView = (ImageView) findViewById(R.id.imageView);

        rbcachorroQuente.setChecked(true);
        itemValor = 3.50;
        item = "Ref.1 - Cachorro Quente - ";

        array_salsicha = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, salsicha);
        spinner.setAdapter(array_salsicha);


        aListItem = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
        lista.setAdapter(aListItem);

        aListValor = new ArrayAdapter<Double>(this, android.R.layout.simple_list_item_1, valorItem);


        handler = new Handler();


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup grupo, int i) {
                int marcado = grupo.getCheckedRadioButtonId();
                switch (marcado) {
                    case R.id.rbcachorroQuente:
                        spinner.setVisibility(EditText.VISIBLE);
                        imagemView.setImageResource(R.drawable.cachorroquente);
                        item = "Ref.1 - Cachorro Quente - ";
                        itemValor = 3.50;
                        break;
                    case R.id.rbbauruS:
                        spinner.setVisibility(EditText.INVISIBLE);
                        imagemView.setImageResource(R.drawable.baurusimples);
                        item = "Ref.2 - Bauru Simples - ";
                        itemValor = 4.00;
                        break;
                    case R.id.rbbauruOvo:
                        spinner.setVisibility(EditText.INVISIBLE);
                        imagemView.setImageResource(R.drawable.bauruovo);
                        item = "Ref.3 - Bauru com Ovo - ";
                        itemValor = 4.50;
                        break;
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                switch (pos) {
                    case 0:
                        edtcachorroQuente.setText("R$ 3,50");
                        itemValor = 3.50;
                        break;
                    case 1:
                        edtcachorroQuente.setText("R$ 4,50");
                        itemValor = 4.50;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtQuantidade.length() == 0) {
                    edtQuantidade.selectAll();
                    edtQuantidade.requestFocus();
                    return;
                }

                valorItem.add(cont, itemValor * Double.parseDouble(edtQuantidade.getText().toString()));

                String itemFinal = item.concat("Quantidade: " + Integer.parseInt(edtQuantidade.getText().toString()) + " - Valor: R$ " + valorItem.get(cont));

                listItem.add(cont, itemFinal);

                aListItem.notifyDataSetChanged();
                aListValor.notifyDataSetChanged();

                valor += valorItem.get(cont);
                if (comissao)
                    edtTotal.setText(Double.toString(valor + valor*0.10));
                else
                    edtTotal.setText(Double.toString(valor));

                cont++;
            }
        });

        cbComissao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    comissao = true;
                    edtTotal.setText(Double.toString(valor + valor * 0.10));
                } else {
                    comissao = false;
                    edtTotal.setText(Double.toString(valor));
                }
            }
        });


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posicaoSelecionada = i; // posicaoSelecionada é declarado como campo da classe int posicaoSelecionada
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setIcon(android.R.drawable.ic_dialog_alert);
                dialogo.setTitle("Exclusão");
                dialogo.setMessage("Deseja Excluir o registro?");
                dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        progresso = ProgressDialog.show(MainActivity.this, "Aguarde", "Processando...");

                        new Thread() {
                            public void run() {
                                try {
                                    // simulando um processo demorado
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                handler.post(new Runnable() {
                                    public void run() {
                                        valor -= Double.parseDouble(valorItem.get(posicaoSelecionada).toString());
                                        edtTotal.setText(Double.toString(valor));
                                        cont--;
                                        listItem.remove(posicaoSelecionada);
                                        valorItem.remove(posicaoSelecionada);
                                        aListItem.notifyDataSetChanged();
                                        aListValor.notifyDataSetChanged();
                                    }
                                });
                                //desativa a barra
                                progresso.dismiss();
                            }
                        }.start();
                    }
                });
                dialogo.setNegativeButton("Cancelar", null);
                dialogo.show();
               }
        });

        btnefetuarPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityPagar = new Intent(MainActivity.this, PagarActivity.class);
                activityPagar.putExtra("valoraPagar", edtTotal.getText().toString());
                startActivityForResult(activityPagar, 1);
                activityPagar.removeExtra("valoraPagar");
            }
        });


        btnnovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoCliente();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Toast.makeText(getApplicationContext(), "texto 0" , Toast.LENGTH_LONG).show();
        switch (requestCode) {
            case 1:
                //Toast.makeText(getApplicationContext(), "texto 1" +data.getExtras().getString("troco"), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pagamento Efetuado!");
                builder.setMessage("Troco: " +data.getExtras().getString("troco"))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                novoCliente();
                            }
                        });
                builder.show();
                data.removeExtra("troco");
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void novoCliente() {
        edtnomeCliente.setText("");
        edtQuantidade.setText("");
        rbcachorroQuente.setChecked(true);
        cbComissao.setChecked(false);
        edtTotal.setText("");
        valor = 0;
        //Toast.makeText(getApplicationContext(), "texto" +cont, Toast.LENGTH_LONG).show();
        while (cont>0) {
            cont--;
            listItem.remove(cont);
            valorItem.remove(cont);
            aListItem.notifyDataSetChanged();
            aListValor.notifyDataSetChanged();
        }
    }
}
