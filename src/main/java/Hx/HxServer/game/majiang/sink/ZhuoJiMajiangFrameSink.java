package Hx.HxServer.game.majiang.sink;


import Hx.HxServer.game.majiang.algorithm.IMajiangJoy;

/**
 * Created by huangxiang on 2017/2/16 0016.
 */
class ZhuoJiMajiangFrameSink implements IMajiangFrameSink{
    private final ESinkType sinkType;  //房间类型
    private final EJoyType joyType;    //玩法类型

    private String password = "";   //密码

    private IMajiangJoy joy;

    public ZhuoJiMajiangFrameSink(ESinkType sinkType, EJoyType joyType) {
        this.sinkType = sinkType;
        this.joyType = joyType;
    }

    @Override
    public  void initSink(IMajiangJoy joy) {
        this.joy = joy;

        joy.init();
    }

    @Override
    public void setPassword(String pwd) {
        this.password = pwd;
    }

    @Override
    public void resetSink() {
        password="";
        joy.reset();
    }

    @Override
    public void startGame() {
        joy.start();
    }

    @Override
    public void stopGame() {
        joy.stop();
    }

    @Override
    public void dispatchInputMessage() {
        joy.dispatchInputMessage();
    }

    @Override
    public void sendOutputMessage() {
        joy.sendOutputMessage();
    }
}
