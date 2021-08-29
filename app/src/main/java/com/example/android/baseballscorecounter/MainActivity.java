package com.example.android.baseballscorecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    int scoreTeamA = 0;
    int scoreTeamB = 0;
    int strikesTeamA = 0;
    int strikesTeamB = 0;
    int ballsTeamA = 0;
    int ballsTeamB = 0;
    int outsTeamA = 0;
    int outsTeamB = 0;
    int inningTeamA = 0;
    int inningTeamB = 0;
    int[] inningScoreTeamA = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] inningScoreTeamB = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayNumbers("team_a");
        displayNumbers("team_b");
    }


    public void displayScore(String team) {
        if (team.equals("team_a")) {
            TextView teamScore = (TextView) findViewById(R.id.team_a_score);
            teamScore.setText(Integer.toString(scoreTeamA));
        } else if (team.equals("team_b")) {
            TextView teamScore = (TextView) findViewById(R.id.team_b_score);
            teamScore.setText(Integer.toString(scoreTeamB));
        }
    }

    /**
     * Displays all the numbers and the team name.
     */
    @SuppressLint("ResourceType")
    public void displayNumbers(String team) {

        int strikes;
        int balls;
        int outs;
        int inning;
        int[] inningScore;

        TableLayout teamScoreTable;
        if (team == "team_a") {
            // set team title
            TextView teamTitle = (TextView) findViewById(R.id.team_a);
            String teamName = getString(R.string.team_a_name);
            teamTitle.setText(teamName);
            displayScore(team);
            teamScoreTable = (TableLayout) findViewById(R.id.team_a_table);
            inningScore = inningScoreTeamA;
            strikes = strikesTeamA;
            inning = inningTeamA;
            balls = ballsTeamA;
            outs = outsTeamA;
        } else {
            // set team title
            TextView teamTitle = (TextView) findViewById(R.id.team_b);
            String teamName = getString(R.string.team_b_name);
            teamTitle.setText(teamName);
            displayScore(team);
            teamScoreTable = (TableLayout) findViewById(R.id.team_b_table);
            inningScore = inningScoreTeamB;
            strikes = strikesTeamB;
            inning = inningTeamB;
            balls = ballsTeamB;
            outs = outsTeamB;
        }

        for (int row = 0; row < teamScoreTable.getChildCount(); row++) {
            View tableRow = teamScoreTable.getChildAt(row);
            if (tableRow instanceof TableRow && row <= 10) {
                View rowChild = ((TableRow) tableRow).getChildAt(1);
                if (row < 10) {
                    if (rowChild instanceof TextView) {
                        TextView textView = (TextView) rowChild;
                        String scoreString = Integer.toString(inningScore[row]);
                        textView.setText(scoreString);
                    }
                }
            } else if (row == 11) {
                /**
                 *  add tags to strikes and total score
                 */
                View rowChild0 = ((TableRow) tableRow).getChildAt(0);
                if (rowChild0 instanceof TextView) {
                    TextView textView = (TextView) rowChild0;
                    textView.setText(Integer.toString(strikes));
                }
                View rowChild1 = ((TableRow) tableRow).getChildAt(1);
                if (rowChild1 instanceof TextView) {
                    TextView textView = (TextView) rowChild1;
                    textView.setText(Integer.toString(inning + 1));
                }
            } else if (row == 13) {
                /**
                 *  add tags to balls and outs
                 */
                View rowChild2 = ((TableRow) tableRow).getChildAt(0);
                if (rowChild2 instanceof TextView) {
                    TextView textView = (TextView) rowChild2;
                    textView.setText(Integer.toString(balls));
                }
                View rowChild3 = ((TableRow) tableRow).getChildAt(1);
                if (rowChild3 instanceof TextView) {
                    TextView textView = (TextView) rowChild3;
                    textView.setText(Integer.toString(outs));
                }
            }
        }
    }

    /**
     * add score
     *
     * @param view
     */
    public void addScore(View view) {
        Object tag = view.getTag();
        String team = tag.toString();
        if (team.equals("team_a")) {
            inningScoreTeamA[inningTeamA] += 1;
            // I can't imagine batter still in the box and
            // runner coming back to home to score
            // so I assume batter was able to walk/run
            strikesTeamA = 0;
            ballsTeamA = 0;
            // probably looping array is safer than this
            scoreTeamA += 1;
            displayNumbers("team_a");
        } else if (team.equals("team_b")) {
            inningScoreTeamB[inningTeamB] += 1;
            strikesTeamB = 0;
            ballsTeamB = 0;
            scoreTeamB += 1;
            displayNumbers("team_b");
        } else {
            Log.i("addScore", "addScore");
        }
    }

    /**
     * add ball
     * 4th ball resets the count
     *
     * @param view
     */
    public void addBall(View view) {
        Object tag = view.getTag();
        String team = tag.toString();
        if (team.equals("team_a")) {
            ballsTeamA += 1;
            // 4 balls, batter walks
            // batter getting hit <= too complicated
            if (ballsTeamA > 3) {
                ballsTeamA = 0;
            }
            TextView ballsTeamView = (TextView) findViewById(R.id.team_a_ball);
            ballsTeamView.setText(Integer.toString(ballsTeamA));
        } else if (team.equals("team_b")) {
            ballsTeamB += 1;
            if (ballsTeamB > 3) {
                ballsTeamB = 0;
            }
            TextView ballsTeamView = (TextView) findViewById(R.id.team_b_ball);
            ballsTeamView.setText(Integer.toString(ballsTeamB));
        } else {
            Log.i("addBall", "addBall");
        }
    }

    /**
     * add strikes
     * 3rd strike resets strike counts
     * and call addOutForTeamA
     *
     * @param view
     */
    public void addStrike(View view) {
        Object tag = view.getTag();
        String team = tag.toString();
        if (team.equals("team_a")) {
            strikesTeamA += 1;
            if (strikesTeamA > 2) {
                strikesTeamA = 0;
                // 3 strikes = 1 out, right? I think?
                addOut(view);
            }
            TextView strikesTeamAView = (TextView) findViewById(R.id.team_a_strike);
            strikesTeamAView.setText(Integer.toString(strikesTeamA));
        } else if (team.equals("team_b")) {
            strikesTeamB += 1;
            if (strikesTeamB > 2) {
                strikesTeamB = 0;
                // 3 strikes = 1 out, right? I think?
                addOut(view);
            }
            TextView strikesTeamAView = (TextView) findViewById(R.id.team_b_strike);
            strikesTeamAView.setText(Integer.toString(strikesTeamB));
        } else {
            Log.i("addStrike", "addStrike");
        }
    }

    /**
     * add outs
     * 3rd out, advance inning
     *
     * @param view
     */
    public void addOut(View view) {
        Object tag = view.getTag();
        String team = tag.toString();
        if (team.equals("team_a")) {
            outsTeamA += 1;
            if (outsTeamA > 2) {
                outsTeamA = 0;
                inningTeamA += 1;
            }
            TextView teamScore = (TextView) findViewById(R.id.team_a_inning);
            teamScore.setText(Integer.toString(inningTeamA + 1));
            TextView outsTeamAView = (TextView) findViewById(R.id.team_a_out);
            outsTeamAView.setText(Integer.toString(outsTeamA));
        } else if (team.equals("team_b")) {
            outsTeamB += 1;
            if (outsTeamB > 2) {
                outsTeamB = 0;
                inningTeamB += 1;
            }
            TextView teamScore = (TextView) findViewById(R.id.team_b_inning);
            teamScore.setText(Integer.toString(inningTeamB + 1));
            TextView outsTeamAView = (TextView) findViewById(R.id.team_b_out);
            outsTeamAView.setText(Integer.toString(outsTeamB));
        } else {
            Log.i("addStrike", "addStrike");
        }
    }


    public void resetScore(View view) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        strikesTeamA = 0;
        strikesTeamB = 0;
        ballsTeamA = 0;
        ballsTeamB = 0;
        outsTeamA = 0;
        outsTeamB = 0;
        inningTeamA = 0;
        inningTeamB = 0;
        Arrays.fill(inningScoreTeamA, 0);
        Arrays.fill(inningScoreTeamB, 0);
        displayNumbers("team_a");
        displayNumbers("team_b");
    }
}