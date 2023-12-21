package com.example.myttt;

import static java.lang.Compiler.enable;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int ActivePlayer = 0;
    int[] GameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    ImageView imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9, imageView10;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        btn = findViewById(R.id.button);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    onImageViewClick((ImageView) v);
                } else if (v instanceof Button) {
                    onButtonClick((Button) v);
                }
            }
        };
        imageView2.setOnClickListener(clickListener);
        imageView3.setOnClickListener(clickListener);
        imageView4.setOnClickListener(clickListener);
        imageView5.setOnClickListener(clickListener);
        imageView6.setOnClickListener(clickListener);
        imageView7.setOnClickListener(clickListener);
        imageView8.setOnClickListener(clickListener);
        imageView9.setOnClickListener(clickListener);
        imageView10.setOnClickListener(clickListener);
        btn.setOnClickListener(clickListener);

    }

    public void onButtonClick(View view) {
        Button clickBtn = (Button) view;
        String str = clickBtn.getText().toString();
        if (str.equals("RESET")) {
            resetGame();
            enable();
        }
    }

        public void onImageViewClick (View view){
            ImageView img = (ImageView) view;
            int clickImage = Integer.parseInt(img.getTag().toString());
            if (GameState[clickImage] == 2) {
                GameState[clickImage] = ActivePlayer;
                if (ActivePlayer == 0) {
                    img.setImageResource(R.drawable.xp);
                    ActivePlayer = 1;
                } else {
                    img.setImageResource(R.drawable.op);
                    ActivePlayer = 0;
                }
            }
            checkWin();
        }

        public void checkWin () {
            int[][] winConditions = {
                    {0, 1, 2},
                    {3, 4, 5},
                    {6, 7, 8},
                    {0, 3, 6},
                    {1, 4, 7},
                    {2, 5, 8},
                    {0, 4, 8},
                    {2, 4, 6}
            };

            for (int[] condition : winConditions) {
                int firstCell = condition[0];
                int secondCell = condition[1];
                int thirdCell = condition[2];

                if (GameState[firstCell] == GameState[secondCell] &&
                        GameState[secondCell] == GameState[thirdCell] &&
                        GameState[firstCell] != 2) {

                    // We have a winner
                    String winner = (GameState[firstCell] == 0) ? "Player X" : "Player O";
                    showCustomToast(winner + " wins!");
                    disable();
                    return;
                }
            }

            // Check for a tie
            boolean isTie = true;
            for (int state : GameState) {
                if (state == 2) {
                    isTie = false;
                    break;
                }
            }

            if (isTie) {
                showCustomToast(" Match Tied!");
                disable();
            }
        }

        private void resetGame () {
            ActivePlayer = 0;
            for (int i = 0; i < GameState.length; i++) {
                GameState[i] = 2;
            }

            // Reset the image views
            imageView2.setImageResource(0);
            imageView3.setImageResource(0);
            imageView4.setImageResource(0);
            imageView5.setImageResource(0);
            imageView6.setImageResource(0);
            imageView7.setImageResource(0);
            imageView8.setImageResource(0);
            imageView9.setImageResource(0);
            imageView10.setImageResource(0);
        }
        public void disable () {
            imageView2.setEnabled(false);
            imageView3.setEnabled(false);
            imageView4.setEnabled(false);
            imageView5.setEnabled(false);
            imageView6.setEnabled(false);
            imageView7.setEnabled(false);
            imageView8.setEnabled(false);
            imageView9.setEnabled(false);
            imageView10.setEnabled(false);
        }

        public void enable () {
            imageView2.setEnabled(true);
            imageView3.setEnabled(true);
            imageView4.setEnabled(true);
            imageView5.setEnabled(true);
            imageView6.setEnabled(true);
            imageView7.setEnabled(true);
            imageView8.setEnabled(true);
            imageView9.setEnabled(true);
            imageView10.setEnabled(true);
        }


        private void showCustomToast (String message){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.customToastLayout));

            TextView text = layout.findViewById(R.id.toastMessage);
            text.setText(message);

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

}
