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

    private int renshu;   //人数
    private IMajiangPlayer[] player;  //玩家
    private Map<Integer, IMajiangRule> ruleMap=new HashMap<>();  //规则

    private int dice1;  //骰子1
    private int dice2;  //骰子2

    private final IMajiangPlayer fangzhu;
    private final int outCardTime;
    private IMajiangPlayer zhuangjia;
    private IMajiangPlayer outCardPlayer;  //  当前出牌用户
    
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

        // 注入麻将规则
    }

    @Override
    public void start() {
        XAssert.check(joyState.equals(EJoyState.PreStart));
        XAssert.check(legalCheck());

        choiceZhuang();

        confuseCard();

        //启动定时器，定时出牌时间，切换出牌玩家，托管未出牌玩家
        startTimer();
        
        setJoyState(EJoyState.Start);
    }

    private void startTimer() {
        
        
    }

    //混乱麻将
    private void confuseCard() {

    }

    //合法检验
    private boolean legalCheck() {
        return false;
    }

    //选定庄家
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

    //解散房间
    private void handleDismissMessage()
    {

    }


    //退出游戏
    private void handleQuitJoyMessage()
    {

    }

    //进入游戏
    private void handleEnterJoyMessage()
    {

    }

    //碰牌
    private void handlePengCardMessage() {

    }

    //杠牌
    private void handleGangCardMessage() {

    }

    //胡牌
    private void handleHuCardMessage() {

    }

    //托管
    private void hanldeTrusteeMessage() {

    }

    //用户出牌
    private void handleOutCardMessage() {
        //校验

        //出牌

        //效应

    }

    //掷骰子
    private void handleThrowDiceMessage() {
        //切牌，发牌
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
