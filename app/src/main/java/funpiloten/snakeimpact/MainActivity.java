package funpiloten.snakeimpact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView row1;
    private TextView row2;
    private TextView row3;
    private TextView bt1;
    private TextView bt2;
    private TextView bt3;
    private TextView bt4;
    private TextView bt5;
    private TextView bt6;
    private TextView bt7;
    private TextView bt8;
    private TextView bt9;
    private TextView bt0;
    private TextView btStart;
    private TextView btEnter;

    private long RandomNumber = 0;
    private boolean isStart = false;
    private int Counter = 0;
    private int Runden = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bildschirmanpassen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        row1 = (TextView)findViewById(R.id.textView1);
        row2 = (TextView)findViewById(R.id.textView2);
        row3 = (TextView)findViewById(R.id.textView3);
        row1.setText("Um das Sicherheitssystem zu deaktivieren musst du die Zahl erraten welche ich mir merke.");
        row2.setText("");
        row3.setText("Drücke START um das Spiel zu beginnen!");

        bt0 = (TextView)findViewById(R.id.bt00);
        bt1 = (TextView)findViewById(R.id.bt01);
        bt2 = (TextView)findViewById(R.id.bt02);
        bt3 = (TextView)findViewById(R.id.bt03);
        bt4 = (TextView)findViewById(R.id.bt04);
        bt5 = (TextView)findViewById(R.id.bt05);
        bt6 = (TextView)findViewById(R.id.bt06);
        bt7 = (TextView)findViewById(R.id.bt07);
        bt8 = (TextView)findViewById(R.id.bt08);
        bt9 = (TextView)findViewById(R.id.bt09);
        btStart = (TextView)findViewById(R.id.btStart);
        btEnter = (TextView)findViewById(R.id.btEnter);

        bt0.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        btStart.setOnClickListener(this);
        btEnter.setOnClickListener(this);
        showButtonText();
        Spielablauf();

    }





    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt00:
                Eingabe(0);
                break;
            case R.id.bt01:
                Eingabe(1);
                break;
            case R.id.bt02:
                Eingabe(2);
                break;
            case R.id.bt03:
                Eingabe(3);
                break;
            case R.id.bt04:
                Eingabe(4);
                break;
            case R.id.bt05:
                Eingabe(5);
                break;
            case R.id.bt06:
                Eingabe(6);
                break;
            case R.id.bt07:
                Eingabe(7);
                break;
            case R.id.bt08:
                Eingabe(8);
                break;
            case R.id.bt09:
                Eingabe(9);
                break;
            case R.id.btStart:
                Game();
                break;
            case R.id.btEnter:
                Enter();
                break;
        }
    }

    private int Ziffer;
    private long eingabe=0;

    private void Eingabe(int i){
        if(!isStart)Game();
        eingabe = eingabe*10 + i;
        row3.setText(String.valueOf(eingabe));
        Ziffer++;
        setButtonNum(!(Ziffer>=3));
    }


    private void Game(){
        if(!isStart) {
            RandomNumber = System.currentTimeMillis() % 1000;
            row1.setText("Suche eine Zahl\nzwischen 0...999");
            row2.setText("SPIELBEGINN\nDu hast 10 Versuche");
            row3.setText("");
            Counter = 10;
            Ziffer = 0;
            isStart = true;
            eingabe = 0;
            showButtonText();
        }else{
            if(Ziffer>0){
                eingabe /= 10;
                Ziffer--;
            }else eingabe = 0;
            row3.setText(String.valueOf(eingabe));
            setButtonNum(true);
        }

    }

    private void Spielablauf(){
        isStart=false;
        showButtonText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sicherheitssystem der Independece");
        builder.setMessage("Um das Sicherheitsystem zu deaktivieren musst du eine Zahl erraten." +
                "\n\nDu hast 10 Versuche und erfährst ob die gesuchte Zahl größer oder kleiner ist." +
                "\n\nHast du nach 10 Versuche die Zahl nicht erraten, dann beginnt eine neue Runde und du musst eine neue Zahl erraten.");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void Enter(){
        String s = row3.getText().toString();
        if(s==null || s=="")s="0";
        long in = Long.parseLong(s);

        if(RandomNumber==in) Richtig();
        else{
            if(RandomNumber<in) row2.setText("Die gesuchte Zahl ist kleiner als " + in);
            else if(RandomNumber>in) row2.setText("Die gesuchte Zahl ist größer als " + in);
            Counter--;
            row1.setText("Anzahl der Veruche: " + Counter);
            setButtonNum(true);
            if(Counter==0)SpielEnde();
            Ziffer=0;
            eingabe = 0;
            row3.setText(String.valueOf(eingabe));
        }
    }

    private void SpielEnde(){
        Runden++;
        isStart=false;
        showButtonText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Leider Verloren!!!");
        builder.setMessage("Die gesuchte Zahl war: " + RandomNumber);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(Runden==5)Hinweis1();
                        else if(Runden==9)Hinweis2();
                        else if(Runden>11)Hinweis3();
                        dialogInterface.dismiss();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void Hinweis1(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("1. Hinweis");
        builder.setMessage("Denke Binär!!!");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Hinweis2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("2. Hinweis");
        builder.setMessage("Beginne bei 512!!!");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void Hinweis3(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("3. Hinweis");
        builder.setMessage("512\n+/-256\n+/-128\n+/-64\n+/-32\n+/-16\n+/-8\n+/-4\n+/-2\n+/-1");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true; // You need to return true here.  This tells android that you consumed the event.
        }
        return false;
    }

    private void Richtig(){
        Runden=0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sicherheitsystem deaktiviert");
        builder.setMessage("Die gesuchte Zahl war: " + RandomNumber +
                "\n\nDie Energieversorgung der Independence ist soweit aufgeladen um die nukleare Sprengung durchzuführen." +
                "\nFliege nun 120m in Richtung 20° zur Rettungsstation und warte auf der Meteriodenschauer." +
                "\n\nVergiß nicht bei der Abreise von der Rettungsstation dich im Logbuch einzutragen und an der Tafel zu verewigen." +
                "\n\n\nBitte schalte das Gerät aus und lege es wieder so wie du es vorgefunden hast zurück in den Behälter." +
                "\nDanke und Gruß die FunPiloten");
        AlertDialog dialog = builder.create();
        dialog.show();

        row2.setText("Gehe nun 120m in Richtung 20°");
        row1.setText("Die gesuchte Zahl " + RandomNumber + " hast du mit " + (10-(Counter-1)) + " Versuche gefunden!!!");
        isStart=false;
        showButtonText();

    }

    private void showButtonText(){
        if(isStart){
            btStart.setText("DEL");
            btEnter.setEnabled(true);
            setButtonNum(true);
        }
        else {
            btStart.setText("START");
            btEnter.setEnabled(false);
            setButtonNum(false);
        }
    }

    private void setButtonNum(boolean status){
        bt0.setEnabled(status);
        bt1.setEnabled(status);
        bt2.setEnabled(status);
        bt3.setEnabled(status);
        bt4.setEnabled(status);
        bt5.setEnabled(status);
        bt6.setEnabled(status);
        bt7.setEnabled(status);
        bt8.setEnabled(status);
        bt9.setEnabled(status);

    }
//


}
