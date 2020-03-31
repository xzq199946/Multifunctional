package com.example.myapplicationcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ListIterator;

public class CalculateActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    TextView tvtshow;
    Button btnClean, btnBack, btnAddMinusSign, btnDiv,//清空按钮，撤退按钮，+/-按钮: 其功能是增加(-已填数字)，将符号减与负数区分开，除法按钮
    btn7,btn8,btn9,btnMul,
    btn4,btn5,btn6,btnDec,
    btn1, btn2, btn3,btnAdd,
    btn0,btnPoint,btnEquals;
    ArrayList<Character> sign = new ArrayList<>(10);//创建字符集列表,初始容量为10，容量可以自动扩充
    ArrayList<Double> allValue = new ArrayList<>(10);//数值集
    ArrayList<Double> resultSet = new ArrayList<>(10);//结果集
    String sb = "";//保存数字的字符串
    double value = 0.0;//符号分隔的值
    double sum = 0.0;//出现新的数值value时计算上一式子的值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calculate);
        tvtshow = findViewById(R.id.txtShowCalculate);

        btnClean = findViewById(R.id.btnClean);
        btnBack = findViewById(R.id.btnBack);
        btnAddMinusSign = findViewById(R.id.btnAddMinusSign);
        btnDiv = findViewById(R.id.btnDiv);

        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnMul = findViewById(R.id.btnMul);

        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btnDec = findViewById(R.id.btnDec);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btnAdd = findViewById(R.id.btnAdd);

        btn0 = findViewById(R.id.btn0);
        btnPoint = findViewById(R.id.btnPoint);
        btnEquals = findViewById(R.id.btnEquals);
        System.out.println("12");
        btnClean.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnBack.setOnLongClickListener(this);
        btnAddMinusSign.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnMul.setOnClickListener(this);

        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btnDec.setOnClickListener(this);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        btn0.setOnClickListener(this);
        btnPoint.setOnClickListener(this);
        btnEquals.setOnClickListener(this);

        tvtshow.setHorizontallyScrolling(false);
    }

    @Override
    public void onClick(View view){
        switch (view.getId())
        {
            case R.id.btn0:
                sb+="0";
                tvtshow.append("0");
                break;
            case R.id.btn1:
                sb+="1";
                tvtshow.append("1");
                break;
            case R.id.btn2:
                sb+="2";
                tvtshow.append("2");
                break;
            case R.id.btn3:
                sb+="3";
                tvtshow.append("3");
                break;
            case R.id.btn4:
                sb+="4";
                tvtshow.append("4");
                break;
            case R.id.btn5:
                sb+="5";
                tvtshow.append("5");
                break;
            case R.id.btn6:
                sb+="6";
                tvtshow.append("6");
                break;
            case R.id.btn7:
                sb+="7";
                tvtshow.append("7");
                break;
            case R.id.btn8:
                sb+="8";
                tvtshow.append("8");
                break;
            case R.id.btn9:
                sb+="9";
                tvtshow.append("9");
                break;
            case R.id.btnAdd:
                if(!sb.equals("")){
                    value = Double.parseDouble(sb);
                    allValue.add(value);
                    sb="";
                }
                if(resultSet.size()>0){
                    sum = resultSet.get(resultSet.size()-1);
                    System.out.println("hfafdsadfadf"+sum);
                }
                sign.add('+');
                tvtshow.append("＋");
                break;
            case R.id.btnDec:
                if(!sb.equals("")){
                    value = Double.parseDouble(sb);
                    allValue.add(value);
                    sb="";
                }
                if(resultSet.size()>0){
                    sum = resultSet.get(resultSet.size()-1);
                    System.out.println("hfafdsadfadf"+sum);
                }
                sign.add('-');
                tvtshow.append("-");
                break;
            case R.id.btnDiv:
                if(!sb.equals("")){
                    value = Double.parseDouble(sb);
                    allValue.add(value);
                    sb="";
                }
                if(resultSet.size()>0){
                    sum = resultSet.get(resultSet.size()-1);
                    System.out.println("hfafdsadfadf"+sum);
                }
                sign.add('/');
                tvtshow.append("÷");
                break;
            case R.id.btnMul:
                if(!sb.equals("")){
                    value = Double.parseDouble(sb);
                    allValue.add(value);
                    sb="";
                }
                if(resultSet.size()>0){
                    sum = resultSet.get(resultSet.size()-1);
                    System.out.println("hfafdsadfadf"+sum);
                }
                sign.add('*');
                tvtshow.append("×");
                break;
            case R.id.btnEquals:
                if(!sb.equals("")){
                    value = Double.parseDouble(sb);
                    System.out.println(sb);
                    sb = "";
                    allValue.add(value);
                }
                    if(sign.size() == (allValue.size()+resultSet.size())){
                        sign.remove(sign.size()-1);
                        tvtshow.setText(tvtshow.getText().toString().substring(0, tvtshow.getText().length()-1));
                    }


//                ListIterator<Double> doubleListIterator = allValue.listIterator();
//                ListIterator<String> stringListIterator = sign.listIterator();
                Double[] valueDouble= (Double[])allValue.toArray(new Double[allValue.size()]);//将列表转成数组
                Character[] signSet = (Character[])sign.toArray(new Character[sign.size()]);//转成字符数组
//                while (doubleListIterator.hasNext()){
//                    int indx = doubleListIterator.nextIndex();
//                    doubleListIterator.next();
//                    allValue.get(indx);
//                }
                int signnum = 0;
                for(int f =signSet.length-1; f>=0;f--){//倒序来算
                    if(signSet[f].equals('=')){
                        signnum = f+1;//f为序号
                        System.out.println(signnum);
                        break;
                    }
                }
                for(int i=signnum,j=signnum; i<valueDouble.length;i++,j++){//i表示数值在数组中的位置，表示符号在符号集中的位置
                    if(i==1){//有两个数值的时候
                        if(signSet[i-1].equals('+')){
                            sum = valueDouble[i-1] + valueDouble[i];
                        }else if(signSet[j-1].equals('-')){
                            sum = valueDouble[i-1] - valueDouble[i];
                        }else if(signSet[j-1].equals('*')){
                            sum = valueDouble[i-1] * valueDouble[i];
                        }else if(signSet[j-1].equals('/')){
                            sum = valueDouble[i-1] / valueDouble[i];
                        }
                    }else if(i>1){//有两个数值以上的时候
                        if(signnum!=0){//有结果集有等号时
                            if(signSet[i].equals('+')){
                                sum += valueDouble[i];
                                System.out.println("woca"+valueDouble[i]);//i==4但是valueDouble.length == 4
                            }else if(signSet[j].equals('-')){
                                sum -= valueDouble[i];
                            }else if(signSet[j].equals('*')){
                                sum *= valueDouble[i];
                            }else if(signSet[j].equals('/')){
                                sum /= valueDouble[i];
                            }
                        }else{//结果集无等号时
                            if(signSet[i-1].equals('+')){
                                sum += valueDouble[i];
                            }else if(signSet[j-1].equals('-')){
                                sum -= valueDouble[i];
                            }else if(signSet[j-1].equals('*')){
                                sum *= valueDouble[i];
                            }else if(signSet[j-1].equals('/')){
                                sum /= valueDouble[i];
                            }
                        }

                    }else{//i==0时
                        sum = valueDouble[i];
                    }

                }
                tvtshow.append("\n"+String.valueOf(sum));
                resultSet.add(sum);//将结果添加到结果集中
                Double[] result= (Double[])allValue.toArray(new Double[allValue.size()]);
//                double resultfinally = Double.valueOf(result.toString());
//                tvtshow.append(result.toString().trim());
                sign.add('=');
                break;
            case R.id.btnPoint:
                tvtshow.append(".");
                sb += ".";
                break;
            case R.id.btnClean:
                tvtshow.setText("");
                sb = "";//初始化每次单次输入的数
                ListIterator<Double> doubleListIterator = allValue.listIterator();//迭代删除数组集
                ListIterator<Character> charListIterator = sign.listIterator();//迭代删除字符集
                while (doubleListIterator.hasNext()){
                    doubleListIterator.next();
                    doubleListIterator.remove();
                }
                while (charListIterator.hasNext()){
                    charListIterator.next();
                    charListIterator.remove();
                }
//                result = new Double[100];
//                for(int i=0; i<result.length; i++){
//
//                }
                break;
            case R.id.btnBack:
                if(tvtshow.getText().toString().trim().equals("")){//输入数字后会留下空白符,虽然你看不到
                    AlertDialog.Builder builder = new AlertDialog.Builder(CalculateActivity.this);
                    final AlertDialog alertDialog= builder.create();
                    alertDialog.setMessage("请输入的数字！");
                    alertDialog.show();
                }
                else if(sign.size()>=allValue.size()){
                    sign.remove(sign.size()-1);
                    tvtshow.setText(tvtshow.getText().toString().substring(0,tvtshow.length()-1));
                }
                else {
                    if (sign.size()>0 && sign.get(sign.size() - 1).toString().equals("=")) {
                        //删除View文本的最后一次运算的结果
                        String lastResult = resultSet.get(resultSet.size() - 1).toString();
                        int lRLength = lastResult.length();
                        tvtshow.setText(tvtshow.getText().toString().substring(0, tvtshow.getText().length() - lRLength - 1));//长度到序号减去1,还有一个空格减去1
                        resultSet.remove(resultSet.size() - 1);
                        sign.remove(sign.size() - 1);
                        sb = allValue.get(allValue.size() - 1).toString();
                        allValue.remove(allValue.size() - 1);
                    }else {
                        int tvtshowlength = tvtshow.getText().length();//文本区的长度
                        //获得最后一个字
                        String back = tvtshow.getText().toString().substring(tvtshow.getText().length(),
                                tvtshow.getText().length());

                        if (back.equals("＋") || back.equals("-") | back.equals("×") || back.equals("÷") ||
                                back.equals("＝")) {
                            ListIterator<Character> charlist = sign.listIterator();
                            while (charlist.hasNext()) {//跳到最后一个
                                charlist.next();
                            }
                            charlist.remove();//删除符号集的最后一个符号
                        } else {
                            sb = sb.substring(0, sb.length() - 1);//让sb去掉最后一位
                        }
                        tvtshow.setText(tvtshow.getText().toString().substring(0, tvtshow.getText().length() - 1));
                        // tvtshow.setSelectAllOnFocus(tvtshow.getText().length());//设置光标始终在最后


                    }
                }
                break;
            case R.id.btnAddMinusSign://暂无功能
                break;
        }
        }

    @Override
    public boolean onLongClick(View view) {
        System.out.println("14");
        if(view.getId() == R.id.btnBack){
                tvtshow.append("长按功能");
                finish();
        }
        return false;
    }
}
