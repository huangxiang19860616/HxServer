package Hx.HxServer.game.majiang.sink;


import Hx.HxServer.game.majiang.algorithm.IMajiangJoy;

/**
 * Created by huangxiang on 2017/2/16 0016.
 *
 * 棋牌桌子
 */
public interface IMajiangFrameSink {
    enum  ESinkType
    {
        JingBiSink, SiRenSink
    }

    enum EJoyType
    {
        GuiYangZhuoJi
    }

    //初始化桌子，配置桌子
    void initSink(IMajiangJoy joy);

    //设置密码
    void setPassword(String pwd);

    //复位桌子
    void resetSink();

    //开始游戏
    void startGame();

    //结束游戏
    void stopGame();

    //处理外部消息
    void dispatchInputMessage();

    //推出内部消息
    void sendOutputMessage();
}
