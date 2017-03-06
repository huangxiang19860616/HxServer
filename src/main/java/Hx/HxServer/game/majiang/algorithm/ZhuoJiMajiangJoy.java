package Hx.HxServer.game.majiang.algorithm;


import Hx.HxServer.exception.XAssert;
import Hx.HxServer.game.majiang.algorithm.card.IMajiangCard;
import Hx.HxServer.game.majiang.algorithm.rules.IMajiangRule;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by huangxiang on 2017/2/17 0017.
 */
public class ZhuoJiMajiangJoy implements IMajiangJoy{

    private int renshu;   //����
    private IMajiangPlayer[] player;  //���
    private Map<Integer, IMajiangRule> ruleMap=new HashMap<>();  //����

    private int dice1;  //����1
    private int dice2;  //����2

    private final IMajiangPlayer fangzhu;
    private final int outCardTime;
    private IMajiangPlayer zhuangjia;
    private IMajiangPlayer outCardPlayer;  //  ��ǰ�����û�
    
    private IMajiangCard[][] majiang;

    private EJoyState joyState = EJoyState.PreStart;

    public ZhuoJiMajiangJoy(IMajiangPlayer fangzhu, int outCardTime)
    {
        this.fangzhu = fangzhu;
        this.outCardTime = outCardTime;
    }

    @Override
    public void init() {
        player = new IMajiangPlayer[renshu];
        dice1 = -1;
        dice2 = -1;
        initRule();
        joyState = EJoyState.PreStart;
    }

    private void initRule() {
        ruleMap.clear();

        // ע���齫����
    }

    @Override
    public void start() {
        XAssert.check(joyState.equals(EJoyState.PreStart));
        XAssert.check(legalCheck());

        choiceZhuang();

        confuseCard();

        //������ʱ������ʱ����ʱ�䣬�л�������ң��й�δ�������
        startTimer();
        
        setJoyState(EJoyState.Start);
    }

    private void startTimer() {
        
        
    }

    //�����齫
    private void confuseCard() {

    }

    //�Ϸ�����
    private boolean legalCheck() {
        return false;
    }

    //ѡ��ׯ��
    private void choiceZhuang() {

    }

    @Override
    public void stop() {

        stopTimer();
        
        setJoyState(EJoyState.Close);
    }

    private void stopTimer() {

    }

    @Override
    public void dispatchInputMessage() {

        handleThrowDiceMessage();

        handleOutCardMessage();

        hanldeTrusteeMessage();

        handleHuCardMessage();

        handleGangCardMessage();

        handlePengCardMessage();

    }

    //��ɢ����
    private void handleDismissMessage()
    {

    }


    //�˳���Ϸ
    private void handleQuitJoyMessage()
    {

    }

    //������Ϸ
    private void handleEnterJoyMessage()
    {

    }

    //����
    private void handlePengCardMessage() {

    }

    //����
    private void handleGangCardMessage() {

    }

    //����
    private void handleHuCardMessage() {

    }

    //�й�
    private void hanldeTrusteeMessage() {

    }

    //�û�����
    private void handleOutCardMessage() {
        //У��

        //����

        //ЧӦ

    }

    //������
    private void handleThrowDiceMessage() {
        //���ƣ�����
    }

    @Override
    public void sendOutputMessage() {

    }

    @Override
    public void reset() {

    }

    public void setJoyState(EJoyState joyState) {
        this.joyState = joyState;
    }
}
