package com.example.tragamonedas_david;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int dificultad = 100;
    int columna;
    boolean[] continuar = {false, false, false};
    TextView tv, txtdifi;
    int[] imgId =
            {R.drawable.arm1,
                    R.drawable.arm2,
                    R.drawable.arm3,
                    R.drawable.arm4,
                    R.drawable.arm5,
                    R.drawable.arm6,
                    R.drawable.arm7,
                    R.drawable.arm8,
                    R.drawable.arm9,};
    int[][] secuencia = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {8, 7, 6, 5, 4, 3, 2, 1, 0},
            {4, 6, 5, 3, 2, 7, 8, 0, 1}};
    ImageView[][] imgv = new ImageView[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.texto);
        txtdifi = (TextView) findViewById(R.id.dificultad);

        imgv[0][0] = (ImageView) findViewById(R.id.arm1);
        imgv[1][0] = (ImageView) findViewById(R.id.arm4);
        imgv[2][0] = (ImageView) findViewById(R.id.arm7);
        imgv[0][1] = (ImageView) findViewById(R.id.arm2);
        imgv[1][1] = (ImageView) findViewById(R.id.arm5);
        imgv[2][1] = (ImageView) findViewById(R.id.arm8);
        imgv[0][2] = (ImageView) findViewById(R.id.arm3);
        imgv[1][2] = (ImageView) findViewById(R.id.arm6);
        imgv[2][2] = (ImageView) findViewById(R.id.arm9);

        View boton1 = findViewById(R.id.btnjugar1);
        boton1.setOnClickListener(this);
        View boton2 = findViewById(R.id.btnjugar2);
        boton2.setOnClickListener(this);
        View boton3 = findViewById(R.id.btnjugar3);
        boton3.setOnClickListener(this);

        View boton4 = findViewById(R.id.btn4);
        boton4.setOnClickListener(this);
        View boton5 = findViewById(R.id.btn5);
        boton5.setOnClickListener(this);
        View boton6 = findViewById(R.id.btn6);
        boton6.setOnClickListener(this);
    }

    class MiAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... parameter) {
            int columna = parameter[0];

            while (continuar[columna]){
                int elem1 = secuencia[columna][0];
                for (int i = 0; i < 8; i++) {
                    secuencia[columna][i] = secuencia[columna][i+1];
                }
                secuencia[columna][8] = elem1;
                try {
                    Thread.sleep(Math.abs(dificultad));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(columna);
            }
            return "Stop columna" + (columna+1);
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            int columna = progress[0];
            for(int i = 0;i<3;i ++) {
                imgv[i][columna].setImageResource(
                        imgId[secuencia[columna] [i]]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (continuar[0] == false & continuar[1] == false & continuar[2] == false){
                if (secuencia[0][1] == secuencia[1][1] & secuencia [0][1] == secuencia [2][1]){
                    tv.setText("¡ FELICIDADES !");
                }
                else{
                    tv.setText("Mala suerte, intena de nuevo");
                }
            }
            else{
                tv.setText(""+result);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn4 | view.getId()==R.id.btn5 | view.getId()==R.id.btn6){
            if (view.getId() == R.id.btn4)
                dificultad = dificultad + 10;
            if (view.getId() == R.id.btn5)
                dificultad = 200;
            if (view.getId() == R.id.btn6)
                dificultad = dificultad - 10;
            txtdifi.setText("Dificultad "+dificultad);
        }
        else{
            if (view.getId() == R.id.btnjugar1)columna=0;
            if (view.getId() == R.id.btnjugar2)columna=1;
            if (view.getId() == R.id.btnjugar3)columna=2;

            continuar[columna] =! continuar[columna];
            if (continuar[columna]){
                new MiAsyncTask().execute(columna);
                ((TextView)view).setText("Detener");
            }
            else{
                ((TextView)view).setText("Continuar");
            }
        }
    }
}